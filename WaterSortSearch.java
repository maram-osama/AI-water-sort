import java.util.Stack;

public class WaterSortSearch extends GenericSearch {
    Strategy strategy;
    boolean visualize;
    
    public void solve(String initialState, Strategy strategy, boolean visualize) {
        this.strategy = strategy;
        this.visualize = visualize;
        switch (strategy) {
            case BF:
                System.out.println(genericSearch(initialState, new BFS()));
                break;
            case DF:
                System.out.println(genericSearch(initialState, new DFS()));
                break;
            case UC:
                System.out.println(genericSearch(initialState, new UCS()));
                break;
            case ID:
                System.out.println(genericSearch(initialState, new IDS()));
                break;
        }
    }
    
    public static void main(String[] args) {
        String init = "5;4;" + "b,y,r,b;" + "b,y,r,r;" +
                "y,r,b,y;" + "e,e,e,e;" + "e,e,e,e;";
        WaterSortSearch ws = new WaterSortSearch();
        ws.solve(init, Strategy.ID, true);
    }
}
