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
            throw new IllegalArgumentException("Error! You entered the wrong coordinates!");
        }
        column = tempX;
        row = tempY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinate that = (Coordinate) o;

        if (row != that.row) return false;
        return column == that.column;
    }

    @Override
    public int hashCode() {
        int result = row;
        result = 31 * result + column;
        return result;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
