package service;

import exception.*;
import model.*;

import java.time.LocalDate;
import java.util.*;

/**
 * =========================================================
 *  OOP CONCEPTS: ENCAPSULATION + ABSTRACTION + POLYMORPHISM
 *  EXCEPTION HANDLING: throws, try-catch-finally, multi-catch
 * =========================================================
 *
 * HotelService is the central brain of the system.
 * It manages rooms, guests, and bookings — and handles all
 * exceptional scenarios gracefully using Java exception hierarchy.
 */
public class HotelService {

    // ── Encapsulated State ─────────────────────────────────────────────────────
    private final String              hotelName;
    private final Map<Integer, Room>  rooms;       // roomNumber → Room
    private final Map<String, Guest>  guests;      // guestId    → Guest
    private final List<Booking>       bookings;    // all bookings log

    public HotelService(String hotelName) {
        this.hotelName = hotelName;
        this.rooms     = new HashMap<>();
        this.guests    = new HashMap<>();
        this.bookings  = new ArrayList<>();
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  ROOM MANAGEMENT
    // ─────────────────────────────────────────────────────────────────────────

    /** Add a room to the hotel inventory. */
    public void addRoom(Room room) {
        rooms.put(room.getRoomNumber(), room);
    }

    /**
     * Lookup a room by number.
     * EXCEPTION: throws RoomNotFoundException (CHECKED) if not found.
     */
    public Room findRoom(int roomNumber) throws RoomNotFoundException {
        Room room = rooms.get(roomNumber);
        if (room == null) {
            throw new RoomNotFoundException(roomNumber);
        }
        return room;
    }

    /** Display all rooms — demonstrates POLYMORPHISM via toString(). */
    public void showAllRooms() {
        System.out.println("\n  ===== ALL ROOMS IN " + hotelName.toUpperCase() + " =====");
        if (rooms.isEmpty()) {
            System.out.println("  No rooms registered yet.");
            return;
        }
        for (Room room : rooms.values()) {
            System.out.println("  " + room);  // polymorphic toString()
        }
    }

    /** Display only available rooms. */
    public void showAvailableRooms() {
        System.out.println("\n  ===== AVAILABLE ROOMS =====");
        boolean any = false;
        for (Room room : rooms.values()) {
            if (room.isAvailable()) {           // isAvailable() from Bookable interface
                System.out.println("  " + room);
                any = true;
            }
        }
        if (!any) System.out.println("  No rooms available at the moment.");
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  GUEST MANAGEMENT
    // ─────────────────────────────────────────────────────────────────────────

    /** Register a new guest. */
    public void registerGuest(Guest guest) {
        guests.put(guest.getGuestId(), guest);
        System.out.println("  ✔ Guest registered: " + guest);
    }

    /**
     * Lookup a guest by ID.
     * EXCEPTION: throws GuestNotFoundException (CHECKED).
     */
    public Guest findGuest(String guestId) throws GuestNotFoundException {
        Guest guest = guests.get(guestId);
        if (guest == null) {
            throw new GuestNotFoundException(guestId);
        }
        return guest;
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  BOOKING — Core method with FULL exception handling
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Books a room for a guest between given dates.
     *
     * EXCEPTION HANDLING demonstrated here:
     *  ✔ Multiple CHECKED exceptions declared with 'throws'
     *  ✔ Internally delegates booking to Room.book() which also throws
     *  ✔ Uses try-finally to guarantee cleanup on failure
     *
     * @throws RoomNotFoundException       if room number invalid
     * @throws GuestNotFoundException      if guest ID invalid
     * @throws RoomAlreadyBookedException  if room is occupied
     * @throws InvalidCheckoutDateException if dates are illogical
     */
    public Booking bookRoom(String guestId, int roomNumber,
                            LocalDate checkIn, LocalDate checkOut)
            throws RoomNotFoundException,
                   GuestNotFoundException,
                   RoomAlreadyBookedException,
                   InvalidCheckoutDateException {

        // Step 1: Find guest — may throw GuestNotFoundException
        Guest guest = findGuest(guestId);

        // Step 2: Find room — may throw RoomNotFoundException
        Room room = findRoom(roomNumber);

        // Step 3: Attempt booking — may throw RoomAlreadyBookedException
        //                                  or InvalidCheckoutDateException
        room.book(checkIn, checkOut);   // delegated to Room (and its subclass)
        room.setGuestName(guest.getName());

        // Step 4: Create and log the Booking object
        Booking booking = new Booking(guest, room, checkIn, checkOut);
        bookings.add(booking);

        System.out.println("\n  ✔ Booking Confirmed!");
        booking.printInvoice();
        return booking;
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  CANCELLATION
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Cancels a booking by booking ID.
     * Uses try-finally to ensure room is always freed.
     */
    public void cancelBooking(String bookingId) {
        Booking target = null;

        for (Booking b : bookings) {
            if (b.getBookingId().equals(bookingId) &&
                b.getStatus() == Booking.Status.CONFIRMED) {
                target = b;
                break;
            }
        }

        if (target == null) {
            System.out.println("  ✘ No active booking found with ID: " + bookingId);
            return;
        }

        try {
            // Mark booking cancelled and free the room
            target.setStatus(Booking.Status.CANCELLED);
            target.getRoom().cancel();  // from Bookable interface
            System.out.println("  ✔ Booking " + bookingId + " cancelled successfully.");
        } finally {
            // finally block: always runs — good for logging/cleanup
            System.out.println("  [LOG] Cancellation processed for booking: " + bookingId);
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  PAYMENT — demonstrates UNCHECKED exception
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Processes payment for a booking.
     * UNCHECKED EXCEPTION: InvalidPaymentException (RuntimeException)
     * thrown if payment amount is invalid — a programming error.
     */
    public void processPayment(Booking booking, double amountPaid) {
        // UNCHECKED — no 'throws' declaration needed, but we throw it
        if (amountPaid <= 0) {
            throw new InvalidPaymentException(amountPaid);
        }

        double bill = booking.getTotalBill(); // POLYMORPHIC call
        System.out.println("\n  ===== PAYMENT RECEIPT =====");
        System.out.printf ("  Booking   : %s%n",  booking.getBookingId());
        System.out.printf ("  Total Bill: Rs. %.2f%n", bill);
        System.out.printf ("  Paid      : Rs. %.2f%n", amountPaid);

        if (amountPaid >= bill) {
            double change = amountPaid - bill;
            System.out.printf("  Change    : Rs. %.2f%n", change);
            System.out.println("  Status    : PAYMENT SUCCESSFUL ✔");
        } else {
            double due = bill - amountPaid;
            System.out.printf("  Balance   : Rs. %.2f (PENDING)%n", due);
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  REPORTS
    // ─────────────────────────────────────────────────────────────────────────

    /** Shows all bookings in the system. */
    public void showAllBookings() {
        System.out.println("\n  ===== ALL BOOKINGS =====");
        if (bookings.isEmpty()) {
            System.out.println("  No bookings made yet.");
            return;
        }
        for (Booking b : bookings) {
            System.out.println("  " + b);
        }
    }

    /** Total revenue via POLYMORPHIC calculateBill() across all confirmed bookings. */
    public void showRevenueSummary() {
        double total = 0;
        int count    = 0;
        for (Booking b : bookings) {
            if (b.getStatus() == Booking.Status.CONFIRMED) {
                total += b.getTotalBill();  // polymorphic!
                count++;
            }
        }
        System.out.println("\n  ===== REVENUE SUMMARY =====");
        System.out.printf ("  Confirmed Bookings : %d%n", count);
        System.out.printf ("  Total Revenue      : Rs. %.2f%n", total);
    }

    /** Find a booking by its ID — returns null if not found. */
    public Booking findBookingById(String bookingId) {
        for (Booking b : bookings) {
            if (b.getBookingId().equals(bookingId)) return b;
        }
        return null;
    }

    public String getHotelName() { return hotelName; }
}
