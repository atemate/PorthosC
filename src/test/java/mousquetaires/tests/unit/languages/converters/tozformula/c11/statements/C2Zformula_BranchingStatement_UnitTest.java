package mousquetaires.tests.unit.languages.converters.tozformula.c11.statements;

import mousquetaires.memorymodels.wmm.MemoryModel;
import mousquetaires.tests.unit.UnitTestPaths;
import mousquetaires.tests.unit.languages.converters.tozformula.C2Zformula_UnitTestBase;
import org.junit.Test;


public class C2Zformula_BranchingStatement_UnitTest extends C2Zformula_UnitTestBase {

    @Override
    protected MemoryModel.Kind memoryModel() {
        return MemoryModel.Kind.TSO;//temporary
    }

    @Test
    public void test() {
        run( UnitTestPaths.c11StatementsDirectory + "branchingStatement.c",
                null);
    }
}
