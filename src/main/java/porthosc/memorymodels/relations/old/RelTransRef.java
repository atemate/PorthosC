package porthosc.memorymodels.relations.old;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Z3Exception;
import dartagnan.program.Event;
import dartagnan.program.Program;
import porthosc.utils.Utils;
import static porthosc.memorymodels.EncodingsOld.satUnion;
import java.util.Set;


public class RelTransRef extends UnaryRelation {

    public RelTransRef(Relation r1, String name) {
        super(r1,name,String.format("(%s)*", r1.getName()));

    }

    public RelTransRef(Relation r1) {
        super(r1,String.format("(%s)*", r1.getName()));
    }

    @Override
    public BoolExpr encodeBasic(Program program, Context ctx) throws Z3Exception {
        BoolExpr enc = ctx.mkTrue();
        Set<Event> events = program.getMemEvents();
        //copied from satTansIDL
        for (Event e1 : events) {
            for (Event e2 : events) {
                //reflexive
                if (e1 == e2) {
                    enc = ctx.mkAnd(enc, Utils.edge(this.getName(), e1, e2, ctx));
                } else {
                    //transitive
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
        }
        return enc;
    }
    
        @Override
    public BoolExpr encodeApprox(Program program, Context ctx) throws Z3Exception {
        BoolExpr enc = ctx.mkTrue();
        Set<Event> events = program.getMemEvents();
        for (Event e1 : events) {
            for (Event e2 : events) {
                //reflexive
                if (e1 == e2) {
                    enc = ctx.mkAnd(enc, Utils.edge(this.getName(), e1, e2, ctx));
                } else {
                    //transitive
                    BoolExpr orClause = ctx.mkFalse();
                    for (Event e3 : events) {
                        orClause = ctx.mkOr(orClause, ctx.mkAnd(Utils.edge(this.getName(), e1, e3, ctx), Utils.edge(this.getName(), e3, e2, ctx)));
                    }
                    //original relation
                    orClause=ctx.mkOr(orClause, Utils.edge(r1.getName(), e1, e2, ctx));
                    //putting it together:
                    enc=ctx.mkAnd(enc,ctx.mkEq(Utils.edge(this.getName(), e1, e2, ctx),orClause));
                }

            }
        }
        return enc;
    }

}