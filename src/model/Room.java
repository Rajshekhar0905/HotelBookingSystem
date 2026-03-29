package model;

import exception.InvalidCheckoutDateException;
import exception.RoomAlreadyBookedException;
import service.Bookable;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * =========================================================
 *  OOP CONCEPTS: ABSTRACT CLASS + ENCAPSULATION
 * =========================================================
 *
 * ABSTRACT CLASS:
 *   - Cannot be instantiated directly ("new Room()" is illegal).
 *   - Provides common fields and concrete methods shared by all rooms.
 *   - Forces subclasses to implement abstract methods: getRoomType()
 *     and calculateBill() — each room type bills differently.
 *
 * ENCAPSULATION:
 *   - All fields are PRIVATE — no direct access from outside.
 *   - Controlled access via getters; only safe setters are exposed.
 *
 * IMPLEMENTS Bookable (Interface):
 *   - Fulfills the contract: book(), cancel(), isAvailable()
 */
public abstract class Room implements Bookable {

    // ── Encapsulated Fields ───────────────────────────────────────────────────
    private final int    roomNumber;
    private final int    floor;
    private       double pricePerNight;
    private       boolean booked;
    private       LocalDate checkInDate;
    private       LocalDate checkOutDate;
    private       String    guestName;   // who currently booked this room

    // ── Constructor ───────────────────────────────────────────────────────────
    public Room(int roomNumber, int floor, double pricePerNight) {
        this.roomNumber     = roomNumber;
        this.floor          = floor;
        this.pricePerNight  = pricePerNight;
        this.booked         = false;
    }

    // ── Abstract Methods (Subclasses MUST override) ───────────────────────────
    /**
     * Returns the type label of this room e.g. "Standard", "Deluxe", "Suite"
     * POLYMORPHISM: same call, different result per subclass.
     */
    public abstract String getRoomType();

    /**
     * Each room type may have its own billing formula.
     * e.g., Suite adds a luxury tax, Deluxe adds a service charge.
     */
    public abstract double calculateBill();

    // ── Concrete Methods (Shared by all rooms) ────────────────────────────────

    /**
     * EXCEPTION HANDLING — Checked Exceptions:
     *  - RoomAlreadyBookedException : if room is occupied
     *  - InvalidCheckoutDateException: if dates are illogical
     */
    @Override
    public void book(LocalDate checkIn, LocalDate checkOut)
            throws RoomAlreadyBookedException, InvalidCheckoutDateException {

        // Guard: already booked?
        if (this.booked) {
            throw new RoomAlreadyBookedException(roomNumber);
        }

        // Guard: checkout must be after checkin
        if (!checkOut.isAfter(checkIn)) {
            throw new InvalidCheckoutDateException(checkIn, checkOut);
        }

        this.booked       = true;
        this.checkInDate  = checkIn;
        this.checkOutDate = checkOut;
    }

    @Override
    public void cancel() {
        this.booked       = false;
        this.checkInDate  = null;
        this.checkOutDate = null;
        this.guestName    = null;
    }

    @Override
    public boolean isAvailable() {
        return !booked;
    }

    /** Helper used by subclasses to compute base bill (nights × price). */
    protected double getBaseBill() {
        if (checkInDate == null || checkOutDate == null) return 0;
        long nights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        return nights * pricePerNight;
    }

    // ── Getters ───────────────────────────────────────────────────────────────
    public int       getRoomNumber()   { return roomNumber;    }
    public int       getFloor()        { return floor;         }
    public double    getPricePerNight(){ return pricePerNight; }
    public boolean   isBooked()        { return booked;        }
    public LocalDate getCheckInDate()  { return checkInDate;   }
    public LocalDate getCheckOutDate() { return checkOutDate;  }
    public String    getGuestName()    { return guestName;     }

    // ── Setter (controlled) ───────────────────────────────────────────────────
    public void setGuestName(String guestName) { this.guestName = guestName; }
    public void setPricePerNight(double price)  { this.pricePerNight = price; }

    // ── toString ──────────────────────────────────────────────────────────────
    @Override
    public String toString() {
        return String.format("Room #%d [%s] | Floor %d | Rs.%.0f/night | %s",
                roomNumber, getRoomType(), floor, pricePerNight,
                booked ? "BOOKED by " + guestName : "AVAILABLE");
    }
}
