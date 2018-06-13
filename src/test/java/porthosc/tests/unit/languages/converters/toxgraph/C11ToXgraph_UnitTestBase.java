package porthosc.tests.unit.languages.converters.toxgraph;

import porthosc.languages.InputExtensions;
import porthosc.languages.InputLanguage;
import porthosc.languages.converters.InputParserBase;
import porthosc.languages.converters.toxgraph.Y2XConverter;
import porthosc.languages.converters.toytree.YtreeParser;
import porthosc.languages.syntax.xgraph.process.XCyclicProcess;
import porthosc.languages.syntax.xgraph.program.XProgramBase;
import porthosc.languages.syntax.ytree.YSyntaxTree;
import porthosc.memorymodels.wmm.MemoryModel;
import porthosc.tests.TestFailedException;
import porthosc.tests.unit.Assertion;
import porthosc.tests.unit.languages.common.graph.AssertionXProcessesEqual;
import porthosc.tests.unit.languages.converters.AbstractConverterUnitTest;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;


public abstract class C11ToXgraph_UnitTestBase extends AbstractConverterUnitTest<XCyclicProcess> {

    protected abstract MemoryModel.Kind memoryModel();

    @Override
    protected Iterator<? extends XCyclicProcess> parseTestFile(String testFile) {
        try {
            File file = new File(testFile);
            InputLanguage language = InputExtensions.parseProgramLanguage(file.getName());
            InputParserBase parser = new YtreeParser(file, language);
            YSyntaxTree internalRepr = parser.parseFile();
            Y2XConverter converter = new Y2XConverter(memoryModel());
            XProgramBase program = converter.convert(internalRepr);
            return program.getProcesses().iterator(); //TODO: fix this Unchecked assignment!
        } catch (IOException e) {
            e.printStackTrace();
            throw new TestFailedException(e);
        }
    }

    @Override
    protected Assertion getComparingAssertion(XCyclicProcess expected, XCyclicProcess actual) {
        return new AssertionXProcessesEqual(expected, actual);
    }
}