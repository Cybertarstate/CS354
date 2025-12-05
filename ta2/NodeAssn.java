public class NodeAssn extends NodeStmt {

	private String id;
	private NodeExpr expr;

	public NodeAssn(String id, NodeExpr expr) {
		this.id = id;
		this.expr = expr;
	}

	public double eval(Environment env) throws EvalException {
		double val = expr.eval(env);
		env.put(id, val);
		return val;
	}

	public String code() {
		return id + "=" + expr.code() + ";";
	}

}
