package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * =========================================================
 *  OOP CONCEPT: ENCAPSULATION + ASSOCIATION
 * =========================================================
 *
 * Booking ties together a Guest and a Room for a date range.
 * - All fields private, accessed through getters.
 * - Has-A relationship with Guest and Room (Association).
 * - Internally tracks booking status (CONFIRMED / CANCELLED).
 */
public class Booking {

    public enum Status { CONFIRMED, CANCELLED }

    private static int counter = 1000; // auto-increment booking ID

    private final String    bookingId;
    private final Guest     guest;
    private final Room      room;
    private final LocalDate checkIn;
    private final LocalDate checkOut;
    private final LocalDateTime bookedAt;
    private       Status    status;

    public Booking(Guest guest, Room room, LocalDate checkIn, LocalDate checkOut) {
        this.bookingId = "BKG" + (++counter);
        this.guest     = guest;
        this.room      = room;
        this.checkIn   = checkIn;
        this.checkOut  = checkOut;
        this.bookedAt  = LocalDateTime.now();
        this.status    = Status.CONFIRMED;
    }

    // ── Getters ───────────────────────────────────────────────────────────────
    public String    getBookingId() { return bookingId; }
    public Guest     getGuest()     { return guest;     }
    public Room      getRoom()      { return room;      }
    public LocalDate getCheckIn()   { return checkIn;   }
    public LocalDate getCheckOut()  { return checkOut;  }
    public Status    getStatus()    { return status;    }

    public void setStatus(Status status) { this.status = status; }

    // ── Bill via POLYMORPHISM ─────────────────────────────────────────────────
    public double getTotalBill() {
        return room.calculateBill(); // polymorphic call!
    }

    // ── Invoice Printer ───────────────────────────────────────────────────────
    public void printInvoice() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
        System.out.println("\n  ╔══════════════════════════════════════════════╗");
        System.out.println("  ║         HOTEL PARADISE — INVOICE             ║");
        System.out.println("  ╠══════════════════════════════════════════════╣");
        System.out.printf ("  ║  Booking ID  : %-29s║%n", bookingId);
        System.out.printf ("  ║  Booked At   : %-29s║%n",
                bookedAt.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm")));
        System.out.println("  ╠══════════════════════════════════════════════╣");
        System.out.printf ("  ║  Guest       : %-29s║%n", guest.getName());
        System.out.printf ("  ║  Guest ID    : %-29s║%n", guest.getGuestId());
        System.out.printf ("  ║  Phone       : %-29s║%n", guest.getPhone());
        System.out.println("  ╠══════════════════════════════════════════════╣");
        System.out.printf ("  ║  Room No.    : %-29s║%n", room.getRoomNumber());
        System.out.printf ("  ║  Room Type   : %-29s║%n", room.getRoomType());
        System.out.printf ("  ║  Check-In    : %-29s║%n", checkIn.format(fmt));
        System.out.printf ("  ║  Check-Out   : %-29s║%n", checkOut.format(fmt));
        System.out.println("  ╠══════════════════════════════════════════════╣");
        System.out.printf ("  ║  Total Bill  : Rs. %-25.2f║%n", getTotalBill());
        System.out.printf ("  ║  Status      : %-29s║%n", status);
        System.out.println("  ╚══════════════════════════════════════════════╝");
    }

    @Override
    public String toString() {
        return String.format("[%s] Guest: %s | Room: #%d (%s) | %s to %s | Rs.%.0f | %s",
                bookingId, guest.getName(), room.getRoomNumber(), room.getRoomType(),
                checkIn, checkOut, getTotalBill(), status);
    }
}
