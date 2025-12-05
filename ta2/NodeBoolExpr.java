public class NodeBoolExpr extends NodeStmt {

	private NodeExpr exprOne;
    private NodeExpr exprTwo;
    private NodeRelop relop;

	public NodeBoolExpr(NodeExpr exprOne, NodeRelop relop, NodeExpr exprTwo) {
		this.exprOne = exprOne;
		this.relop = relop;
		this.exprTwo = exprTwo;
	}

    @Override
	public double eval(Environment env) throws EvalException {
		double left  = exprOne.eval(env);
        double right = exprTwo.eval(env);
        return relop.op(left, right);
	}
    
	public String code() {
		return (exprOne == null || exprTwo == null || relop == null) 
			? "" 
			: exprOne.code() + relop.code() + exprTwo.code();
	}

}
