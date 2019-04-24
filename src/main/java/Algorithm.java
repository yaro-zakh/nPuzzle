import java.util.*;


class Algorithm {
    PriorityQueue<AStarMatrix> openList;
    List<AStarMatrix> closedList = new LinkedList<>() {
        @Override
        public boolean contains(Object o) {
            AStarMatrix tmp = (AStarMatrix) o;
            for (AStarMatrix element: this) {
                if (Arrays.deepEquals(element.puzzle, tmp.puzzle)) {
                    return true;
                }
            }
            return false;
        }
    };
    List<AStarMatrix> neighborhood = new LinkedList<>();
    AStarMatrix current;
    AStarMatrix goal;
    Heuristic heuristic;
    private int[][] original;
    private int sizePuzzle;

    Algorithm(LinkedHashSet<Integer> inputPuzzle) {
        this.sizePuzzle = FileParser.sizePuzzle;
        goal = new AStarMatrix(GoalPuzzle.getInstance().initPuzzle(sizePuzzle, sizePuzzle));
        heuristic = new Heuristic(goal);
        initCurrentPuzzle(inputPuzzle);
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

    void initOpenList() {
        Comparator<AStarMatrix> fStateComparator = Comparator.comparingInt(m -> m.f);
        openList = new PriorityQueue<>(fStateComparator) {
            @Override
            public boolean contains(Object o) {
                AStarMatrix tmp = (AStarMatrix) o;
                for (AStarMatrix element: this) {
                    if (Arrays.deepEquals(element.puzzle, tmp.puzzle)) {
                        return true;
                    }
                }
                return false;
            }
        };
    }

    void findPath() {
        openList.add(new AStarMatrix(original));
        do {
            current = openList.peek();
            print2dArray(current.puzzle);
            closedList.add(current);
            openList.remove();
            if (closedList.contains(goal)) {
                break;
            }
            neighborhood = findNeighborhood(current);
            for (AStarMatrix aStarMatrix : neighborhood) {
                if (closedList.contains(aStarMatrix)) {
                    continue;
                }
                if (!openList.contains(aStarMatrix)) {
                    aStarMatrix.g += 1;
                    aStarMatrix.h = heuristic.manhattan(aStarMatrix);
                    aStarMatrix.setF();
                    aStarMatrix.setParents(current);
                    openList.add(aStarMatrix);
                }
            }
        } while (!openList.isEmpty());
    }

    private List<AStarMatrix> findNeighborhood(AStarMatrix current) {
        List<AStarMatrix> neighborhood = new LinkedList<>();
        int iCurrent = 0, jCurrent = 0;
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

    public boolean possiblePos(int x, int y) {
        return x < FileParser.sizePuzzle && x >= 0 && y < FileParser.sizePuzzle && y >= 0;
    }

    public void print2dArray(int[][] array) {
        for (int i = 0; i < FileParser.sizePuzzle; i++) {
            for (int j = 0; j < FileParser.sizePuzzle; j++) {
                System.out.printf("%3d",array[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
}
