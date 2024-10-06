import java.util.*;

public class UCS implements QingFunction {
    PriorityQueue<Node> q = new PriorityQueue<>(Comparator.comparingInt(a -> a.pathCost));
    
    public void insert(ArrayList<Node> children) {
        q.addAll(children);
    }
    
    public Node remove() {
        if (q.isEmpty()) return null;
        return q.remove();
    }
    
}
