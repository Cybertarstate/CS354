public class NodeBlock extends Node {
    private NodeStmt stmt;
    private NodeBlock next;

    public NodeBlock(NodeStmt stmt, NodeBlock next) {
        this.stmt = stmt;
        this.next = next;
    }

    @Override
    public double eval(Environment env) throws EvalException {
        double result = 0;
        NodeBlock curr = this;
        while (curr != null) {
            result = curr.stmt.eval(env);
            curr = curr.next;
        }
        return result;
    }

    @Override
    public String code() {
        StringBuilder sb = new StringBuilder();
        NodeBlock curr = this;
        while (curr != null) {
            sb.append(curr.stmt.code());
            sb.append("\n"); 
            curr = curr.next;
        }
        return sb.toString();
    }
}
