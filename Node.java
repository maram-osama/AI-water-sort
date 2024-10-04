import java.util.Stack;

public class Node {
    Stack<String>[] state;
    Node parent;
    int[] operator;
    int depth;
    int pathCost;
    
    public Node(Stack<String>[] state, Node parent, int[] operator, int depth, int pathCost) {
        this.state = state;
        this.parent = parent;
        this.operator = operator;
        this.depth = depth;
        this.pathCost = pathCost;
    }
    
    String stateToString(){
        StringBuilder res= new StringBuilder();
        for(Stack<String>s:state){
            res.append(s.toString());
        }
        return res.toString();
    }
    
}
