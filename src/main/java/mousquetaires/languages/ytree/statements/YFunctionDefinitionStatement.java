package mousquetaires.languages.ytree.statements;

import mousquetaires.languages.common.visitors.YtreeVisitor;
import mousquetaires.languages.ytree.YEntity;
import mousquetaires.languages.ytree.signatures.MethodSignature;
import mousquetaires.utils.YtreeUtils;
import mousquetaires.utils.exceptions.NotSupportedException;

import java.util.Iterator;


public class YFunctionDefinitionStatement extends YStatement {

    private final MethodSignature signature;
    private final YBlockStatement body;

    public YFunctionDefinitionStatement(MethodSignature signature, YBlockStatement body) {
        super(null);
        this.signature = signature;
        this.body = body;
    }

    @Override
    public YStatement withLabel(String newLabel) {
        throw new NotSupportedException();
    }

    @Override
    public Iterator<? extends YEntity> getChildrenIterator() {
        return YtreeUtils.createIteratorFrom(body);
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public YFunctionDefinitionStatement copy() {
        return new YFunctionDefinitionStatement(signature, body);
    }
}