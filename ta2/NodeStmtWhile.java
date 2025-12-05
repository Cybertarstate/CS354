public class NodeStmtWhile extends NodeStmt {
    private NodeBoolExpr condition;
    private NodeBlock block;

    public NodeStmtWhile(NodeBoolExpr condition, NodeBlock block) {
        this.condition = condition;
        this.block = block;
    }

    @Override
    public double eval(Environment env) throws EvalException {
        while (condition.eval(env) != 0) {   
            block.eval(env);
        }
        return 0;  
    }

    @Override
    public String code() {
        String blockCode = block.code().trim();
        return "while (" + condition.code() + ") " + blockCode + "\n";
    }
}