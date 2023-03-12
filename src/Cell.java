public class Cell {
    protected boolean alive;
    protected short lastModified;

    public Cell() {
        // Exactly half of the cells will start out alive
        alive = Math.random() < 0.5;
        lastModified = 0;
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
    public void incrementTime() {
        lastModified++;
    }
}
