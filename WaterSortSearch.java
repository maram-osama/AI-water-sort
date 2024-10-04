import java.util.Stack;

public class WaterSortSearch extends GenericSearch {
    Strategy strategy;
    boolean visualize;
    
    public void solve(String initialState, Strategy strategy, boolean visualize){
        Node init = parseInitialState(initialState);
        this.strategy = strategy;
        this.visualize = visualize;
        switch(strategy){
            case BF:
            case DF:
            case UC:
        }
    }
}
