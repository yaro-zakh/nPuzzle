import java.io.*;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage [input] [-'flag' heuristic function]\n" +
                    "Heuristic functions:\n" +
                    "\t-m manhattan (default);\n" +
                    "\t-c chiponpos;\n" +
                    "\t-e eucldist.\n");
            System.exit(1);
        }

        try {
            FileInputStream puzzleFile = new FileInputStream(args[0]);
            FileParser fileParser = new FileParser(puzzleFile);
            if (fileParser.correctFile()) {
                fileParser.parseFlag(args);
                Algorithm start = new Algorithm(fileParser.getPuzzleCell());
                start.findPath();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
