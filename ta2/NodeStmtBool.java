public class NodeStmtBool extends NodeStmt {
    private NodeBoolExpr expr;

    public NodeStmtBool(NodeBoolExpr expr) {
        this.expr = expr;
    }

    @Override
    public double eval(Environment env) throws EvalException {
        double result = expr.eval(env);
        System.out.println((int)result);
        return result;
    }

   
    @Override
    public String code() {
        return "printf(\"%d\\n\", " + expr.code() + ");\n"; 
    }


}