package WaterSort;

public class WaterSortSearch extends GenericSearch {
    static String strategy;
    static boolean visualize;
    
    public static String solve(String initialState, String strategy, boolean visualize) {
        strategy = strategy;
        visualize = visualize;
        String result="";
        switch (strategy) {
            case "BF":
                result = genericSearch(initialState, new BFS());
                break;
            case "DF":
                result = genericSearch(initialState, new DFS());
                break;
            case "UC":
                result = genericSearch(initialState, new UCS());
                break;
            case "ID":
                result = genericSearch(initialState, new IDS());
                break;
            case "GR1":
                result = genericSearch(initialState, new GR1());
                break;
            case "GR2":
                result = genericSearch(initialState, new GR2());
                break;
            case "AS1":
                result = genericSearch(initialState, new AS1());
                break;
            case "AS2":
                result = genericSearch(initialState, new AS2());
                break;
        }
        return result;
    }
    
    public static void main(String[] args) {
        String grid0 = "3;" +
                "4;" +
                "r,y,r,y;" +
                "y,r,y,r;" +
                "e,e,e,e;";
        WaterSortSearch ws = new WaterSortSearch();
        solve(grid0, "AS2", true);
    }
}
