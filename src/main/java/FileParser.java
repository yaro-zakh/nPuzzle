import java.io.*;
import java.util.LinkedHashSet;
import java.util.regex.Pattern;

public class FileParser {
    private FileInputStream file;
    private String stringFile = "";
    public static int sizePuzzle;
    private int cntPuzzleLine;
    private LinkedHashSet<Integer> puzzleCell = new LinkedHashSet<>();

    FileParser(FileInputStream inputFile) {
        this.file = inputFile;
    }

    @SuppressWarnings("StringConcatenationInLoop")
    boolean correctFile() throws IOException {
        int i;
        while((i = file.read()) != -1) stringFile += (char) i;
        file.close();
        String[] lines = stringFile.split("\\r?\\n");
        for (String line: lines) {
            if (!correctionLine(line)) {
                System.out.println("The line \"" + line + "\" is incorrect");
                return false;
            }
        }
        if (puzzleCell.size() != Math.pow(sizePuzzle, 2)) {
            System.out.println("Identical cells found");
            return false;
        }
        for (Integer cell: puzzleCell) {
            if (cell > (Math.pow(sizePuzzle, 2)) - 1) {
                System.out.println("Incorrect cell \"" + cell + "\"");
                return false;
            }
        }
        return true;
    }

    private boolean correctionLine(String stringFile) {
        int size = (sizePuzzle == 0) ? 1: sizePuzzle - 2;
        String commentReg = "(#.*)*";
        String sizeReg = "^\\s*\\d+\\s*";
        String lineReg = String.format("\\s+(\\d+\\s+){%d}\\d+\\s*", size);

        Pattern commentPat = Pattern.compile(commentReg);
        Pattern sizePuzzlePat = Pattern.compile(sizeReg + commentReg);
        Pattern digitLinePat = Pattern.compile(sizeReg + lineReg + commentReg);

        if (commentPat.matcher(stringFile).matches()) {
            return true;
        } else if (sizePuzzlePat.matcher(stringFile).matches()) {
            String[] tmpSize = stringFile.trim().split("\\s");
            sizePuzzle = Integer.parseInt(tmpSize[0]);
        } else if (digitLinePat.matcher(stringFile).matches() && sizePuzzle > 0) {
            String[] splitLine = stringFile.split("\\s");
            for (String cell: splitLine) {
                try {
                    puzzleCell.add(Integer.parseInt(cell));
                } catch (NumberFormatException ignored) {
                }
            }
            cntPuzzleLine++;
            return cntPuzzleLine <= sizePuzzle;
        } else {
            return false;
        }
        return true;
    }

    public LinkedHashSet<Integer> getPuzzleCell() {
        return puzzleCell;
    }
}
