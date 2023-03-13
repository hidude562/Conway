import java.util.Arrays;

public class CellArray {
    public static int allIterations = 0;
    public static int allGameCount = 0;

    public Cell[][] cells;
    private static final int sizeX = 50;
    private static final int sizeY = 50;
    private int iter;

    // Blocks, bee-hive, loaf, boat, tub, blinker, Toad, beacon, glider, small, med, large spaceship
    // Count frames each object in frame
    protected static int[] countOfEachType;

    // Increments by one if object on screen for atleast one frame
    protected static int[] countOfEachTypeOne;
    //
    protected static boolean[][][] objectToLookData;
    private boolean[] objectFoundThisRound;
    private static int lookForObjectsCount = 2;


    // Static initializer for initializing object and stuff
    static {
        countOfEachType = new int[lookForObjectsCount];
        countOfEachTypeOne = new int[lookForObjectsCount];

        // Note that this only checks for one orientation of the thing

        objectToLookData = new boolean[lookForObjectsCount][][];

        // Square
        objectToLookData[0] = new boolean[][]
                {{false, false, false, false},
                {false, true, true, false},
                {false, true, true, false},
                {false, false, false, false}};

        // bee-hive
        objectToLookData[1] = new boolean[][]
                {{false, false, false, false, false, false},
                {false, false, true, true, false, false},
                {false, true, false, false,true, false},
                {false, false, true, true, false, false},
                {false, false, false, false, false, false}};
    }


    public CellArray() {
        allGameCount++;
        objectFoundThisRound = new boolean[objectToLookData.length];
        cells = new Cell[sizeX][sizeY];
        iter = 0;

        // Initialize each member of array
        for(int x0 = 0; x0 < sizeX; x0++) {
            for(int y0 = 0; y0 < sizeY; y0++) {
                cells[x0][y0] = new Cell();
            }
        }
    }

    public boolean findThingInCellArray(boolean[][] toLook) {
        // Credit: https://stackoverflow.com/questions/20828033/finding-smaller-2d-array-inside-a-bigger-2d-array
        // Note that this doesn't work for objects on the edge

        for (int x = 0; x < cells.length - toLook.length + 1; ++x)
            loopY: for (int y = 0; y < cells[x].length - toLook[0].length + 1; ++y) {
                for (int xx = 0; xx < toLook.length; ++xx)
                    for (int yy = 0; yy < toLook[0].length; ++yy) {
                        // Tests if live cells equal to tolook
                        if (!(cells[x + xx][y + yy].get() == toLook[xx][yy])) {
                            continue loopY;
                        }
                    }

                // Found the submatrix!
                return true;
            }
        return false;
    }

    public Cell[][] copyCells(Cell[][] cells) {
        // Credit to my man Rocrick on stackoverflow https://stackoverflow.com/questions/1564832/how-do-i-do-a-deep-copy-of-a-2d-array-in-java

        final Cell[][] result = new Cell[sizeX][sizeY];
        for (int x0 = 0; x0 < sizeX; x0++) {
            for (int y0 = 0; y0 < sizeX; y0++) {
                result[x0][y0] = new Cell(cells[x0][y0]);
            }
        }
        return result;
    }

    public void nextStep() {
        allIterations++;
        iter++;
        Cell[][] tempCells = copyCells(cells);
        for(int x0 = 0; x0 < sizeX; x0++) {
            for(int y0 = 0; y0 < sizeY; y0++) {
                tempCells[x0][y0].incrementTime();
                int cellSurroundings = getSurroundings(x0, y0);
                if(cellSurroundings < 2 || cellSurroundings > 3)
                    tempCells[x0][y0].setDead();
                else if(cellSurroundings == 3)
                    tempCells[x0][y0].setAlive();
            }
        }
        cells = copyCells(tempCells);
    }
    public int getSurroundings(int x0, int y0) {
        int liveCells = (cells[x0][y0].get() ? -1 : 0);
        for(int x1 = x0 - 1; x1 <= x0 + 1; x1++) {
            for(int y1 = y0 - 1; y1 <= y0 + 1; y1++) {
                liveCells += (cells[Math.floorMod(x1, sizeX)][Math.floorMod(y1, sizeY)].get() ? 1 : 0);
            }
        }
        return liveCells;
    }
    public void print() {
        System.out.print("\n\n");
        System.out.print("Iter: " + iter + " ");


        System.out.print("\n");
        for(int x0 = 0; x0 < sizeX; x0++) {
            for(int y0 = 0; y0 < sizeY; y0++) {
                if(cells[x0][y0].get())
                    System.out.print("@");
                else
                    System.out.print(" ");
            }
            System.out.print("\n");
        }
    }

    public void endRound() {
        for(int i = 0; i < objectToLookData.length; i++) {
            countOfEachTypeOne[i] += objectFoundThisRound[i] ? 1 : 0;
        }
    }

    public void printStats() {
        for(int i = 0; i < objectFoundThisRound.length; i++) {
            System.out.println(i + ":\ntotal count: " + countOfEachType[i] + "\neach game: " + countOfEachTypeOne[i] + "\n\n");
        }
    }

    public void updateRecognizedObjets() {
        for(int i = 0; i < objectToLookData.length; i++) {
            boolean foundObject = findThingInCellArray(objectToLookData[i]);
            if(foundObject) {
                objectFoundThisRound[i] = true;
            }
            countOfEachType[i] += foundObject ? 1 : 0;
        }
    }
}
