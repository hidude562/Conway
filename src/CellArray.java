public class CellArray {
    private Cell[][] cells;
    private static final int sizeX = 50;
    private static final int sizeY = 50;

    // Blocks, bee-hive, loaf, boat, tub, blinker, Toad, beacon, glider, small, med, large spaceship
    protected static int[] countOfEachType;

    public CellArray() {
        cells = new Cell[50][50];
    }
    public void nextStep() {
        Cell[][] tempCells = cells.clone();
        for(int x0 = 0; x0 < sizeX; x0++) {
            for(int y0 = 0; y0 < sizeX; y0++) {
                tempCells[x0][y0].incrementTime();
                int cellSurroundings = getSurroundings(x0, y0, tempCells);
                if(cellSurroundings < 2 || cellSurroundings > 3)
                    tempCells[x0][y0].setDead();
                else if(cellSurroundings == 3)
                    tempCells[x0][y0].setAlive();
            }
        }
        cells = tempCells.clone();
    }
    public int getSurroundings(int x0, int y0, Cell[][] cells) {
        int liveCells = 0;
        for(int x1 = x0 - 1; x1 <= x0 + 1; x1++) {
            for(int y1 = y0 - 1; y1 <= y0 + 1; y1++) {
                liveCells += (cells[Math.floorMod(x1, sizeX)][Math.floorMod(y1, sizeY)].get() ? 1 : 0);
            }
        }
        return liveCells;
    }
}
