package mousquetaires.languages.syntax.xrepr.events.controlflow;

import mousquetaires.languages.converters.toxrepr.XEventInfo;
import mousquetaires.languages.syntax.xrepr.memories.XLocalMemoryUnit;


public class XConditionalJumpEvent extends XUnconditionalJumpEvent {
    public final XLocalMemoryUnit conditionalMemory;

    public XConditionalJumpEvent(XEventInfo info, XLocalMemoryUnit conditionalMemory, String jumpToLabel) {
        super(info, jumpToLabel);
        this.conditionalMemory = conditionalMemory;
    }
}
