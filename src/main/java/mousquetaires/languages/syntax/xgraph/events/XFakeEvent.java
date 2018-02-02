package mousquetaires.languages.syntax.xgraph.events;

import mousquetaires.languages.syntax.xgraph.processes.XEventInfo;
import mousquetaires.languages.visitors.xgraph.XgraphVisitor;
import mousquetaires.utils.exceptions.NotSupportedException;


public class XFakeEvent extends XEventBase {

    public XFakeEvent(XEventInfo info) {
        super(info);
    }

    @Override
    public <T> T accept(XgraphVisitor<T> visitor) {
        throw new NotSupportedException();
    }
}
