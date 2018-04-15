package mousquetaires.tests.func.dartagnan;

import com.googlecode.zohhak.api.TestWith;
import mousquetaires.app.modules.dartagnan.DartagnanVerdict;
import mousquetaires.memorymodels.wmm.MemoryModel;
import mousquetaires.tests.unit.FuncTestPaths;

import static org.junit.Assert.assertEquals;


public class DartagnanSzymanskiTest extends AbstractDartagnanFuncTest {

    private final String szymanski_pts_rx    = FuncTestPaths.targetsDirectory + "all_rx/szymanski.pts";
    private final String szymanski_litmus_rx = FuncTestPaths.targetsDirectory + "all_rx/szymanski.litmus";
    private final String szymanski_pts_sc    = FuncTestPaths.targetsDirectory + "all_sc/szymanski.pts";

    // == Relaxed operations: ==

    @TestWith({
            szymanski_pts_rx + ", " + "SC,    Reachable",
            szymanski_pts_rx + ", " + "TSO,   Reachable",
            szymanski_pts_rx + ", " + "PSO,   NonReachable",
            szymanski_pts_rx + ", " + "RMO,   NonReachable",
            szymanski_pts_rx + ", " + "Alpha, NonReachable",
            szymanski_pts_rx + ", " + "Power, NonReachable",
            szymanski_pts_rx + ", " + "ARM,   NonReachable",
    })
    public void test_szymanski_pts_rx(String inputProgramFile, MemoryModel.Kind sourceModel, DartagnanVerdict.Status expected) {
        DartagnanVerdict verdict = runTest(inputProgramFile, sourceModel);
        assertEquals(expected, verdict.result);
    }

    @TestWith({
            szymanski_litmus_rx + ", " + "SC,    NonReachable",
            szymanski_litmus_rx + ", " + "TSO,   NonReachable",
            szymanski_litmus_rx + ", " + "PSO,   NonReachable",
            szymanski_litmus_rx + ", " + "RMO,   NonReachable",
            szymanski_litmus_rx + ", " + "Alpha, NonReachable",
            szymanski_litmus_rx + ", " + "Power, NonReachable",
            szymanski_litmus_rx + ", " + "ARM,   NonReachable",
    })
    public void test_szymanski_litmus_rx(String inputProgramFile, MemoryModel.Kind sourceModel, DartagnanVerdict.Status expected) {
        DartagnanVerdict verdict = runTest(inputProgramFile, sourceModel);
        assertEquals(expected, verdict.result);
    }

    // == Sequentially consistent operations: ==

    @TestWith({
            szymanski_pts_sc + ", " + "SC,    NonReachable",
            szymanski_pts_sc + ", " + "TSO,   NonReachable",
            szymanski_pts_sc + ", " + "PSO,   NonReachable",
            szymanski_pts_sc + ", " + "RMO,   NonReachable",
            szymanski_pts_sc + ", " + "Alpha, NonReachable",
            szymanski_pts_sc + ", " + "Power, NonReachable",
            szymanski_pts_sc + ", " + "ARM,   NonReachable",
    })
    public void test_szymanski_pts_sc(String inputProgramFile, MemoryModel.Kind sourceModel, DartagnanVerdict.Status expected) {
        DartagnanVerdict verdict = runTest(inputProgramFile, sourceModel);
        assertEquals(expected, verdict.result);
    }

}
