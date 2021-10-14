package battleship;

import static battleship.Square.SHIP;

public class HorizontalLocationValidator extends LocationValidator {

    public HorizontalLocationValidator(Square[][] field) {
        super(field);
    }

    @Override
    public void validate(Coordinate head, Coordinate tail) {
        if (head.getRow() != 0) {
            validateTopSide(head, tail);
            if (head.getColumn() != 0) {
                validateTopLeftCorner(head);
            }
            if (tail.getColumn() != field.length - 1) {
                validateTopRightCorner(head);
            }
        }
        if (head.getRow() != field.length - 1) {
            validateBottomSide(head, tail);
            if (head.getColumn() != 0) {
                validateBottomLeftCorner(head);
            }
            if (tail.getColumn() != 0) {
                validateBottomRightCorner(head);
            }
        }
        if (head.getColumn() != 0) {
            validateLeftSide(head);
            if (head.getRow() != 0) {
                validateTopLeftCorner(head);
            }
            if (head.getRow() != field.length - 1) {
                validateBottomLeftCorner(head);
            }
        }
        if (tail.getColumn() != field.length - 1) {
            validateRightSide(head);
            if (head.getRow() != 0) {
                validateTopRightCorner(tail);
            }
            if (tail.getRow() != field.length - 1) {
                validateBottomRightCorner(tail);
            }
        }
    }

    private void validateTopSide(Coordinate head, Coordinate tail) {
        for (int i = head.getColumn(); i <= tail.getColumn(); i++) {
            if (field[head.getRow() - 1][i] == SHIP) {
                throw new IllegalArgumentException(WRONG_PLACE_ERROR_TEXT);
            }
        }
    }

    private void validateBottomSide(Coordinate head, Coordinate tail) {
        for (int i = head.getColumn(); i <= tail.getColumn(); i++) {
            if (field[head.getRow() + 1][i] == SHIP) {
                throw new IllegalArgumentException(WRONG_PLACE_ERROR_TEXT);
            }
        }
    }

    private void validateLeftSide(Coordinate head) {
        if (field[head.getRow()][head.getColumn() - 1] == SHIP) {
            throw new IllegalArgumentException(WRONG_PLACE_ERROR_TEXT);
        }
    }

    private void validateRightSide(Coordinate tail) {
        if (field[tail.getRow()][tail.getColumn() + 1] == SHIP) {
            throw new IllegalArgumentException(WRONG_PLACE_ERROR_TEXT);
        }
    }

    private void validateTopLeftCorner(Coordinate head) {
        if (field[head.getRow() - 1][head.getColumn() - 1] == SHIP) {
            throw new IllegalArgumentException(WRONG_PLACE_ERROR_TEXT);
        }
    }

    private void validateTopRightCorner(Coordinate tail) {
        if (field[tail.getRow() - 1][tail.getColumn() + 1] == SHIP) {
            throw new IllegalArgumentException(WRONG_PLACE_ERROR_TEXT);
        }
    }

    private void validateBottomLeftCorner(Coordinate head) {
        if (field[head.getRow() + 1][head.getColumn() - 1] == SHIP) {
            throw new IllegalArgumentException(WRONG_PLACE_ERROR_TEXT);
        }
    }

    private void validateBottomRightCorner(Coordinate tail) {
        if (field[tail.getRow() + 1][tail.getColumn() + 1] == SHIP) {
            throw new IllegalArgumentException(WRONG_PLACE_ERROR_TEXT);
        }
    }
}
