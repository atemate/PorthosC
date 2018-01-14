package mousquetaires.languages.ytree.signatures;

import mousquetaires.languages.common.types.YXType;
import mousquetaires.languages.common.visitors.YtreeVisitor;
import mousquetaires.languages.ytree.YEntity;
import mousquetaires.utils.YtreeUtils;

import java.util.Iterator;


public class YFunctionParameter implements YEntity {

    public final YXType type;
    public final String name;

    public YFunctionParameter(YXType type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public Iterator<? extends YEntity> getChildrenIterator() {
        return YtreeUtils.createIteratorFrom(type);
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public YFunctionParameter copy() {
        return new YFunctionParameter(type, name);
    }
}