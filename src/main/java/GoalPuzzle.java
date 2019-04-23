class GoalPuzzle {
    private static GoalPuzzle ourInstance = new GoalPuzzle();

    static GoalPuzzle getInstance() {
        return ourInstance;
    }

    private GoalPuzzle() {
    }

    int[][] initPuzzle(int sizeRow, int sizeColumn) {
        int i, k = 0, l = 0, cnt = 0, size = sizeColumn * sizeRow;
        int [][] goal = new int[sizeRow][sizeColumn];

        while (k < sizeRow && l < sizeColumn) {
            for (i = l; i < sizeColumn; ++i) {
                goal[k][i] = (cnt == (size - 1)) ? 0 : ++cnt;
            }
            k++;

            for (i = k; i < sizeRow; ++i) {
                goal[i][sizeColumn-1] = (cnt == (size - 1)) ? 0 : ++cnt;
            }
            sizeColumn--;

            if ( k < sizeRow) {
                for (i = sizeColumn-1; i >= l; --i) {
                    goal[sizeRow-1][i] = (cnt == (size - 1)) ? 0 : ++cnt;
                }
                sizeRow--;
            }

            if (l < sizeColumn) {
                for (i = sizeRow-1; i >= k; --i) {
                    goal[i][l] = (cnt == (size - 1)) ? 0 : ++cnt;
                }
                l++;
            }
        }
        return goal;
    }
}
