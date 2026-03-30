// Represents a guest staying at the hotel
public class Guest {

    private int guestId;
    private String name;
    private String phone;

    public Guest(int guestId, String name, String phone) {
        this.guestId = guestId;
        this.name = name;
        this.phone = phone;
    }

    public int getGuestId() {
        return guestId;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return "Guest [ID: " + guestId + ", Name: " + name + ", Phone: " + phone + "]";
    }
}
