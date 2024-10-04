import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Stack;

public class GenericSearch{
    int numLayers;
    int numBottles;
    int numExpansions;
    HashSet<String> visited = new HashSet<>();
    
    public String genericSearch(String problem, QingFunction qingFunc){
        Node init= parseInitialState(problem);
        qingFunc.insert(init);
        while(true){
            Node current= qingFunc.remove();
            if (current == null) break;
            numExpansions++;
            if (isGoal(current)){
                StringBuilder plan = pathToGoal(current);
                return plan.deleteCharAt(plan.length()-1)+";"+ current.pathCost+";"+numExpansions;
            }
            ArrayList<Node> children=expand(current);
            for (Node child:children) {
                qingFunc.insert(child);
            }
        }
        return "NOSOLUTION";
    }
    
    StringBuilder pathToGoal(Node cur){
        if(cur==null)return new StringBuilder();
        return pathToGoal(cur.parent).
                append("pour_").
                append(cur.operator[0]).
                append("_").append(cur.operator[1]).
                append(",");
    }
    
    public boolean isGoal(Node node){
        Stack<String> rev = new Stack<>();
        for(Stack<String> s: node.state){
            if(s.isEmpty())continue;
            String colorOnTop = s.peek();
            while(!s.isEmpty()){
              String c = s.pop();
              if(!Objects.equals(c, colorOnTop))return false;
              rev.push(c);
            }
            while(!rev.isEmpty()){
                s.push(rev.pop());
            }
        }
        return true;
    }
    
    public ArrayList<Node> expand(Node node){
        ArrayList<Node> children= new ArrayList<>();
        for(int i=0; i<numBottles;i++){
            for(int j=i+1;j<numBottles;j++){
                Stack<String>[] state= node.state.clone();
                Stack<String> currentBottle=state[i];
                if (currentBottle.isEmpty()) break;
                boolean expanded=false;
                Stack<String> nextBottle=state[j];
                while(nextBottle.size()<numLayers &&
                        (nextBottle.isEmpty() || currentBottle.peek().equals(nextBottle.peek())) ){
                    nextBottle.push(currentBottle.pop());
                    expanded=true;
                }
                if (expanded){
                    int [] operation=new int[]{i,j};
                    Node child= new Node(state,node,operation,node.depth+1, node.pathCost+1);
                    String stateString = child.stateToString();
                if(!visited.contains(stateString))
                    children.add(child);
                    visited.add(child.stateToString());
                }
            }
        }
        return children;
        
    }
    
    Node parseInitialState(String s){
        String[] splitOnSemiColon= s.split(";");
        this.numBottles = Integer.parseInt(splitOnSemiColon[0]);
        this.numLayers = Integer.parseInt(splitOnSemiColon[1]);
        Stack<String>[] state =  new Stack[numBottles];
        for(int i=0; i<numBottles; i++){
            String[]colors = splitOnSemiColon[i+2].split(",");
            state[i]= new Stack<>();
            for(int j= colors.length-1; j>=0; j--){
                String c = colors[j];
                if(c.equals("e"))break;
                state[i].push(c);
            }
        }
        Node init = new Node(state,null,null,0,0);
        return init;
    }
}
