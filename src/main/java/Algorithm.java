import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.PriorityQueue;


class Algorithm {
    PriorityQueue<AStarMatrix> openList;
    private int[][] goal;
    private int[][] current;
    private int sizePuzzle;

    Algorithm(int size, LinkedHashSet<Integer> inputPuzzle) {
        this.sizePuzzle = size;
        goal = GoalPuzzle.getInstance().initPuzzle(size, size);
        initCurrentPuzzle(inputPuzzle);
        initOpenList();
    }

    private void initCurrentPuzzle(LinkedHashSet<Integer> inputPuzzle) {
        int cnt = -1;
        Integer[] tmpPuzzle = inputPuzzle.toArray(new Integer[sizePuzzle * sizePuzzle]);
        current = new int[sizePuzzle][sizePuzzle];
        for (int i = 0; i < sizePuzzle; i++) {
            for (int j = 0; j < sizePuzzle; j++) {
                current[i][j] = tmpPuzzle[++cnt];
            }
        }
    }

    void initOpenList() {
        Comparator<AStarMatrix> fStateComparator = Comparator.comparingInt(m -> m.f);
        openList = new PriorityQueue<>(fStateComparator);
    }

}
