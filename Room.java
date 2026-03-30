// Represents a room in the hotel
public class Room {

    private int roomNumber;
    private String type;       // Single, Double, Suite
    private double pricePerNight;
    private boolean isAvailable;

    public Room(int roomNumber, String type, double pricePerNight) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.pricePerNight = pricePerNight;
        this.isAvailable = true;   // all rooms start as available
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getType() {
        return type;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    @Override
    public String toString() {
        String status = isAvailable ? "Available" : "Occupied";
        return "Room [No: " + roomNumber + ", Type: " + type
                + ", Price: Rs." + pricePerNight + "/night, Status: " + status + "]";
    }
}
