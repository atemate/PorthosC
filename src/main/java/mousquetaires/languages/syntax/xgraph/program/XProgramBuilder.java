package mousquetaires.languages.syntax.xgraph.program;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.syntax.xgraph.process.XProcess;
import mousquetaires.utils.patterns.BuilderBase;


public class XProgramBuilder extends BuilderBase<XProgram> {

    private ImmutableList.Builder<XProcess> processes;

    public XProgramBuilder() {
        this.processes = new ImmutableList.Builder<>();
    }

    public void addProcess(XProcess process) {
        processes.add(process);
    }

    @Override
    public XProgram build() {
        return new XProgram(processes.build());
    }
}
