package mousquetaires.tests.unit.unroller;

import mousquetaires.tests.unit.languages.common.graph.IntNode;
import mousquetaires.tests.unit.languages.common.graph.IntTestFlowGraph;
import mousquetaires.tests.unit.languages.common.graph.IntTestFlowGraphBuilder;
import org.junit.Test;

import static mousquetaires.tests.unit.languages.common.graph.IntTestFlowGraphBuilder.node;
import static mousquetaires.tests.unit.languages.common.graph.IntTestFlowGraphBuilder.r;


public class UnrollerDoubleNestedLoopTest extends UnrollerTestBase {

    @Test
    public void test_boundMeetsTwice() {
        final int bound = 8;
        // expected graph construction:
        IntNode source = node(0), sink = node(-1);
        IntTestFlowGraphBuilder builder = new IntTestFlowGraphBuilder(source, sink);

        //first forward path:
        builder.addEdge(true, source,  r(1, 1));
        builder.addPath(true, r(1, 1), r(2, 1), r(3, 1), r(4, 1), sink);
        //backtracking from (4,1):
        builder.addEdge(false, r(4, 1), r(1, 2));
        builder.addPath(true,  r(1, 2), r(2, 2), r(3, 2), r(4, 2), sink);
        //backtracking from (4,2):
        //builder.addEdge(false, r(4, 2), sink); //not need this edge, already has true-edge
        //backtracking from (3,2):
        builder.addEdge(false, r(3, 2), r(2, 3));
        builder.addPath(true,  r(2, 3), sink);
        //backtracking from (3,1):
        builder.addEdge(false, r(3, 1), r(2, 4));
        builder.addPath(true,  r(2, 4), r(3, 3), r(4, 3), sink);
        //backtracking from (4,3):
        builder.addEdge(false, r(4, 3), r(1, 3));
        builder.addPath(true,  r(1, 3), r(2, 5), sink);
        //backtracking from (3,3):
        builder.addEdge(false, r(3, 3), r(2, 6));
        builder.addPath(true,  r(2, 6), r(3, 4), r(4, 4), sink);
        //backtracking from (3,4):
        builder.addEdge(false, r(3, 4), r(2, 7));
        builder.addPath(true, r(2, 7), sink);

        IntTestFlowGraph expectedUnrolled = builder.build();

        run(expectedUnrolled, getNonUnrolledGraph(), bound);
    }

    @Override
    protected IntTestFlowGraph getNonUnrolledGraph() {
        // length = 5
        IntNode source = node(0), sink = node(-1);
        IntTestFlowGraphBuilder builder = new IntTestFlowGraphBuilder(source, sink);
        builder.addPath(true, source, node(1), node(2), node(3), node(4), sink);
        builder.addEdge(false, node(3), node(2));
        builder.addEdge(false, node(4), node(1));
        return builder.build();
    }
}