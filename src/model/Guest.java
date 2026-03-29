package model;

/**
 * =========================================================
 *  OOP CONCEPT: ENCAPSULATION
 * =========================================================
 *
 * Guest class tightly encapsulates personal data:
 *   - All fields are PRIVATE.
 *   - ID proof and phone number are validated in setters.
 *   - Outside code cannot corrupt guest data arbitrarily.
 */
public class Guest {

    private final String guestId;    // unique ID e.g. "G001"
    private       String name;
    private       String phone;
    private       String email;
    private       String idProof;    // Aadhar / Passport number

    public Guest(String guestId, String name, String phone, String email, String idProof) {
        this.guestId = guestId;
        setName(name);
        setPhone(phone);
        this.email   = email;
        setIdProof(idProof);
    }

    // ── Validated Setters (Encapsulation) ─────────────────────────────────────

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Guest name cannot be empty.");
        }
        this.name = name.trim();
    }

    public void setPhone(String phone) {
        if (phone == null || !phone.matches("\\d{10}")) {
            throw new IllegalArgumentException(
                "Invalid phone number: '" + phone + "'. Must be 10 digits.");
        }
        this.phone = phone;
    }

    public void setIdProof(String idProof) {
        if (idProof == null || idProof.trim().length() < 6) {
            throw new IllegalArgumentException("ID proof must be at least 6 characters.");
        }
        this.idProof = idProof.trim();
    }

    public void setEmail(String email) { this.email = email; }

    // ── Getters ───────────────────────────────────────────────────────────────
    public String getGuestId() { return guestId; }
    public String getName()    { return name;    }
    public String getPhone()   { return phone;   }
    public String getEmail()   { return email;   }
    public String getIdProof() { return idProof; }

    @Override
    public String toString() {
        return String.format("Guest [%s] | Name: %s | Phone: %s | Email: %s",
                guestId, name, phone, email);
    }
}
