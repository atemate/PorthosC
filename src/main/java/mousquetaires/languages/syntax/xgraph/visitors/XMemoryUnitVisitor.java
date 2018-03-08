package mousquetaires.languages.syntax.xgraph.visitors;

import mousquetaires.languages.syntax.xgraph.memories.XConstant;
import mousquetaires.languages.syntax.xgraph.memories.XLocation;
import mousquetaires.languages.syntax.xgraph.memories.XRegister;


public interface XMemoryUnitVisitor<T> {
    T visit(XRegister entity);
    T visit(XLocation entity);
    T visit(XConstant entity);
}