import java.time.LocalDateTime;

public class Transaction {
    private final int id;
    private final int productId;
    private final String type;
    private final int quantity;
    private final LocalDateTime time;

    public Transaction(int id, int productId, String type, int quantity) {
        this.id = id;
        this.productId = productId;
        this.type = type;
        this.quantity = quantity;
        this.time = LocalDateTime.now();
    }

    public Transaction(int id, int productId, String type, int quantity, LocalDateTime time) {
        this.id = id;
        this.productId = productId;
        this.type = type;
        this.quantity = quantity;
        this.time = time;
    }

    public String toCsv() {
        return id + "," + productId + "," + type + "," + quantity + "," + time;
    }

    @Override
    public String toString() {
        return "Transaction ID: " + id + " | Product ID: " + productId +
               " | Type: " + type + " | Quantity: " + quantity + " | Time: " + time;
    }
}
