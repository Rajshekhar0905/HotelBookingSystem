package model;

/**
 * =========================================================
 *  OOP CONCEPTS: INHERITANCE + METHOD OVERRIDING
 * =========================================================
 *
 * INHERITANCE:
 *   - Inherits ALL fields and methods from Room (parent class).
 *   - Uses 'super(...)' to call the parent constructor.
 *   - Gets book(), cancel(), isAvailable() for FREE.
 *
 * METHOD OVERRIDING:
 *   - Overrides getRoomType() → returns "Standard"
 *   - Overrides calculateBill() → no extra charges, just base bill
 *
 * POLYMORPHISM IN ACTION:
 *   - When a Room reference points to StandardRoom, calling
 *     calculateBill() executes THIS version, not the abstract one.
 */
public class StandardRoom extends Room {

    private final boolean hasTV;
    private final boolean hasAC;

    public StandardRoom(int roomNumber, int floor, boolean hasTV, boolean hasAC) {
        super(roomNumber, floor, 1500.0); // base price Rs.1500/night
        this.hasTV = hasTV;
        this.hasAC = hasAC;
    }

    // ── Overriding Abstract Methods ───────────────────────────────────────────

    @Override
    public String getRoomType() {
        return "Standard";
    }

    /**
     * Standard room: No extra charges.
     * Bill = nights × pricePerNight
     */
    @Override
    public double calculateBill() {
        return getBaseBill(); // inherited protected method from Room
    }

    // ── StandardRoom-specific getters ─────────────────────────────────────────
    public boolean hasTV() { return hasTV; }
    public boolean hasAC() { return hasAC; }

    @Override
    public String toString() {
        return super.toString() +
               String.format(" | TV: %s | AC: %s", hasTV ? "Yes" : "No", hasAC ? "Yes" : "No");
    }
}
