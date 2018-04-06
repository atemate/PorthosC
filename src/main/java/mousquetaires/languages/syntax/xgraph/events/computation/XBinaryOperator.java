package mousquetaires.languages.syntax.xgraph.events.computation;


public enum XBinaryOperator implements XOperator {
    // Int / Float operators:
    Addition,
    Subtraction,
    Multiplication,
    Division,
    Modulo,
    LeftShift,
    RightShift,
    // BitVec operators:
    BitAnd,
    BitOr,
    BitXor,
    // Relative operators:
    CompareEquals,
    CompareNotEquals,
    CompareLess,
    CompareLessOrEquals,
    CompareGreater,
    CompareGreaterOrEquals,
    // Logical operators:
    Conjunction,
    Disjunction,
    ;

    //TODO: toZ3() //add correct method

    @Override
    public String toString() {
        switch (this) {
            case Addition:               return "+";
            case Subtraction:            return "-";
            case Multiplication:         return "*";
            case Division:               return "/";
            case Modulo:                 return "%";
            case LeftShift:              return "<<";
            case RightShift:             return ">>";
            case BitAnd:                 return "&";
            case BitOr:                  return "|";
            case BitXor:                 return "^";
            case CompareEquals:          return "==";
            case CompareNotEquals:       return "!=";
            case CompareLess:            return "<";
            case CompareLessOrEquals:    return "<=";
            case CompareGreater:         return ">";
            case CompareGreaterOrEquals: return ">=";
            case Conjunction:            return "&&";
            case Disjunction:            return "||";
            default:
                throw new IllegalArgumentException(this.name());
        }
    }
}
