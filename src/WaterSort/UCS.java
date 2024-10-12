package WaterSort;

import java.util.*;

public class UCS implements QingFunction {
    PriorityQueue<Node> q = new PriorityQueue<>(Comparator.comparingInt(a -> a.pathCost));
    HashSet<String> visited = new HashSet<>();
    
    public void insert(ArrayList<Node> children) {
        // TODO: Ask TA about visited in UCS
//        for (Node child : children) {
//            String stateString = child.stateToString();
//            if (!visited.contains(stateString)) {
//                q.add(child);
//                visited.add(child.stateToString());
//            }
//        }
        q.addAll(children);
    }
    
    public Node remove() {
        if (q.isEmpty()) return null;
        return q.remove();
    }
    
}
