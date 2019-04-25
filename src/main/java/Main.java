import java.io.*;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            usage();
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
            usage();
        }
    }

    public static void usage() {
        System.err.println("Usage [input] [-'flag' heuristic function] [-'search flag']\n" +
                "Heuristic functions:\n" +
                "\t-m manhattan (default);\n" +
                "\t-c chiponpos;\n" +
                "\t-e eucldist.\n\n" +
                "\t-g greedy search\n" +
                "\t-u uniform-cost.\n");
        System.exit(1);
    }
}
