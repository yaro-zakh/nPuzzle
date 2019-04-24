import java.util.Arrays;

class AStarMatrix {
    int[][] puzzle;
    Integer f;
    int g;
    int h;
    private AStarMatrix parents;

    public AStarMatrix(int[][] puzzle) {
        this.puzzle = puzzle;
    }

    public int getF() {
        return f;
    }

    public void setF() {
        this.f = this.g + this.h;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public AStarMatrix getParents() {
        return parents;
    }

    public void setParents(AStarMatrix parents) {
        this.parents = parents;
    }

    public AStarMatrix swap(int xOld, int yOld, int xNew, int yNew) {
        AStarMatrix tmp = new AStarMatrix(new int[FileParser.sizePuzzle][FileParser.sizePuzzle]);
        for (int i = 0; i < FileParser.sizePuzzle; i++) {
            for (int j = 0; j < FileParser.sizePuzzle; j++) {
                tmp.puzzle[i][j] = this.puzzle[i][j];
            }
        }
        tmp.puzzle[xOld][yOld] = this.puzzle[xNew][yNew];
        tmp.puzzle[xNew][yNew] = this.puzzle[xOld][yOld];
        return tmp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AStarMatrix that = (AStarMatrix) o;

        return Arrays.deepEquals(puzzle, that.puzzle);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(puzzle);
    }
}
