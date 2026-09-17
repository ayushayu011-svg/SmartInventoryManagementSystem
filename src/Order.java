import java.time.LocalDateTime;

public class Order {
    private final int id;
    private final int productId;
    private final int quantity;
    private final double total;
    private final LocalDateTime time;

    public Order(int id, int productId, int quantity, double total) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.total = total;
        this.time = LocalDateTime.now();
    }

    public String toCsv() {
        return id + "," + productId + "," + quantity + "," + total + "," + time;
    }

    @Override
    public String toString() {
        return "Order ID: " + id + " | Product ID: " + productId +
               " | Quantity: " + quantity + " | Total: Rs." +
               String.format("%.2f", total) + " | Time: " + time;
    }
}
