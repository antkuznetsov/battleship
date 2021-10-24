package battleship;

import java.util.Scanner;

public class Main {
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Main app = new Main();
    }

    public Main() {
        System.out.println("Player 1, place your ships on the game field");
        Field field1 = new Field();
        field1.printOpen();
        field1.placeShips();

        System.out.println("Press Enter and pass the move to another player");
        scanner.nextLine();

        System.out.println("Player 2, place your ships on the game field");
        Field field2 = new Field();
        field2.printOpen();
        field2.placeShips();

        System.out.println("Press Enter and pass the move to another player");
        scanner.nextLine();

        boolean isFirstPlayerMove = true;

        while (field1.areShipsLeft() || field2.areShipsLeft()) {
            if (isFirstPlayerMove) {
                field2.printHidden();
                System.out.println("---------------------");
                field1.printOpen();
                System.out.println("Player 1, it's your turn:");
                takeShot(field2);
            } else {
                field1.printHidden();
                System.out.println("---------------------");
                field2.printOpen();
                System.out.println("Player 2, it's your turn:");
                takeShot(field1);
            }
            isFirstPlayerMove = !isFirstPlayerMove;
        }
        System.out.println("You sank the last ship. You won. Congratulations!");
    }

    private void takeShot(Field field) {
        boolean isShotDone = false;
        while (!isShotDone) {
            try {
                field.checkShot(new Coordinate(scanner.nextLine()));
                isShotDone = true;
            } catch (IllegalArgumentException e) {
                System.out.printf("%s Try again:%n", e.getMessage());
            }
        }
    }
}
