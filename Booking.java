// A booking ties a guest to a specific room for a number of nights
public class Booking {

    private int bookingId;
    private Guest guest;
    private Room room;
    private int nights;
    private double totalCost;

    public Booking(int bookingId, Guest guest, Room room, int nights) {
        this.bookingId = bookingId;
        this.guest = guest;
        this.room = room;
        this.nights = nights;
        this.totalCost = room.getPricePerNight() * nights;
    }

    public int getBookingId() {
        return bookingId;
    }

    public Guest getGuest() {
        return guest;
    }

    public Room getRoom() {
        return room;
    }

    public int getNights() {
        return nights;
    }

    public double getTotalCost() {
        return totalCost;
    }

    @Override
    public String toString() {
        return "Booking [ID: " + bookingId
                + ", Guest: " + guest.getName()
                + ", Room No: " + room.getRoomNumber()
                + ", Nights: " + nights
                + ", Total Cost: Rs." + totalCost + "]";
    }
}
