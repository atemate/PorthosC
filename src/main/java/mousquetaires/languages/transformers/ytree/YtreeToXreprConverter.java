package mousquetaires.languages.transformers.ytree;

import mousquetaires.interpretation.eventrepr.Interpreter;
import mousquetaires.languages.xrepr.XProgram;
import mousquetaires.languages.ytree.YSyntaxTree;


public class YtreeToXreprConverter {

    private final Interpreter interpreter;

    public YtreeToXreprConverter(Interpreter interpreter) {
        this.interpreter = interpreter;
    }

    public XProgram convert(YSyntaxTree internalSyntaxTree) {
        YtreeToXreprConverterVisitor visitor = new YtreeToXreprConverterVisitor(interpreter);
        internalSyntaxTree.accept(visitor);
        return visitor.getResult();
    }
}
