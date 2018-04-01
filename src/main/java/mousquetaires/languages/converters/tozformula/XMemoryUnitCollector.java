package mousquetaires.languages.converters.tozformula;

import mousquetaires.languages.syntax.xgraph.events.computation.XBinaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XUnaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.fake.XEntryEvent;
import mousquetaires.languages.syntax.xgraph.events.fake.XExitEvent;
import mousquetaires.languages.syntax.xgraph.events.fake.XJumpEvent;
import mousquetaires.languages.syntax.xgraph.events.fake.XNopEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XInitialWriteEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XLoadMemoryEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XRegisterMemoryEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XStoreMemoryEvent;
import mousquetaires.languages.syntax.xgraph.memories.XMemoryUnit;
import mousquetaires.languages.syntax.xgraph.visitors.XEventVisitor;
import mousquetaires.utils.exceptions.NotImplementedException;

import java.util.Set;


class XMemoryUnitCollector implements XEventVisitor<Iterable<XMemoryUnit>> {

    @Override
    public Iterable<XMemoryUnit> visit(XUnaryComputationEvent event) {
        return Set.of(event.getOperand());
    }

    @Override
    public Iterable<XMemoryUnit> visit(XBinaryComputationEvent event) {
        return Set.of(event.getFirstOperand(), event.getSecondOperand());
    }

    @Override
    public Iterable<XMemoryUnit> visit(XInitialWriteEvent event) {
        throw new NotImplementedException();
    }

    @Override
    public Iterable<XMemoryUnit> visit(XRegisterMemoryEvent event) {
        return Set.of(event.getSource(), event.getDestination());
    }

    @Override
    public Iterable<XMemoryUnit> visit(XStoreMemoryEvent event) {
        return Set.of(event.getSource(), event.getDestination());
    }

    @Override
    public Iterable<XMemoryUnit> visit(XLoadMemoryEvent event) {
        return Set.of(event.getSource(), event.getDestination());
    }

    @Override
    public Iterable<XMemoryUnit> visit(XEntryEvent event) {
        return Set.of();
    }

    @Override
    public Iterable<XMemoryUnit> visit(XExitEvent event) {
        return Set.of();
    }

    @Override
    public Iterable<XMemoryUnit> visit(XJumpEvent event) {
        return Set.of();
    }

    @Override
    public Iterable<XMemoryUnit> visit(XNopEvent event) {
        return Set.of();
    }

}
