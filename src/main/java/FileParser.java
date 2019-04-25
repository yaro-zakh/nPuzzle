import java.io.*;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class FileParser {
    private FileInputStream file;
    private String stringFile = "";
    public static int sizePuzzle;
    public static char flag;
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

    public static boolean solvable(int[][] puzzle) {
        int sizeRow = FileParser.sizePuzzle;
        int sizeColumn = FileParser.sizePuzzle;
        int i, k = 0, l = 0, cnt = 0;
        List<Integer> linePuzzle = new LinkedList<>();

        while (k < sizeRow && l < sizeColumn) {
            for (i = l; i < sizeColumn; ++i) linePuzzle.add(puzzle[k][i]);
            k++;

            for (i = k; i < sizeRow; ++i) linePuzzle.add(puzzle[i][sizeColumn - 1]);
            sizeColumn--;

            if ( k < sizeRow) {
                for (i = sizeColumn-1; i >= l; --i) linePuzzle.add(puzzle[sizeRow - 1][i]);
                sizeRow--;
            }

            if (l < sizeColumn) {
                for (i = sizeRow-1; i >= k; --i) linePuzzle.add(puzzle[i][l]);
                l++;
            }
        }
        for (int element = 0; element < linePuzzle.size(); element++) {
            if (element == linePuzzle.size() - 1) {
                break;
            }
            for (int j = element + 1; j < linePuzzle.size(); j++) {
                if (linePuzzle.get(element) > linePuzzle.get(j)) {
                    cnt++;
                }
            }
        }
        cnt += linePuzzle.size() % 2 == 0 ? 1 : 0;
        return cnt % 2 == 0;
    }

    void parseFlag(String[] args) {
        label: for (String value: args) {
            switch (value) {
                case "-m": setFlag('m');
                    break label;
                case "-c": setFlag('c');
                    break label;
                case "-e": setFlag('e');
                    break label;
                default: setFlag('m');
                    break;
            }
        }
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

    public static void setFlag(char flag) {
        FileParser.flag = flag;
    }
}
