package mousquetaires.tests.unit.languages.converters.toxgraph.c11;

import mousquetaires.languages.ProgramLanguage;
import mousquetaires.languages.syntax.xgraph.events.computation.XComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.operators.XZOperator;
import mousquetaires.languages.syntax.xgraph.events.memory.XMemoryEvent;
import mousquetaires.languages.syntax.xgraph.memories.XConstant;
import mousquetaires.languages.syntax.xgraph.memories.XMemoryManager;
import mousquetaires.languages.syntax.xgraph.memories.XRegister;
import mousquetaires.languages.syntax.xgraph.processes.XProcess;
import mousquetaires.tests.unit.UnitTestPaths;
import mousquetaires.tests.unit.languages.converters.toxgraph.C11ToXgraph_UnitTestBase;
import mousquetaires.tests.unit.languages.converters.toxgraph.XProcessTestBuilder;
import org.junit.Test;


public class C11ToXgraph_BranchingStatement_UnitTest extends C11ToXgraph_UnitTestBase {

    @Test
    public void test() {
        XProcessTestBuilder builder = new XProcessTestBuilder("?");//TODO: process id
        XMemoryManager memoryManager = new XMemoryManager(ProgramLanguage.C11, null);
        XRegister registerX = memoryManager.getLocalMemoryUnit("x");
        XRegister registerY = memoryManager.getLocalMemoryUnit("y");
        XConstant const1 = memoryManager.getConstant(1);
        XConstant const2 = memoryManager.getConstant(2);
        XConstant const3 = memoryManager.getConstant(3);
        XConstant const4 = memoryManager.getConstant(4);

        XComputationEvent conditionXequals1 = builder.createComputationEvent(XZOperator.CompareEquals,  registerX, const1);
        XComputationEvent conditionXgreater2 = builder.createComputationEvent(XZOperator.CompareGreater, registerX, const2);
        XMemoryEvent assignY2 = builder.createAssignmentEvent(registerY, const2);
        XMemoryEvent assignXY = builder.createAssignmentEvent(registerX, registerY);
        XMemoryEvent assignY3 = builder.createAssignmentEvent(registerY, const3);
        XMemoryEvent assignX4 = builder.createAssignmentEvent(registerX, const4);

        builder.processFirstEvent(conditionXequals1);
        builder.processBranchingEvent(conditionXequals1, assignY2, conditionXgreater2);
        builder.processNextEvent(assignY2, assignXY);
        builder.processBranchingEvent(conditionXgreater2, assignY3, assignX4);
        builder.processNextEvent(assignXY, assignX4);
        builder.processNextEvent(assignY3, assignX4);
        builder.processLastEvent(assignX4);

        XProcess process = builder.build();

        run( UnitTestPaths.c11StatementsDirectory + "branchingStatement.c",
                getIterator(process));
    }
}