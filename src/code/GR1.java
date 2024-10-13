package code;

import java.util.*;

public class GR1 implements QingFunction {
    PriorityQueue<Node> q = new PriorityQueue<>(Comparator.comparingInt(a -> a.h1));
    HashMap<String,Integer> visited = new HashMap<>();

    public void insert(ArrayList<Node> children) {
        for (Node child : children) {
           heuristicFunction(child);
           String state = child.stateToString();
           if(!visited.containsKey(child) || visited.get(state)> child.h1) {
               q.add(child);
               visited.put(state,child.h1);
           }
        }
    }
    
    public Node remove() {
        if (q.isEmpty()) return null;
        return q.remove();
    }
    
    public int heuristicFunction(Node node){
        Stack<String> rev = new Stack<>();
        int mismatchedBottles=0;
        for (Stack<String> s : node.state) {
            if (s.isEmpty()) continue;
            String colorOnTop = s.peek();
            while (!s.isEmpty()) {
                String c = s.pop();
                if (!Objects.equals(c, colorOnTop)) {
                    rev.push(c);
                    mismatchedBottles++;
                   // System.out.println(mismatchedBottles);
                    continue;
                }
                rev.push(c);
            }
            while (!rev.isEmpty()) {
                s.push(rev.pop());
            }
        }
        node.h1= mismatchedBottles;
      //  System.out.println("Node "+ node.pathCost + " " + mismatchedBottles);
        return mismatchedBottles;
    }
    
}
