public class Product {
    private final int id;
    private String name;
    private String category;
    private double price;
    private int quantity;
    private int lowStockLimit;

    public Product(int id, String name, String category, double price, int quantity, int lowStockLimit) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.lowStockLimit = lowStockLimit;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public int getLowStockLimit() { return lowStockLimit; }

    public void setName(String name) { this.name = name; }
    public void setCategory(String category) { this.category = category; }
    public void setPrice(double price) { this.price = price; }
    public synchronized void setQuantity(int quantity) { this.quantity = quantity; }
    public void setLowStockLimit(int limit) { this.lowStockLimit = limit; }

    public boolean isLowStock() {
        return quantity <= lowStockLimit;
    }

    public double getInventoryValue() {
        return price * quantity;
    }

    public String toCsv() {
        return id + "," + clean(name) + "," + clean(category) + "," + price + "," + quantity + "," + lowStockLimit;
    }

    private String clean(String value) {
        return value.replace(",", " ");
    }

    @Override
    public String toString() {
        return String.format("%-5d %-22s %-15s %10.2f %8d %10d",
                id, name, category, price, quantity, lowStockLimit);
    }
}
