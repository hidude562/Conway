import java.util.Arrays;

public class CellArray {
    public Cell[][] cells;
    private static final int sizeX = 50;
    private static final int sizeY = 50;
    private int iter;

    // Blocks, bee-hive, loaf, boat, tub, blinker, Toad, beacon, glider, small, med, large spaceship
    protected static int[] countOfEachType;


    public CellArray() {
        cells = new Cell[sizeX][sizeY];
        iter = 0;

        // Initialize each member of array
        for(int x0 = 0; x0 < sizeX; x0++) {
            for(int y0 = 0; y0 < sizeY; y0++) {
                cells[x0][y0] = new Cell();
            }
        }
    }

    public Cell[][] copyCells() {
        // Credit to my man Rocrick on stackoverflow https://stackoverflow.com/questions/1564832/how-do-i-do-a-deep-copy-of-a-2d-array-in-java

        final Cell[][] result = new Cell[sizeX][];
        for (int i = 0; i < sizeX; i++) {
            result[i] = Arrays.copyOf(cells[i], sizeY);
        }
        return result;
    }

    public void nextStep() {
        iter++;
        Cell[][] tempCells = copyCells();
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
        cells = tempCells;
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
}
