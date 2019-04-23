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
                System.out.println("File OK");
                Algorithm start = new Algorithm(fileParser.getSizePuzzle(), fileParser.getPuzzleCell());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
