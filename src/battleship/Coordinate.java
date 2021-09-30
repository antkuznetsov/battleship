package battleship;

public class Coordinate {
    private final int row;
    private final int column;

    public Coordinate(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public Coordinate(String coordinate) {
        int tempX = Integer.parseInt(coordinate.substring(1)) - 1;
        int tempY = coordinate.charAt(0) - 65;
        if (tempX > 9 || tempY > 9) {
            throw new RuntimeException("Wrong coordinate!");
        }
        column = tempX;
        row = tempY;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
