package battleship;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static battleship.Square.*;

public class Main {
    private static final int FIELD_SIZE = 10;

    private final Square[][] field = new Square[FIELD_SIZE][FIELD_SIZE];
    private final Scanner scanner = new Scanner(System.in);

    private final Map<Coordinate, Ship> ships = new HashMap<>();

    public static void main(String[] args) {
        Main app = new Main();
    }

    public Main() {
        initializeField();
        printOpenField();
        placeShips();
        System.out.println("The game starts!");
        printHiddenField();
        while (!ships.isEmpty()) {
            takeShot();
        }
        System.out.println("You sank the last ship. You won. Congratulations!");
    }

    private void initializeField() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                field[i][j] = SEE;
            }
        }
    }

    private void printOpenField() {
        printField(false);
    }

    private void printHiddenField() {
        printField(true);
    }

    private void printField(boolean hideShips) {
        System.out.println();
        System.out.print("  ");
        for (int i = 0; i < field.length; i++) {
            System.out.printf("%d ", i + 1);
        }
        System.out.println();
        for (int i = 0; i < field.length; i++) {
            System.out.print((char) (65 + i) + " ");
            for (int j = 0; j < field[i].length; j++) {
                if (hideShips && field[i][j] == SHIP) {
                    System.out.printf("%s ", SEE.getPicture());
                } else {
                    System.out.printf("%s ", field[i][j].getPicture());
                }
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
                printOpenField();
            } catch (IllegalArgumentException e) {
                System.out.printf("%s Try again:%n", e.getMessage());
            }
        }
    }

    private void createShip(Ship ship, Coordinate head, Coordinate tail) {
        LocationValidator locationValidator;
        if (isHorizontalShip(head, tail)) {
            if (head.getColumn() > tail.getColumn()) {
                Coordinate temp = head;
                head = tail;
                tail = temp;
            }
            validateShipLength(ship, head.getColumn(), tail.getColumn());
            locationValidator = new HorizontalLocationValidator(field);
            locationValidator.validate(head, tail);

            // Place a ship
            for (int i = head.getColumn(); i <= tail.getColumn(); i++) {
                field[head.getRow()][i] = SHIP;
                ships.put(new Coordinate(head.getRow(), i), ship);
            }
        } else {
            if (head.getRow() > tail.getRow()) {
                Coordinate temp = head;
                head = tail;
                tail = temp;
            }
            validateShipLength(ship, head.getRow(), tail.getRow());
            locationValidator = new VerticalLocationValidator(field);
            locationValidator.validate(head, tail);

            for (int i = head.getRow(); i <= tail.getRow(); i++) {
                field[i][head.getColumn()] = SHIP;
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

    private void takeShot() {
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
        if (field[coordinate.getRow()][coordinate.getColumn()] == SHIP) {
            field[coordinate.getRow()][coordinate.getColumn()] = HIT;
            Ship ship = ships.get(coordinate);
            ships.remove(coordinate);
            printHiddenField();
            if (!ships.isEmpty()) {
                if (ships.containsValue(ship)) {
                    System.out.println("You hit a ship! Try again:");
                } else {
                    System.out.println("You sank a ship! Specify a new target:");
                }
            }
        } else {
            if (field[coordinate.getRow()][coordinate.getColumn()] != HIT) {
                field[coordinate.getRow()][coordinate.getColumn()] = MISS;
            }
            printHiddenField();
            System.out.println("You missed. Try again:");
        }
    }
}
