public class NodeStmt extends Node {

    private Node stmt;  // can be NodeAssn, NodeWr, or NodeRd

    public NodeStmt() {
        
    }


    public NodeStmt(Node stmt) {
        this.stmt = stmt;
    }

    public double eval(Environment env) throws EvalException {
        return stmt.eval(env);
    }

    public String code() {
        return stmt.code();
    }

}
