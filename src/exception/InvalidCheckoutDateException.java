package exception;

import java.time.LocalDate;

public class InvalidCheckoutDateException extends Exception {
    private final LocalDate checkIn;
    private final LocalDate checkOut;
    public InvalidCheckoutDateException(LocalDate checkIn, LocalDate checkOut) {
        super("Invalid dates: Check-out (" + checkOut + ") must be after Check-in (" + checkIn + ").");
        this.checkIn  = checkIn;
        this.checkOut = checkOut;
    }
    public LocalDate getCheckIn()  { return checkIn;  }
    public LocalDate getCheckOut() { return checkOut; }
}
