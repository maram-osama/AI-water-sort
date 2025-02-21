package code;

import java.util.*;

public class GenericSearch {
    static int numLayers;
    static int numBottles;
    static int numExpansions = 1;
    
    public static String genericSearch(String problem, QingFunction qingFunc) {
        Node init = parseInitialState(problem);
        ArrayList<Node> tree = new ArrayList<>();
        tree.add(init);
        qingFunc.insert(tree);
        while (true) {
            Node current = qingFunc.remove();
            if (current == null) break;
            numExpansions++;
//            System.out.println("hi");
//            if(numExpansions<50){
//                System.out.println(current.stateToString());
//            }
            if (isGoal(current)) {
//                System.out.println("Goal" + current.stateToString());
                StringBuilder plan = pathToGoal(current);
                String s = plan.deleteCharAt(plan.length() - 1) + ";" + current.pathCost + ";" + numExpansions;
              //  System.out.println("sol" +s);
             //   System.out.println(overestimate(current, current.pathCost));
                return s;
            }
            //    System.out.println(current.stateToString() + "pour_" + Arrays.toString(current.operator));
            ArrayList<Node> children = expand(current);
            qingFunc.insert(children);
            
        }
        return "NOSOLUTION";
    }
    
    static StringBuilder pathToGoal(Node cur) {
        if (cur.parent == null) return new StringBuilder();
        
        return pathToGoal(cur.parent).
                append("pour_").
                append(cur.operator[0]).
                append("_").
                append(cur.operator[1]).
                append(",");
    }
    static StringBuilder overestimate(Node cur, int pathcost) {
        
        if (cur.parent == null) return new StringBuilder("Path cost 0 Heuristic value "+ cur.h2);
        int pathdiff= pathcost-cur.pathCost;
        return overestimate(cur.parent, pathcost).
                append("Path cost ").
                append(pathdiff).
                append(" Heuristic value ").
                append(cur.h2)
                .append("\n");
    }
    
    public static boolean isGoal(Node node) {
        Stack<String> rev = new Stack<>();
        boolean isGoal = true;
        for (Stack<String> s : node.state) {
            if (s.isEmpty()) continue;
            String colorOnTop = s.peek();
            while (!s.isEmpty()) {
                String c = s.pop();
                if (!Objects.equals(c, colorOnTop)) {
                    isGoal = false;
                }
                rev.push(c);
            }
            while (!rev.isEmpty()) {
                s.push(rev.pop());
            }
        }
        return isGoal;
    }
    
    public static ArrayList<Node> expand(Node node) {
        ArrayList<Node> children = new ArrayList<>();
        for (int i = 0; i < numBottles; i++) {
            for (int j = 0; j < numBottles; j++) {
                if (i == j) continue;
                int cost = node.pathCost;
                Stack<String>[] state = node.state.clone();
                Stack<String> currentBottle = (Stack<String>) state[i].clone();
                if (currentBottle.isEmpty()) break;
                boolean expanded = false;
                Stack<String> nextBottle = (Stack<String>) state[j].clone();
                while (nextBottle.size() < numLayers &&
                        (nextBottle.isEmpty() || (!currentBottle.isEmpty() && currentBottle.peek().equals(nextBottle.peek())))) {
                    String x = currentBottle.pop();
                    nextBottle.push(x);
                    cost++;
                    expanded = true;
                }
                if (expanded) {
                    int[] operation = new int[2];
                    operation[0] = i;
                    operation[1] = j;
                    state[i] = currentBottle;
                    state[j] = nextBottle;
                    Node child = new Node(state, node, operation, node.depth + 1, cost);
                    children.add(child);
                }
            }
        }
        return children;
        
    }
    
    static Node parseInitialState(String s) {
        String[] splitOnSemiColon = s.split(";");
        numBottles = Integer.parseInt(splitOnSemiColon[0]);
        numLayers = Integer.parseInt(splitOnSemiColon[1]);
        numExpansions = 1;
        Stack<String>[] state = new Stack[numBottles];
        for (int i = 0; i < numBottles; i++) {
            String[] colors = splitOnSemiColon[i + 2].split(",");
            state[i] = new Stack<>();
            for (int j = colors.length - 1; j >= 0; j--) {
                String c = colors[j];
                if (c.equals("e")) break;
                state[i].push(c);
            }
        }
        Node init = new Node(state, null, null, 0, 0);
        return init;
    }
}
