public class NodeStmtBlock extends NodeStmt {
    private NodeBlock block;

    public NodeStmtBlock(NodeBlock block) {
        this.block = block;
    }

    @Override
    public double eval(Environment env) throws EvalException {
        return block.eval(env);  
    }

      @Override
    public String code() {
       
        String blockCode = block.code();
        if (blockCode.contains(";")) {
            return "{\n" + blockCode + "}\n";
        } else {
            return blockCode; 
        }
    }
}