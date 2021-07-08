package cardgame.schemas;

public enum Suit {
    HEART((byte) 1), SPADE((byte) 2), DIAMOND((byte) 3), CLUB((byte) 4);
    private byte id;
    Suit(byte id) {
        this.id = id;
    }

    public byte getId() {
        return this.id;
    }
}
