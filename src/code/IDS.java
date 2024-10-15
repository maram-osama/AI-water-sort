package code;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Stack;

public class IDS implements QingFunction {
    Stack<Node> stack = new Stack<>();
    Node root;
    int curMaxDepth;
    HashSet<String> visited = new HashSet<>();
    
    public void insert(ArrayList<Node> children) {
        if(children.isEmpty()){
            return;
        }
        if (root == null)
            root = children.get(0);
        Collections.reverse(children);
        for (Node child : children) {
            String stateString = child.stateToString();
            if (child.depth <= curMaxDepth && !visited.contains(stateString)) {
                stack.push(child);
                visited.add(child.stateToString());
            }
        }
        if (stack.isEmpty()) {
            stack.push(root);
            curMaxDepth++;
            visited.clear();
        }
    }
    
    public Node remove() {
        if (stack.isEmpty()) return null;
        return stack.pop();
    }
    
}
