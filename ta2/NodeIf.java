public class NodeIf extends Node {
    private NodeBoolExpr condition;
    private Node stmtThen;
    private Node stmtElse;   

    public NodeIf(NodeBoolExpr condition, Node stmtThen, Node stmtElse) {
        this.condition = condition;
        this.stmtThen = stmtThen;
        this.stmtElse = stmtElse;
    }

    public double eval(Environment env) throws EvalException {
        if (condition.eval(env) != 0) {
            stmtThen.eval(env);
        } else if (stmtElse != null) {
            stmtElse.eval(env);
        }
        return 0; 
    }

    public String code() {
        String code = "if " + condition.code() + " then " + stmtThen.code();
        if (stmtElse != null) {
            code += " else " + stmtElse.code();
        }
        return code;
    }
} 
