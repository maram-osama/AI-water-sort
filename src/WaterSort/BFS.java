package WaterSort;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class BFS implements QingFunction {
    Queue<Node> q = new LinkedList<>();
    HashSet<String> visited = new HashSet<>();
    
    public void insert(ArrayList<Node> children) {
        for (Node child : children) {
            String stateString = child.stateToString();
            if (!visited.contains(stateString)) {
                q.add(child);
                visited.add(child.stateToString());
            }
        }
    }
    
    public Node remove() {
        if (q.isEmpty()) return null;
        return q.remove();
    }
    
}
