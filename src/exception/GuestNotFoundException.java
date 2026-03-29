package exception;

public class GuestNotFoundException extends Exception {
    private final String guestId;
    public GuestNotFoundException(String guestId) {
        super("Guest with ID '" + guestId + "' not found in the system.");
        this.guestId = guestId;
    }
    public String getGuestId() { return guestId; }
}
