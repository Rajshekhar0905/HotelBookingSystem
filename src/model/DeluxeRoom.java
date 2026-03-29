package model;

/**
 * =========================================================
 *  OOP CONCEPTS: INHERITANCE + METHOD OVERRIDING
 * =========================================================
 *
 * Deluxe Room:
 *   - Inherits from Room.
 *   - Adds a 15% SERVICE CHARGE on top of the base bill.
 *   - Has extra amenities: minibar, balcony.
 *
 * POLYMORPHISM:
 *   - calculateBill() here adds service charge — different from Standard.
 *   - Same method name, completely different result.
 */
public class DeluxeRoom extends Room {

    private static final double SERVICE_CHARGE_PERCENT = 15.0;

    private final boolean hasMinibar;
    private final boolean hasBalcony;

    public DeluxeRoom(int roomNumber, int floor, boolean hasMinibar, boolean hasBalcony) {
        super(roomNumber, floor, 3000.0); // base price Rs.3000/night
        this.hasMinibar  = hasMinibar;
        this.hasBalcony  = hasBalcony;
    }

    // ── Overriding Abstract Methods ───────────────────────────────────────────

    @Override
    public String getRoomType() {
        return "Deluxe";
    }

    /**
     * Deluxe room: base bill + 15% service charge.
     */
    @Override
    public double calculateBill() {
        double base          = getBaseBill();
        double serviceCharge = base * (SERVICE_CHARGE_PERCENT / 100.0);
        return base + serviceCharge;
    }

    // ── DeluxeRoom-specific getters ───────────────────────────────────────────
    public boolean hasMinibar()  { return hasMinibar; }
    public boolean hasBalcony()  { return hasBalcony;  }
    public double  getServiceChargePercent() { return SERVICE_CHARGE_PERCENT; }

    @Override
    public String toString() {
        return super.toString() +
               String.format(" | Minibar: %s | Balcony: %s | +%.0f%% service charge",
                       hasMinibar ? "Yes" : "No",
                       hasBalcony ? "Yes" : "No",
                       SERVICE_CHARGE_PERCENT);
    }
}
