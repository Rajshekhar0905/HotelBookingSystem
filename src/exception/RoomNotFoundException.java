package exception;

public class RoomNotFoundException extends Exception {
    private final int roomNumber;
    public RoomNotFoundException(int roomNumber) {
        super("Room #" + roomNumber + " does not exist in this hotel.");
        this.roomNumber = roomNumber;
    }
    public int getRoomNumber() { return roomNumber; }
}
