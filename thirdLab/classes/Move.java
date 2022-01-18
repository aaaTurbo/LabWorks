package classes;

public class Move {
    private String move;

    public Move(String name) {
        move = name;
    }

    @Override
    public String toString() {
        return " Действие " + move;
    }

    @Override
    public int hashCode() {
        return move.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Move m = (Move) o;
        return this.move == m.move;
    }
}
