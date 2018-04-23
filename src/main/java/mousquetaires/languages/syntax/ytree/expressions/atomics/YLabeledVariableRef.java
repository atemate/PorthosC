package mousquetaires.languages.syntax.ytree.expressions.atomics;

import mousquetaires.languages.common.citation.CodeLocation;
import mousquetaires.languages.syntax.ytree.visitors.ytree.YtreeVisitor;
import mousquetaires.utils.exceptions.NotSupportedException;

import java.util.Objects;


public class YLabeledVariableRef extends YVariableRef {

    private final String label;

    public YLabeledVariableRef(CodeLocation location, String label, String name) {
        this(location, label, name, 0);
    }

    private YLabeledVariableRef(CodeLocation location, String label, String name, int pointerLevel) {
        super(location, Kind.Local, name, pointerLevel);
        this.label = label;
    }


    public String getLabel() {
        return label;
    }

    @Override
    public YVariableRef asGlobal() {
        throw new NotSupportedException("Labeled variables may be only local");
    }

    @Override
    public YLabeledVariableRef withPointerLevel(int level) {
        return new YLabeledVariableRef(codeLocation(), getLabel(), getName(), level);
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return getLabel() + ':' + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (!(o instanceof YLabeledVariableRef)) { return false; }
        if (!super.equals(o)) { return false; }
        YLabeledVariableRef that = (YLabeledVariableRef) o;
        return Objects.equals(getLabel(), that.getLabel());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getLabel());
    }
}