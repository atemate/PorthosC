package mousquetaires.utils.exceptions.ytree;

import org.antlr.v4.runtime.ParserRuleContext;


public class ParserException extends RuntimeException {

    public final ParserRuleContext context;

    public ParserException(ParserRuleContext context, Exception e) {
        super(e);
        this.context = context;
    }

    public ParserException(ParserRuleContext context, String message) {
        super(message);
        this.context = context;
    }

    //// bad design
    //public ParserException(String message) {
    //    super(message);
    //    //this.context = null;
    //}
}
