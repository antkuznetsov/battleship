package battleship;

import java.util.Scanner;

public class Main {
    private static final int FIELD_SIZE = 10;
    private static final char SEE_SECTION = '~';
    private static final char SHIP_SECTION = 'O';
    private static final String WRONG_PLACE_ERROR_TEXT = "Error! You placed it too close to another one.";

    private final char[][] field = new char[FIELD_SIZE][FIELD_SIZE];
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Main app = new Main();
    }

    public Main() {
        initializeField();
        printField();
        placeShips();
        System.out.println("The game starts!");
        takeShot();
    }

    private void initializeField() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                field[i][j] = SEE_SECTION;
            }
        }
    }

    private void printField() {
        System.out.print("  ");
        for (int i = 0; i < field.length; i++) {
            System.out.printf("%d ", i + 1);
        }
        System.out.println();
        for (int i = 0; i < field.length; i++) {
            System.out.print((char) (65 + i) + " ");
            for (int j = 0; j < field[i].length; j++) {
                System.out.printf("%s ", field[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    private void placeShips() {
        requestCoordinatesAndCreateShip(Ship.AIRCRAFT_CARRIER);
        requestCoordinatesAndCreateShip(Ship.BATTLESHIP);
        requestCoordinatesAndCreateShip(Ship.SUBMARINE);
        requestCoordinatesAndCreateShip(Ship.CRUISER);
        requestCoordinatesAndCreateShip(Ship.DESTROYER);
    }

    private void requestCoordinatesAndCreateShip(Ship type) {
        System.out.printf("Enter the coordinates of the %s (%d cells):%n", type.getName(), type.getLength());
        boolean isShipCreated = false;
        while (!isShipCreated) {
            try {
                createShip(type, new Coordinate(scanner.next()), new Coordinate(scanner.next()));
                isShipCreated = true;
                printField();
            } catch (IllegalArgumentException e) {
                System.out.printf("%s Try again:%n", e.getMessage());
            }
        }
    }

    private void createShip(Ship ship, Coordinate head, Coordinate tail) {
        if (isHorizontalShip(head, tail)) {
            if (head.getColumn() > tail.getColumn()) {
                Coordinate temp = head;
                head = tail;
                tail = temp;
            }
            validateShipLength(ship, head.getColumn(), tail.getColumn());
            validateHorizontalShipLocation(head, tail);

            for (int i = head.getColumn(); i <= tail.getColumn(); i++) {
                field[head.getRow()][i] = SHIP_SECTION;
            }
        } else {
            if (head.getRow() > tail.getRow()) {
                Coordinate temp = head;
                head = tail;
                tail = temp;
            }
            validateShipLength(ship, head.getRow(), tail.getRow());
            validateVerticalShipLocation(head, tail);

            for (int i = head.getRow(); i <= tail.getRow(); i++) {
                field[i][head.getColumn()] = SHIP_SECTION;
            }
        }
    }

    private boolean isHorizontalShip(Coordinate head, Coordinate tail) {
        if (head.getRow() == tail.getRow()) {
            return true;
        } else if (head.getColumn() == tail.getColumn()) {
            return false;
        }
        throw new IllegalArgumentException("Error! Wrong ship location!");
    }

    private void validateShipLength(Ship ship, int head, int tail) {
        if (tail - head + 1 != ship.getLength()) {
            throw new IllegalArgumentException(String.format("Error! Wrong length of the %s!", ship.getName()));
        }
    }

    private void validateHorizontalShipLocation(Coordinate head, Coordinate tail) {
        if (head.getRow() != 0) {
            validateTopSideOfHorizontalShip(head, tail);
            if (head.getColumn() != 0) {
                validateTopLeftCorner(head);
            }
            if (tail.getColumn() != FIELD_SIZE - 1) {
                validateTopRightCorner(head);
            }
        }
        if (head.getRow() != FIELD_SIZE - 1) {
            validateBottomSideOfHorizontalShip(head, tail);
            if (head.getColumn() != 0) {
                validateBottomLeftCorner(head);
            }
            if (tail.getColumn() != 0) {
                validateBottomRightCorner(head);
            }
        }
        if (head.getColumn() != 0) {
            validateLeftSideOfHorizontalShip(head);
            if (head.getRow() != 0) {
                validateTopLeftCorner(head);
            }
            if (head.getRow() != FIELD_SIZE - 1) {
                validateBottomLeftCorner(head);
            }
        }
        if (tail.getColumn() != FIELD_SIZE - 1) {
            validateRightSideOfHorizontalShip(head);
            if (head.getRow() != 0) {
                validateTopRightCorner(tail);
            }
            if (tail.getRow() != FIELD_SIZE - 1) {
                validateBottomRightCorner(tail);
            }
        }
    }

    private void validateTopSideOfHorizontalShip(Coordinate head, Coordinate tail) {
        for (int i = head.getColumn(); i <= tail.getColumn(); i++) {
            if (field[head.getRow() - 1][i] == SHIP_SECTION) {
                throw new IllegalArgumentException(WRONG_PLACE_ERROR_TEXT);
            }
        }
    }

    private void validateBottomSideOfHorizontalShip(Coordinate head, Coordinate tail) {
        for (int i = head.getColumn(); i <= tail.getColumn(); i++) {
            if (field[head.getRow() + 1][i] == SHIP_SECTION) {
                throw new IllegalArgumentException(WRONG_PLACE_ERROR_TEXT);
            }
        }
    }

    private void validateLeftSideOfHorizontalShip(Coordinate head) {
        if (field[head.getRow()][head.getColumn() - 1] == SHIP_SECTION) {
            throw new IllegalArgumentException(WRONG_PLACE_ERROR_TEXT);
        }
    }

    private void validateRightSideOfHorizontalShip(Coordinate tail) {
        if (field[tail.getRow()][tail.getColumn() + 1] == SHIP_SECTION) {
            throw new IllegalArgumentException(WRONG_PLACE_ERROR_TEXT);
        }
    }

    private void validateTopLeftCorner(Coordinate head) {
        if (field[head.getRow() - 1][head.getColumn() - 1] == SHIP_SECTION) {
            throw new IllegalArgumentException(WRONG_PLACE_ERROR_TEXT);
        }
    }

    private void validateTopRightCorner(Coordinate tail) {
        if (field[tail.getRow() - 1][tail.getColumn() + 1] == SHIP_SECTION) {
            throw new IllegalArgumentException(WRONG_PLACE_ERROR_TEXT);
        }
    }

    private void validateBottomLeftCorner(Coordinate head) {
        if (field[head.getRow() + 1][head.getColumn() - 1] == SHIP_SECTION) {
            throw new IllegalArgumentException(WRONG_PLACE_ERROR_TEXT);
        }
    }

    private void validateBottomRightCorner(Coordinate tail) {
        if (field[tail.getRow() + 1][tail.getColumn() + 1] == SHIP_SECTION) {
            throw new IllegalArgumentException(WRONG_PLACE_ERROR_TEXT);
        }
    }

    private void validateVerticalShipLocation(Coordinate head, Coordinate tail) {
        if (head.getRow() != 0) {
            validateTopSideOfVerticalShip(head);
            if (head.getColumn() != 0) {
                validateTopLeftCornerOfVerticalShip(head);
            }
            if (head.getColumn() != FIELD_SIZE - 1) {
                validateTopRightCornerOfVerticalShip(head);
            }
        }

        if (tail.getRow() != FIELD_SIZE - 1) {
            validateBottomSideOfVerticalShip(tail);
            if (tail.getColumn() != 0) {
                validateBottomLeftCornerOfVerticalShip(tail);
            }
            if (tail.getColumn() != FIELD_SIZE - 1) {
                validateBottomRightCornerOfVerticalShip(tail);
            }
        }

        if (head.getColumn() != 0) {
            validateLeftSideOfVerticalShip(head, tail);
            if (head.getRow() != 0) {
                validateTopLeftCornerOfVerticalShip(head);
            }
            if (tail.getRow() != FIELD_SIZE - 1) {
                validateBottomLeftCornerOfVerticalShip(tail);
            }
        }

        if (head.getColumn() != FIELD_SIZE - 1) {
            validateRightSideOfVerticalShip(head, tail);
            if (tail.getRow() != 0) {
                validateTopRightCornerOfVerticalShip(tail);
            }
            if (tail.getRow() != FIELD_SIZE - 1) {
                validateBottomRightCornerOfVerticalShip(head);
            }
        }
    }

    private void validateTopSideOfVerticalShip(Coordinate head) {
        if (field[head.getRow() - 1][head.getColumn()] == SHIP_SECTION) {
            throw new IllegalArgumentException(WRONG_PLACE_ERROR_TEXT);
        }
    }

    private void validateBottomSideOfVerticalShip(Coordinate tail) {
        if (field[tail.getRow() + 1][tail.getColumn()] == SHIP_SECTION) {
            throw new IllegalArgumentException(WRONG_PLACE_ERROR_TEXT);
        }
    }

    private void validateLeftSideOfVerticalShip(Coordinate head, Coordinate tail) {
        for (int i = head.getRow(); i <= tail.getRow(); i++) {
            if (field[i][head.getColumn() - 1] == SHIP_SECTION) {
                throw new IllegalArgumentException(WRONG_PLACE_ERROR_TEXT);
            }
        }
    }

    private void validateRightSideOfVerticalShip(Coordinate head, Coordinate tail) {
        for (int i = head.getRow(); i <= tail.getRow(); i++) {
            if (field[i][head.getColumn() + 1] == SHIP_SECTION) {
                throw new IllegalArgumentException(WRONG_PLACE_ERROR_TEXT);
            }
        }
    }

    private void validateTopLeftCornerOfVerticalShip(Coordinate head) {
        if (field[head.getRow() - 1][head.getColumn() - 1] == SHIP_SECTION) {
            throw new IllegalArgumentException(WRONG_PLACE_ERROR_TEXT);
        }
    }

    private void validateTopRightCornerOfVerticalShip(Coordinate head) {
        if (field[head.getRow() - 1][head.getColumn() + 1] == SHIP_SECTION) {
            throw new IllegalArgumentException(WRONG_PLACE_ERROR_TEXT);
        }
    }

    private void validateBottomLeftCornerOfVerticalShip(Coordinate tail) {
        if (field[tail.getRow() + 1][tail.getColumn() - 1] == SHIP_SECTION) {
            throw new IllegalArgumentException(WRONG_PLACE_ERROR_TEXT);
        }
    }

    private void validateBottomRightCornerOfVerticalShip(Coordinate tail) {
        if (field[tail.getRow() + 1][tail.getColumn() + 1] == SHIP_SECTION) {
            throw new IllegalArgumentException(WRONG_PLACE_ERROR_TEXT);
        }
    }

    private void takeShot() {
        System.out.println("Take a shot!");
        boolean isShotDone = false;
        while (!isShotDone) {
            try {
                checkShot(new Coordinate(scanner.next()));
                isShotDone = true;
            } catch (IllegalArgumentException e) {
                System.out.printf("%s Try again:%n", e.getMessage());
            }
        }
    }

    private void checkShot(Coordinate coordinate) {
        if (field[coordinate.getRow()][coordinate.getColumn()] == SHIP_SECTION) {
            field[coordinate.getRow()][coordinate.getColumn()] = 'X';
            printField();
            System.out.println("You hit a ship!");
        } else {
            field[coordinate.getRow()][coordinate.getColumn()] = 'M';
            printField();
            System.out.println("You missed!");
        }
    }
}
