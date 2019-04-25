import java.util.*;

class Algorithm {
    private PriorityQueue<AStarMatrix> openList;
    private HashSet<AStarMatrix> closedList = new LinkedHashSet<>();
    private AStarMatrix goal;
    private Heuristic heuristic;
    private int[][] original;
    private int sizePuzzle;

    Algorithm(LinkedHashSet<Integer> inputPuzzle) {
        this.sizePuzzle = FileParser.sizePuzzle;
        goal = new AStarMatrix(GoalPuzzle.getInstance().initPuzzle(sizePuzzle, sizePuzzle));
        heuristic = new Heuristic(goal);
        initCurrentPuzzle(inputPuzzle);
        if (!FileParser.solvable(original)) {
            System.out.println("This puzzle is unsolvable");
            System.exit(1);
        }
        initOpenList();
    }

    private void initCurrentPuzzle(LinkedHashSet<Integer> inputPuzzle) {
        int cnt = -1;
        Integer[] tmpPuzzle = inputPuzzle.toArray(new Integer[sizePuzzle * sizePuzzle]);
        original = new int[sizePuzzle][sizePuzzle];
        for (int i = 0; i < sizePuzzle; i++) {
            for (int j = 0; j < sizePuzzle; j++) {
                original[i][j] = tmpPuzzle[++cnt];
            }
        }
    }

    private void initOpenList() {
        Comparator<AStarMatrix> fStateComparator = Comparator.comparingInt(m -> m.f);
        openList = new PriorityQueue<>(fStateComparator);
    }

    void findPath() {
        long startTime = System.nanoTime();
        openList.add(new AStarMatrix(original));

        do {
            AStarMatrix current = openList.peek();
            assert current != null;
            print2dArray(current.puzzle);
            closedList.add(current);
            openList.remove();
            if (Arrays.deepEquals(current.puzzle, goal.puzzle)) {
                break;
            }
            List<AStarMatrix> neighborhood = findNeighborhood(current);
            for (AStarMatrix aStarMatrix : neighborhood) {
                if (closedList.contains(aStarMatrix)) {
                    continue;
                }
                if (!openList.contains(aStarMatrix)) {
                    aStarMatrix.g += 1;
                    switch (FileParser.flag) {
                        case 'm': aStarMatrix.h = heuristic.manhattan(aStarMatrix);
                            break;
                        case 'c': aStarMatrix.h = heuristic.chiponpos(aStarMatrix);
                            break;
                        case 'e': aStarMatrix.h = heuristic.eucldist(aStarMatrix);
                            break;
                    }
                    aStarMatrix.setF();
                    aStarMatrix.setParents(current);
                    openList.add(aStarMatrix);
                }
            }
        } while (!openList.isEmpty());

        long endTime = System.nanoTime();
        double elapsedTimeInSecond = (double) (endTime - startTime) / 1_000_000_000;
        System.out.println(elapsedTimeInSecond + " seconds");
    }

    private List<AStarMatrix> findNeighborhood(AStarMatrix current) {
        List<AStarMatrix> neighborhood = new LinkedList<>();
        int iCurrent, jCurrent = 0;
        label: for (iCurrent = 0; iCurrent < sizePuzzle; iCurrent++) {
            for (jCurrent = 0; jCurrent < sizePuzzle; jCurrent++) {
                if (current.puzzle[iCurrent][jCurrent] == 0) {
                    break label;
                }
            }
        }
        //swapUP
        if (possiblePos(iCurrent - 1, jCurrent)) {
            neighborhood.add(current.swap(iCurrent, jCurrent, iCurrent - 1, jCurrent));
        }
        //swapDown
        if (possiblePos(iCurrent + 1, jCurrent)) {
            neighborhood.add(current.swap(iCurrent, jCurrent, iCurrent + 1, jCurrent));
        }
        //swapRight
        if (possiblePos(iCurrent, jCurrent + 1)) {
            neighborhood.add(current.swap(iCurrent, jCurrent, iCurrent, jCurrent + 1));
        }
        //swapLeft
        if (possiblePos(iCurrent, jCurrent - 1)) {
            neighborhood.add(current.swap(iCurrent, jCurrent, iCurrent, jCurrent - 1));
        }
        return neighborhood;
    }

    private boolean possiblePos(int x, int y) {
        return x < FileParser.sizePuzzle && x >= 0 && y < FileParser.sizePuzzle && y >= 0;
    }

    private void print2dArray(int[][] array) {
        for (int i = 0; i < FileParser.sizePuzzle; i++) {
            for (int j = 0; j < FileParser.sizePuzzle; j++) {
                System.out.printf("%3d",array[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
}
