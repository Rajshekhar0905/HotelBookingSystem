import java.util.ArrayList;

// This is the main manager class — handles all hotel operations
public class Hotel {

    private ArrayList<Guest> guests;
    private ArrayList<Room> rooms;
    private ArrayList<Booking> bookings;

    private int nextGuestId = 1;
    private int nextBookingId = 1;

    public Hotel() {
        guests = new ArrayList<>();
        rooms = new ArrayList<>();
        bookings = new ArrayList<>();
    }

    // ─── ADD GUEST ──────────────────────────────────────────────────────────────

    public void addGuest(String name, String phone) {
        Guest g = new Guest(nextGuestId++, name, phone);
        guests.add(g);
        System.out.println("Guest added: " + g);
    }

    // ─── ADD ROOM ───────────────────────────────────────────────────────────────

    public void addRoom(int roomNumber, String type, double price) {
        // make sure the room number doesn't already exist
        for (Room r : rooms) {
            if (r.getRoomNumber() == roomNumber) {
                System.out.println("Room " + roomNumber + " already exists!");
                return;
            }
        }
        Room room = new Room(roomNumber, type, price);
        rooms.add(room);
        System.out.println("Room added: " + room);
    }

    // ─── ALLOCATE ROOM ──────────────────────────────────────────────────────────

    public void allocateRoom(int guestId, int roomNumber, int nights) {
        Guest foundGuest = findGuest(guestId);
        if (foundGuest == null) {
            System.out.println("No guest found with ID " + guestId);
            return;
        }

        Room foundRoom = findRoom(roomNumber);
        if (foundRoom == null) {
            System.out.println("No room found with number " + roomNumber);
            return;
        }

        if (!foundRoom.isAvailable()) {
            System.out.println("Room " + roomNumber + " is already occupied.");
            return;
        }

        // mark room as occupied and create a booking
        foundRoom.setAvailable(false);
        Booking booking = new Booking(nextBookingId++, foundGuest, foundRoom, nights);
        bookings.add(booking);
        System.out.println("Room allocated successfully! " + booking);
    }

    // ─── CHECKOUT GUEST ─────────────────────────────────────────────────────────

    public void checkoutGuest(int guestId) {
        // find the active booking for this guest
        Booking activeBooking = null;
        for (Booking b : bookings) {
            if (b.getGuest().getGuestId() == guestId) {
                activeBooking = b;
                break;
            }
        }

        if (activeBooking == null) {
            System.out.println("No active booking found for guest ID " + guestId);
            return;
        }

        // free up the room
        activeBooking.getRoom().setAvailable(true);
        bookings.remove(activeBooking);

        System.out.println("Guest " + activeBooking.getGuest().getName()
                + " checked out from Room " + activeBooking.getRoom().getRoomNumber()
                + ". Total bill: Rs." + activeBooking.getTotalCost());
    }

    // ─── DISPLAY METHODS ────────────────────────────────────────────────────────

    public void displayAllRooms() {
        if (rooms.isEmpty()) {
            System.out.println("No rooms added yet.");
            return;
        }
        System.out.println("\n--- All Rooms ---");
        for (Room r : rooms) {
            System.out.println(r);
        }
    }

    public void displayAvailableRooms() {
        System.out.println("\n--- Available Rooms ---");
        boolean any = false;
        for (Room r : rooms) {
            if (r.isAvailable()) {
                System.out.println(r);
                any = true;
            }
        }
        if (!any) {
            System.out.println("No rooms available at the moment.");
        }
    }

    public void displayAllBookings() {
        if (bookings.isEmpty()) {
            System.out.println("No active bookings right now.");
            return;
        }
        System.out.println("\n--- Active Bookings ---");
        for (Booking b : bookings) {
            System.out.println(b);
        }
    }

    // ─── HELPER METHODS ─────────────────────────────────────────────────────────

    private Guest findGuest(int guestId) {
        for (Guest g : guests) {
            if (g.getGuestId() == guestId) {
                return g;
            }
        }
        return null;
    }

    private Room findRoom(int roomNumber) {
        for (Room r : rooms) {
            if (r.getRoomNumber() == roomNumber) {
                return r;
            }
        }
        return null;
    }
}
