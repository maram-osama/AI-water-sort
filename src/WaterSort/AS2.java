package WaterSort;

import java.util.*;

public class AS2 implements QingFunction {
    PriorityQueue<Node> q = new PriorityQueue<>(Comparator.comparingInt(a -> (a.h2+a.pathCost)));
    // HashSet<String> visited = new HashSet<>();
    
    public void insert(ArrayList<Node> children) {
        for (Node child : children) {
            heuristicFunction(child);
            q.add(child);
        }
    }
    
    public Node remove() {
        if (q.isEmpty()) return null;
        return q.remove();
    }
    
    public int heuristicFunction(Node node){
        Stack<String> rev = new Stack<>();
        int mismatchedLayers=0;
        for (Stack<String> s : node.state) {
            if (s.isEmpty()) continue;
            while (!s.isEmpty()) {
                String colorOnTop = s.pop();
                if (s.isEmpty()){
                    rev.push(colorOnTop);
                    continue;
                }
                String c = s.peek();
                if (!Objects.equals(c, colorOnTop)) {
                    mismatchedLayers++;
                }
                rev.push(colorOnTop);
            }
        
            while (!rev.isEmpty()) {
                s.push(rev.pop());
            }
        }
        System.out.println("Mismatched layers" + mismatchedLayers);
        node.h2= mismatchedLayers;
        return mismatchedLayers;
    }
    
}
