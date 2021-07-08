package cardgame.exceptions;

public class PlayerFullException extends Exception{
    public PlayerFullException(int limit) {
        super("The Table is at its maximum capacity - " + limit);
    }
}
