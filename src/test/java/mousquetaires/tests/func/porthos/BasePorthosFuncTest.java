package mousquetaires.tests.func.porthos;

import mousquetaires.PorthosModule;
import mousquetaires.app.modules.porthos.PorthosMode;
import mousquetaires.app.modules.porthos.PorthosOptions;
import mousquetaires.app.modules.porthos.PorthosVerdict;
import mousquetaires.models.MemoryModelName;
import mousquetaires.tests.func.BaseFuncTest;

import java.io.File;


class BasePorthosFuncTest extends BaseFuncTest {

    protected PorthosVerdict runTest(String inputProgramFile,
                                     MemoryModelName sourceModel,
                                     MemoryModelName targetModel,
                                     PorthosMode mode) {
        PorthosOptions options = new PorthosOptions();
        options.inputProgramFile = new File(inputProgramFile);
        options.sourceModel = sourceModel;
        options.targetModel = targetModel;
        options.mode = mode;

        PorthosModule module = new PorthosModule(options);
        return (PorthosVerdict) run(module);
    }
}