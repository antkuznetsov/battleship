package battleship;

public enum Square {
    SEE('~'),
    SHIP('O'),
    HIT('X'),
    MISS('M');

    private char picture;

    Square(char picture) {
        this.picture = picture;
    }

    public char getPicture() {
        return picture;
    }
}
