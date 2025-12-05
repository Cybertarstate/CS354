import java.util.Scanner;

public class NodeRd extends NodeStmt {
    private static final Scanner SCANNER = new Scanner(System.in);
    private NodeFactId id;   

    public NodeRd(NodeFactId id) {
        this.id = id;
    }

    @Override
    public double eval(Environment env) throws EvalException {
        double d = SCANNER.nextDouble();
        env.put(id.code(), d); 
        return d;
    }

    @Override
    public String code() {
        return "scanf(\"%lf\", &" + id.code() + ");";
    }
}
