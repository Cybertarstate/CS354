public class NodeStmtIf extends NodeStmt {
    private NodeBoolExpr condition;
    private NodeBlock thenBlock;
    private NodeBlock elseBlock;  

    public NodeStmtIf(NodeBoolExpr condition, NodeBlock thenBlock, NodeBlock elseBlock) {
        this.condition = condition;
        this.thenBlock = thenBlock;
        this.elseBlock = elseBlock;
    }

    @Override
    public double eval(Environment env) throws EvalException {
        if (condition.eval(env) != 0) {     
            thenBlock.eval(env);
        } else if (elseBlock != null) {
            elseBlock.eval(env);
        }
        return 0; 
    }

@Override
    public String code() {
        String code = "if (" + condition.code() + ") {\n"
                    + thenBlock.code()
                    + "}\n";
        if (elseBlock != null) {
            code += "else {\n"
                + elseBlock.code()
                + "}\n";
        }
        return code;
    }
}