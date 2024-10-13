package code;

import java.util.*;

public class UCS implements QingFunction {
    PriorityQueue<Node> q = new PriorityQueue<>(Comparator.comparingInt(a -> a.pathCost));
    HashMap<String,Integer> visited = new HashMap<>();
    
    public void insert(ArrayList<Node> children) {
        // TODO: Ask TA about visited in UCS
        for (Node child : children) {
            String stateString = child.stateToString();
            if (!visited.containsKey(stateString) || visited.get(stateString)> child.pathCost) {
                q.add(child);
                visited.put(child.stateToString(), child.pathCost);
            }
        }
        q.addAll(children);
    }
    
    public Node remove() {
        if (q.isEmpty()) return null;
        return q.remove();
    }
    
}
