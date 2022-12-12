import java.io.IOException;

public class DataCollector {
    public static void main(String[] args) throws IOException {
        // run a few Othello games
        for(int i=0; i<1000; i++) {
            Runtime rt = Runtime.getRuntime();
            Process pr = rt.exec("java Othello -nw ABOthelloPlayer RandomOthelloPlayer");
        }
        for(int i=0; i<1000; i++) {
	    Runtime rt = Runtime.getRuntime();
	    Process pr = rt.exec("java Othello -nw RandomOthelloPlayer ABOthelloPlayer");
	} 
	System.out.println("DONE.");
    }
}
