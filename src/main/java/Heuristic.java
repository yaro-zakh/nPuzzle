public class Heuristic {
    AStarMatrix goal;
    int h;

    public Heuristic(AStarMatrix goal) {
        this.goal = goal;
    }

    public int manhattan(AStarMatrix origin) {
        Coordinate goal;
        h = 0;
        for (int i = 0; i < FileParser.sizePuzzle; i++) {
            for (int j = 0; j < FileParser.sizePuzzle; j++) {
                goal = findGoalCell(origin.puzzle[i][j]);
                h += Math.abs(goal.x - i) + Math.abs(goal.y - j);
            }
        }
        return h;
    }

    public int chiponpos(AStarMatrix origin) {
        Coordinate goal;
        Coordinate current;
        h = 0;
        for (int i = 0; i < FileParser.sizePuzzle; i++) {
            for (int j = 0; j < FileParser.sizePuzzle; j++) {
                goal = findGoalCell(origin.puzzle[i][j]);
                current = new Coordinate(i, j);
                if (!goal.equals(current)) {
                    h++;
                }
            }
        }
        return h;
    }

    public int eucldist(AStarMatrix origin) {
        Coordinate goal;
        h = 0;
        for (int i = 0; i < FileParser.sizePuzzle; i++) {
            for (int j = 0; j < FileParser.sizePuzzle; j++) {
                goal = findGoalCell(origin.puzzle[i][j]);
                h += Math.sqrt(Math.pow(i - goal.x, 2) + Math.pow(j - goal.y, 2));
            }
        }
        return h;
    }

    public Coordinate findGoalCell(int value) {
        for (int i = 0; i < FileParser.sizePuzzle; i++) {
            for (int j = 0; j < FileParser.sizePuzzle; j++) {
                if (goal.puzzle[i][j] == value) {
                    return new Coordinate(i, j);
                }
            }
        }
        return null;
    }
}
