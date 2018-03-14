package mousquetaires.tests.unit.languages.converters.toxgraph;

import com.google.common.collect.ImmutableMap;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.XEventInfo;
import mousquetaires.languages.syntax.xgraph.events.fake.XEntryEvent;
import mousquetaires.languages.syntax.xgraph.events.fake.XExitEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XBinaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XNullaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XUnaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.operators.XZOperator;
import mousquetaires.languages.syntax.xgraph.events.memory.XLoadMemoryEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XLocalMemoryEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XRegisterMemoryEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XStoreMemoryEvent;
import mousquetaires.languages.syntax.xgraph.memories.XLocalMemoryUnit;
import mousquetaires.languages.syntax.xgraph.memories.XSharedMemoryUnit;
import mousquetaires.languages.syntax.xgraph.process.XFlowGraph;
import mousquetaires.utils.CollectionUtils;
import mousquetaires.utils.patterns.Builder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class XFlowGraphTestBuilder extends Builder<XFlowGraph> {

    private String processId;
    private XEntryEvent entryEvent;
    private XExitEvent exitEvent;

    private ImmutableMap.Builder<XEvent, XEvent> edges;
    private ImmutableMap.Builder<XEvent, XEvent> alternativeEdges;
    private Map<XEvent, Set<XEvent>> edgesReversed;

    private boolean isUnrolled;

    public XFlowGraphTestBuilder(String processId) {
        this.processId = processId;
        this.edges = new ImmutableMap.Builder<>();
        this.alternativeEdges = new ImmutableMap.Builder<>();
        this.edgesReversed = new HashMap<>();
    }

    @Override
    public XFlowGraph build() {
        return new XFlowGraph(processId, entryEvent, exitEvent,
                edges.build(), alternativeEdges.build(),
                CollectionUtils.buildMapOfSets(edgesReversed),
                //null, //TODO: store topologically sorted nodes in separate visitor
                isUnrolled);
    }

    public void markAsUnrolled() {
        this.isUnrolled = true;
    }

    public XComputationEvent createComputationEvent(XLocalMemoryUnit first) {
        return new XNullaryComputationEvent(createEventInfo(), first);
    }

    public XComputationEvent createComputationEvent(XZOperator operator, XLocalMemoryUnit first) {
        return new XUnaryComputationEvent(createEventInfo(), operator, first);
    }

    public XComputationEvent createComputationEvent(XZOperator operator, XLocalMemoryUnit first, XLocalMemoryUnit second) {
        return new XBinaryComputationEvent(createEventInfo(), operator, first, second);
    }

    public XLocalMemoryEvent createAssignmentEvent(XLocalMemoryUnit left, XLocalMemoryUnit right) {
        return new XRegisterMemoryEvent(createEventInfo(), left, right);
    }

    public XLoadMemoryEvent createAssignmentEvent(XLocalMemoryUnit left, XSharedMemoryUnit right) {
        return new XLoadMemoryEvent(createEventInfo(), left, right);
    }

    public XStoreMemoryEvent createAssignmentEvent(XSharedMemoryUnit left, XLocalMemoryUnit right) {
        return new XStoreMemoryEvent(createEventInfo(), left, right);
    }

    public void processFirstEvent(XEvent postEntryEvent) {
        entryEvent = new XEntryEvent(createEventInfo());
        addEventImpl(edges, entryEvent, postEntryEvent);
    }

    public void processLastEvents(XEvent... preExitEvents) {
        exitEvent = new XExitEvent(createEventInfo());
        for (XEvent event : preExitEvents) {
            addEventImpl(edges, event, exitEvent);
        }
    }

    public void processNextEvent(XEvent first, XEvent second, XEvent... others) {
        addEventImpl(edges, first, second);
        XEvent previous = second, next;
        for (int i = 0; i < others.length; i++) {
            next = others[i];
            addEventImpl(edges, previous, next);
            previous = next;
        }
    }


    public void processBranchingEvent(XEvent condition, XEvent firstThen, XEvent firstElse) {
        // todo: make checks more systematic in this builder
        //assert condition instanceof XComputationEvent || (condition instanceof XEventRef && ((XEventRef)condition).getOriginalNode() instanceof XComputationEvent);
        addEventImpl(edges, condition, firstThen);
        alternativeEdges.put(condition, firstElse);
    }

    private XEventInfo createEventInfo() {
        return new XEventInfo(processId);
    }

    private void addEventImpl(ImmutableMap.Builder<XEvent, XEvent> map, XEvent from, XEvent to) {
        map.put(from, to);
        if (!edgesReversed.containsKey(to)) {
            edgesReversed.put(to, new HashSet<>());
        }
        edgesReversed.get(to).add(from);
    }
}
