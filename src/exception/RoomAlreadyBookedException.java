package exception;

public class RoomAlreadyBookedException extends Exception {
    private final int roomNumber;
    public RoomAlreadyBookedException(int roomNumber) {
        super("Room #" + roomNumber + " is already booked. Please choose another room.");
        this.roomNumber = roomNumber;
    }
    public int getRoomNumber() { return roomNumber; }
}
