package exception;

public class InvalidPaymentException extends RuntimeException {
    private final double amount;
    public InvalidPaymentException(double amount) {
        super("Invalid payment: Rs." + amount + ". Amount must be greater than zero.");
        this.amount = amount;
    }
    public double getAmount() { return amount; }
}
