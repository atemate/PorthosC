package mousquetaires.languages.converters.tozformula;

import mousquetaires.languages.syntax.xgraph.XUnrolledProgram;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XBinaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XNullaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XUnaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XLoadMemoryEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XStoreMemoryEvent;
import mousquetaires.languages.syntax.xgraph.visitors.XEventVisitorBase;
import mousquetaires.languages.syntax.xgraph.visitors.XVisitorIllegalStateException;
import mousquetaires.languages.syntax.zformula.ZBoolFormula;
import mousquetaires.languages.syntax.zformula.ZVariableReference;
import mousquetaires.utils.exceptions.NotImplementedException;

import static mousquetaires.languages.syntax.zformula.ZBoolFormulaHelper.equality;


class ZDataFlowEncoder extends XEventVisitorBase<ZBoolFormula> {

    private final ZStaticSingleAssignmentMap ssaMap;
    //private final ZOperatorEncoder operatorEncoder;

    ZDataFlowEncoder(XUnrolledProgram program) {
        this.ssaMap = new ZStaticSingleAssignmentMap(program.size());
        //this.operatorEncoder = new ZOperatorEncoder();
    }

    public void updateReferences(XEvent current, XEvent parent) {
        ssaMap.copyValues(parent, current);
    }

    public ZBoolFormula encodeOrNull(XEvent current) {
        try {
            return current.accept(this);//TODO
        }
        catch (XVisitorIllegalStateException e) {
            return null;
        }
    }

    @Override
    public ZBoolFormula visit(XNullaryComputationEvent event) {
        return null; // no dataflow transition here
    }

    @Override
    public ZBoolFormula visit(XUnaryComputationEvent event) {
        throw new NotImplementedException(); // ?
    }

    @Override
    public ZBoolFormula visit(XBinaryComputationEvent event) {
        ZVariableReferenceMap map = ssaMap.getEventMapOrThrow(event);
        ZVariableReference left = map.getReferenceOrThrow(event.getFirstOperand());
        ZVariableReference right = map.getReferenceOrThrow(event.getSecondOperand());
        return equality(left, right);
    }

    @Override
    public ZBoolFormula visit(XStoreMemoryEvent event) {
        throw new NotImplementedException();
    }

    @Override
    public ZBoolFormula visit(XLoadMemoryEvent event) {
        ZVariableReferenceMap map = ssaMap.getEventMapOrThrow(event);
        ZVariableReference sourceRef = map.getReferenceOrThrow(event.getSource());
        ZVariableReference destRef = map.getReferenceOrThrow(event.getDestination());
        return equality(sourceRef, destRef);
    }
}
