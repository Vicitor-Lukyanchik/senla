package task4.domain;

public class Room {

    private int cost;
    private boolean isSettled = false;
    private boolean isRepaired = false;

    public Room() {
    }

    public Room(int cost) {
        this.cost = cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }

    public boolean isSettled() {
        return isSettled;
    }

    public void setSettled(boolean isSettled) {
        this.isSettled = isSettled;
    }

    public boolean isRepaired() {
        return isRepaired;
    }

    public void setRepaired(boolean isRepaired) {
        this.isRepaired = isRepaired;
    }
}
