package mousquetaires.languages.converters.toxgraph.interpretation;

import mousquetaires.languages.syntax.xgraph.events.computation.XAssertionEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XBinaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.process.XProcessId;


public class XPostludeInterpreter extends XLudeInterpreterBase {

    public XPostludeInterpreter(XProcessId processId, XMemoryManager memoryManager) {
        super(processId, memoryManager);
    }

    @Override
    public XAssertionEvent processAssertion(XBinaryComputationEvent assertion) {
        XAssertionEvent assertionEvent = new XAssertionEvent(assertion);
        processNextEvent(assertionEvent);
        return assertionEvent;
    }
}