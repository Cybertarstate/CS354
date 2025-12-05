public class NodeWr extends NodeStmt {

	private NodeExpr expr;

	public NodeWr(NodeExpr expr) {
		this.expr=expr;
	}

   @Override
    public double eval(Environment env) throws EvalException {
        double val = expr.eval(env);
        System.out.printf("%.2f\n", val);
        return val;
    }

    @Override
    public String code() {
        return "printf(\"%.2f\\n\", (double)(" + expr.code() + "));";
    }

}
