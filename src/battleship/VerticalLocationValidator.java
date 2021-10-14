package battleship;

import static battleship.Square.SHIP;

public class VerticalLocationValidator extends LocationValidator {

    public VerticalLocationValidator(Square[][] field) {
        super(field);
    }

    @Override
    public void validate(Coordinate head, Coordinate tail) {
        if (head.getRow() != 0) {
            validateTopSide(head);
            if (head.getColumn() != 0) {
                validateTopLeftCorner(head);
            }
            if (head.getColumn() != field.length - 1) {
                validateTopRightCorner(head);
            }
        }

        if (tail.getRow() != field.length - 1) {
            validateBottomSide(tail);
            if (tail.getColumn() != 0) {
                validateBottomLeftCorner(tail);
            }
            if (tail.getColumn() != field.length - 1) {
                validateBottomRightCorner(tail);
            }
        }

        if (head.getColumn() != 0) {
            validateLeftSide(head, tail);
            if (head.getRow() != 0) {
                validateTopLeftCorner(head);
            }
            if (tail.getRow() != field.length - 1) {
                validateBottomLeftCorner(tail);
            }
        }

        if (head.getColumn() != field.length - 1) {
            validateRightSide(head, tail);
            if (tail.getRow() != 0) {
                validateTopRightCorner(tail);
            }
            if (tail.getRow() != field.length - 1) {
                validateBottomRightCorner(head);
            }
        }
    }

    private void validateTopSide(Coordinate head) {
        if (field[head.getRow() - 1][head.getColumn()] == SHIP) {
            throw new IllegalArgumentException(WRONG_PLACE_ERROR_TEXT);
        }
    }

    private void validateBottomSide(Coordinate tail) {
        if (field[tail.getRow() + 1][tail.getColumn()] == SHIP) {
            throw new IllegalArgumentException(WRONG_PLACE_ERROR_TEXT);
        }
    }

    private void validateLeftSide(Coordinate head, Coordinate tail) {
        for (int i = head.getRow(); i <= tail.getRow(); i++) {
            if (field[i][head.getColumn() - 1] == SHIP) {
                throw new IllegalArgumentException(WRONG_PLACE_ERROR_TEXT);
            }
        }
    }

    private void validateRightSide(Coordinate head, Coordinate tail) {
        for (int i = head.getRow(); i <= tail.getRow(); i++) {
            if (field[i][head.getColumn() + 1] == SHIP) {
                throw new IllegalArgumentException(WRONG_PLACE_ERROR_TEXT);
            }
        }
    }

    private void validateTopLeftCorner(Coordinate head) {
        if (field[head.getRow() - 1][head.getColumn() - 1] == SHIP) {
            throw new IllegalArgumentException(WRONG_PLACE_ERROR_TEXT);
        }
    }

    private void validateTopRightCorner(Coordinate head) {
        if (field[head.getRow() - 1][head.getColumn() + 1] == SHIP) {
            throw new IllegalArgumentException(WRONG_PLACE_ERROR_TEXT);
        }
    }

    private void validateBottomLeftCorner(Coordinate tail) {
        if (field[tail.getRow() + 1][tail.getColumn() - 1] == SHIP) {
            throw new IllegalArgumentException(WRONG_PLACE_ERROR_TEXT);
        }
    }

    private void validateBottomRightCorner(Coordinate tail) {
        if (field[tail.getRow() + 1][tail.getColumn() + 1] == SHIP) {
            throw new IllegalArgumentException(WRONG_PLACE_ERROR_TEXT);
        }
    }
}
