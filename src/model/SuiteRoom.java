package model;

/**
 * =========================================================
 *  OOP CONCEPTS: INHERITANCE + METHOD OVERRIDING
 * =========================================================
 *
 * Suite Room — the most premium room type:
 *   - Inherits from Room.
 *   - Adds 15% service charge + 10% luxury tax on base bill.
 *   - Has private pool, butler service options.
 *
 * POLYMORPHISM:
 *   - calculateBill() applies both service charge AND luxury tax.
 *   - Most expensive billing formula of all three room types.
 */
public class SuiteRoom extends Room {

    private static final double SERVICE_CHARGE_PERCENT = 15.0;
    private static final double LUXURY_TAX_PERCENT     = 10.0;

    private final boolean hasPrivatePool;
    private final boolean hasButlerService;

    public SuiteRoom(int roomNumber, int floor, boolean hasPrivatePool, boolean hasButlerService) {
        super(roomNumber, floor, 7000.0); // base price Rs.7000/night
        this.hasPrivatePool    = hasPrivatePool;
        this.hasButlerService  = hasButlerService;
    }

    // ── Overriding Abstract Methods ───────────────────────────────────────────

    @Override
    public String getRoomType() {
        return "Suite";
    }

    /**
     * Suite room: base + 15% service charge + 10% luxury tax.
     * POLYMORPHISM: completely unique billing logic vs Standard/Deluxe.
     */
    @Override
    public double calculateBill() {
        double base          = getBaseBill();
        double serviceCharge = base * (SERVICE_CHARGE_PERCENT / 100.0);
        double luxuryTax     = base * (LUXURY_TAX_PERCENT / 100.0);
        return base + serviceCharge + luxuryTax;
    }

    // ── SuiteRoom-specific getters ────────────────────────────────────────────
    public boolean hasPrivatePool()   { return hasPrivatePool;   }
    public boolean hasButlerService() { return hasButlerService; }

    @Override
    public String toString() {
        return super.toString() +
               String.format(" | Pool: %s | Butler: %s | +%.0f%% service +%.0f%% luxury tax",
                       hasPrivatePool   ? "Yes" : "No",
                       hasButlerService ? "Yes" : "No",
                       SERVICE_CHARGE_PERCENT,
                       LUXURY_TAX_PERCENT);
    }
}
