package exception;

public class RoomAlreadyBookedException extends Exception {
    public RoomAlreadyBookedException(String message) {
        super(message);
    }

    public RoomAlreadyBookedException(String message, Throwable cause) {
        super(message, cause);
    }
}
