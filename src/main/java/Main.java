import java.io.*;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage [input]");
            System.exit(1);
        }

        try {
            FileInputStream puzzleFile = new FileInputStream(args[0]);
            FileParser fileParser = new FileParser(puzzleFile);
            if (fileParser.correctFile()) {
                Algorithm start = new Algorithm(fileParser.getPuzzleCell());
                start.findPath();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
