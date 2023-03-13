public class Cell {
    protected boolean alive;
    protected int lastModified;

    public Cell() {
        // Exactly half of the cells will start out alive
        alive = Math.random() < 0.5;
        lastModified = 0;
    }
    public Cell(Cell otherCell) {
        alive = otherCell.get();
        lastModified = otherCell.getLastModified();
    }
    public boolean get() {
        return alive;
    }

    public void setDead() {
        alive = false;
        lastModified = 0;
    }
    public void setAlive() {
        alive = true;
        lastModified = 0;
    }
    public int getLastModified() {
        return lastModified;
    }
    public void incrementTime() {
        lastModified++;
    }
}
