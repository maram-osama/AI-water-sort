import java.util.*;

public class DFS implements QingFunction {
    Stack<Node> stack = new Stack<>();
    HashSet<String> visited = new HashSet<>();
    
    public void insert(ArrayList<Node> children) {
        Collections.reverse(children);
        for (Node child : children) {
            String stateString = child.stateToString();
            if (!visited.contains(stateString)) {
                stack.push(child);
                visited.add(child.stateToString());
            }
        }
    }
    
    public Node remove() {
        if (stack.isEmpty()) return null;
        return stack.pop();
    }
    
}
