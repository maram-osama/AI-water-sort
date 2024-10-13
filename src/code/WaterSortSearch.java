package code;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.ThreadMXBean;
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
        // Get the OS MXBean and ThreadMXBean
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();

        // Measure CPU time at start
        long startCpuTime = threadBean.getCurrentThreadCpuTime();
        Runtime runtime = Runtime.getRuntime();

       // Total memory available to the JVM
        long totalMemory = runtime.freeMemory();
        String grid0 = "5;4;" + "b,y,r,b;" + "b,y,r,r;" +
                "y,r,b,y;" + "e,e,e,e;" + "e,e,e,e;";



        double numIterations = 100;
        for(int i=0;i<numIterations;i++) {
            solve(grid0, "UC", true);
        }

        // Free memory within the JVM
        long freeMemory = runtime.freeMemory();

        // Memory being used by the program
        long usedMemory = totalMemory - freeMemory;
        System.out.println("Used memory in MB: " + usedMemory/((1024* 1024* numIterations)));

        // Measure CPU time at end
        long endCpuTime = threadBean.getCurrentThreadCpuTime();
        System.out.println("CPU Time Used: " + (endCpuTime - startCpuTime)/(numIterations*(int)(1e6))+"ms");

    }
}
