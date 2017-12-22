/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dartagnan.wmm;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Z3Exception;
import dartagnan.program.Event;
import dartagnan.program.MemEvent;
import dartagnan.program.Program;
import dartagnan.utils.Utils;
import static dartagnan.wmm.Encodings.satTransIDL;
import static dartagnan.wmm.Encodings.satUnion;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author Florian Furbach
 */
public class RelTrans extends Relation {

    private Relation r1;

    public RelTrans(Relation r1) {
        super(String.format("%s^+", r1.getName()));
        this.r1 = r1;
        containsRec = r1.containsRec;
        namedRelations.addAll(r1.namedRelations);
    }

    @Override
    public BoolExpr encode(Program program, Context ctx) throws Z3Exception {
        BoolExpr enc = r1.encode(program, ctx);
        Set<Event> events = program.getEvents().stream().filter(e -> e instanceof MemEvent).collect(Collectors.toSet());
        //copied from satTansIDL
        for (Event e1 : events) {
            for (Event e2 : events) {
                BoolExpr orClause = ctx.mkFalse();
                for (Event e3 : events) {
                    orClause = ctx.mkOr(orClause, ctx.mkAnd(Utils.edge(this.getName(), e1, e3, ctx), Utils.edge(this.getName(), e3, e2, ctx),
                            ctx.mkGt(Utils.intCount(String.format("(%s^+;%s^+)", r1.getName(), r1.getName()), e1, e2, ctx), Utils.intCount(this.getName(), e1, e3, ctx)),
                            ctx.mkGt(Utils.intCount(String.format("(%s^+;%s^+)", r1.getName(), r1.getName()), e1, e2, ctx), Utils.intCount(this.getName(), e3, e2, ctx))));
                }
                enc = ctx.mkAnd(enc, ctx.mkEq(Utils.edge(String.format("(%s^+;%s^+)", r1.getName(), r1.getName()), e1, e2, ctx), orClause));
                enc = ctx.mkAnd(enc, ctx.mkEq(Utils.edge(this.getName(), e1, e2, ctx), ctx.mkOr(
                        ctx.mkAnd(Utils.edge(r1.getName(), e1, e2, ctx), ctx.mkGt(Utils.intCount(this.getName(), e1, e2, ctx), Utils.intCount(r1.getName(), e1, e2, ctx))),
                        ctx.mkAnd(Utils.edge(String.format("(%s^+;%s^+)", r1.getName(), r1.getName()), e1, e2, ctx), ctx.mkGt(Utils.intCount(this.getName(), e1, e2, ctx), Utils.intCount(String.format("(%s^+;%s^+)", r1.getName(), r1.getName()), e1, e2, ctx))))));

            }
        }
        return enc;
    }

}