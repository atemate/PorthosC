package mousquetaires.languages.converters.toxgraph.interpretation;

import mousquetaires.languages.syntax.xgraph.XProgram;
import mousquetaires.languages.syntax.xgraph.XProgramBuilder;
import mousquetaires.languages.syntax.xgraph.process.XProcessId;
import mousquetaires.utils.patterns.BuilderBase;


public class XProgramInterpreter extends BuilderBase<XProgram> {

    // TODO: publish methods also!
    private XProgramBuilder programBuilder;
    public final XMemoryManager memoryManager;
    public XProcessInterpreter currentProcess;

    public XProgramInterpreter(XMemoryManager memoryManager) {
        this.memoryManager = memoryManager;
        this.programBuilder = new XProgramBuilder();
        // TODO: add prelude and postlude processes!..
    }

    @Override
    public XProgram build() {
        markFinished();
        assert currentProcess == null : "process " + currentProcess.getProcessId() + " is not finished yet";
        return programBuilder.build(); //preludeBuilder.build(), process.build(), postludeBuilder.build());
    }

    public void startProcessDefinition(XProcessId processId) {
        if (currentProcess != null) {
            throw new IllegalStateException("Attempt to start new process '" + processId +
                                                    "' definition while another process '" +
                                                    currentProcess.getProcessId() +
                                                    "' is being constructed");
        }
        memoryManager.reset(processId);
        currentProcess = new XProcessInterpreter(processId, memoryManager);
        currentProcess.emitEntryEvent();
    }

    public void finishProcessDefinition() {
        if (currentProcess == null) {
            throw new IllegalStateException("Attempt to end process definition without starting it");
        }
        currentProcess.finish();
        programBuilder.addProcess(currentProcess.getResult());
        currentProcess = null;
    }

}