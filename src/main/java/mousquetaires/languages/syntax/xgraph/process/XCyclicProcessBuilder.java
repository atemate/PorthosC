package mousquetaires.languages.syntax.xgraph.process;

import com.google.common.collect.ImmutableMap;
import mousquetaires.languages.common.graph.FlowGraphBuilder;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.controlflow.XEntryEvent;
import mousquetaires.languages.syntax.xgraph.events.controlflow.XExitEvent;


public class XCyclicProcessBuilder extends FlowGraphBuilder<XEvent, XCyclicProcess> {
    private final XProcessId processId;

    public XCyclicProcessBuilder(XProcessId processId) {
        this.processId = processId;
    }

    @Override
    public XCyclicProcess build() {
        finishBuilding();
        return new XCyclicProcess(getProcessId(),
                                  getSource(),
                                  getSink(),
                                  ImmutableMap.copyOf(getEdges(true)),
                                  ImmutableMap.copyOf(getEdges(false)));
    }

    public XProcessId getProcessId() {
        return processId;
    }


    @Override
    public XEntryEvent getSource() {
        return (XEntryEvent) super.getSource();
    }

    @Override
    public XExitEvent getSink() {
        return (XExitEvent) super.getSink();
    }

    @Override
    public void setSource(XEvent source) {
        if (!(source instanceof XEntryEvent)) {
            throw new IllegalArgumentException();
        }
        super.setSource(source);
    }

    @Override
    public void setSink(XEvent sink) {
        if (!(sink instanceof XExitEvent)) {
            throw new IllegalArgumentException();
        }
        super.setSink(sink);
    }
}
