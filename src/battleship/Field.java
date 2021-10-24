package battleship;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static battleship.Square.*;

public class Field {
    private final Scanner scanner = new Scanner(System.in);
    private static final int SIZE = 10;

    private final Square[][] grid = new Square[SIZE][SIZE];
    private final Map<Coordinate, Ship> ships = new HashMap<>();

    public Field() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = SEE;
            }
        }
    }

    public void printOpen() {
        printField(false);
    }

    public void printHidden() {
        printField(true);
    }

    private void printField(boolean hideShips) {
        System.out.print("  ");
        for (int i = 0; i < grid.length; i++) {
            System.out.printf("%d ", i + 1);
        }
        System.out.println();
        for (int i = 0; i < grid.length; i++) {
            System.out.print((char) (65 + i) + " ");
            for (int j = 0; j < grid[i].length; j++) {
                if (hideShips && grid[i][j] == SHIP) {
                    System.out.printf("%s ", SEE.getPicture());
                } else {
                    System.out.printf("%s ", grid[i][j].getPicture());
                }
            }
            System.out.println();
        }
    }

    public void placeShips() {
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
                String[] coordinates = scanner.nextLine().split(" ");
                createShip(type, new Coordinate(coordinates[0]), new Coordinate(coordinates[1]));
                isShipCreated = true;
                printOpen();
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
            addHorizontalShip(ship, head, tail);  // Place a ship
        } else {
            if (head.getRow() > tail.getRow()) {
                Coordinate temp = head;
                head = tail;
                tail = temp;
            }
            validateShipLength(ship, head.getRow(), tail.getRow());
            addVerticalShip(ship, head, tail);  // Place a ship
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

    public void addHorizontalShip(Ship ship, Coordinate head, Coordinate tail) {
        validateHorizontalShip(head, tail);
        for (int i = head.getColumn(); i <= tail.getColumn(); i++) {
            grid[head.getRow()][i] = SHIP;
            ships.put(new Coordinate(head.getRow(), i), ship);
        }
    }

    public void addVerticalShip(Ship ship, Coordinate head, Coordinate tail) {
        validateVerticalShip(head, tail);
        for (int i = head.getRow(); i <= tail.getRow(); i++) {
            grid[i][head.getColumn()] = SHIP;
            ships.put(new Coordinate(i, head.getColumn()), ship);
        }
    }

    private void validateHorizontalShip(Coordinate head, Coordinate tail) {
        LocationValidator locationValidator =  new HorizontalLocationValidator(grid);
        locationValidator.validate(head, tail);
    }

    private void validateVerticalShip(Coordinate head, Coordinate tail) {
        LocationValidator locationValidator =  new VerticalLocationValidator(grid);
        locationValidator.validate(head, tail);
    }

    public void checkShot(Coordinate coordinate) {
        if (grid[coordinate.getRow()][coordinate.getColumn()] == SHIP) {
            grid[coordinate.getRow()][coordinate.getColumn()] = HIT;
            Ship ship = ships.get(coordinate);
            ships.remove(coordinate);
            printHidden();
            if (areShipsLeft()) {
                if (ships.containsValue(ship)) {
                    System.out.println("You hit a ship!");
                } else {
                    System.out.println("You sank a ship!");
                }
            } else {
                System.out.println("You sank the last ship. You won. Congratulations!");
                System.exit(0);
            }
        } else {
            if (grid[coordinate.getRow()][coordinate.getColumn()] != HIT) {
                grid[coordinate.getRow()][coordinate.getColumn()] = MISS;
            }
            printHidden();
            System.out.println("You missed!");
        }
        System.out.println("Press Enter and pass the move to another player");
        scanner.nextLine();
    }

    public boolean areShipsLeft() {
        return !ships.isEmpty();
    }
}
