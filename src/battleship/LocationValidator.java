package battleship;

public abstract class LocationValidator implements Validator {
    protected static final String WRONG_PLACE_ERROR_TEXT = "Error! You placed it too close to another one.";

    protected final Square[][] field;

    public LocationValidator(Square[][] field) {
        this.field = field;
    }

    @Override
    public abstract void validate(Coordinate head, Coordinate tail);
}
