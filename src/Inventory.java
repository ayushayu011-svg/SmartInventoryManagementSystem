import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

public class Inventory {
    private final ArrayList<Product> products = new ArrayList<>();
    private final Vector<Product> productCache = new Vector<>();
    private final ArrayList<Transaction> transactions = new ArrayList<>();
    private final Stack<Transaction> recentTransactions = new Stack<>();

    public synchronized void addProduct(Product product) {
        if (findProduct(product.getId()) != null) {
            throw new IllegalArgumentException("Product ID already exists.");
        }
        products.add(product);
        productCache.add(product);
    }

    public Product findProduct(int id) {
        for (Product p : products) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public List<Product> search(String text) {
        ArrayList<Product> result = new ArrayList<>();
        String key = text.toLowerCase();
        for (Product p : products) {
            if (p.getName().toLowerCase().contains(key) ||
                p.getCategory().toLowerCase().contains(key)) {
                result.add(p);
            }
        }
        return result;
    }

    public synchronized void stockIn(int id, int amount) {
        Product p = findProduct(id);
        if (p == null) throw new IllegalArgumentException("Product not found.");
        if (amount <= 0) throw new IllegalArgumentException("Quantity must be positive.");
        p.setQuantity(p.getQuantity() + amount);
        record(id, "STOCK_IN", amount);
    }

    public synchronized void stockOut(int id, int amount) {
        Product p = findProduct(id);
        if (p == null) throw new IllegalArgumentException("Product not found.");
        if (amount <= 0) throw new IllegalArgumentException("Quantity must be positive.");
        if (amount > p.getQuantity()) throw new IllegalArgumentException("Insufficient stock.");
        p.setQuantity(p.getQuantity() - amount);
        record(id, "STOCK_OUT", amount);
    }

    public synchronized void orderStockOut(int id, int amount) {
        Product p = findProduct(id);
        if (p == null) throw new IllegalArgumentException("Product not found.");
        if (amount <= 0) throw new IllegalArgumentException("Quantity must be positive.");
        if (amount > p.getQuantity()) throw new IllegalArgumentException("Insufficient stock.");
        p.setQuantity(p.getQuantity() - amount);
        record(id, "ORDER", amount);
    }

    private void record(int productId, String type, int quantity) {
        Transaction t = new Transaction(transactions.size() + 1, productId, type, quantity);
        transactions.add(t);
        recentTransactions.push(t);
    }

    public List<Product> lowStockProducts() {
        ArrayList<Product> result = new ArrayList<>();
        for (Product p : products) {
            if (p.isLowStock()) result.add(p);
        }
        return result;
    }

    public double totalValue() {
        double total = 0;
        for (Product p : products) total += p.getInventoryValue();
        return total;
    }

    public boolean deleteProduct(int id) {
        Product p = findProduct(id);
        if (p == null) return false;
        products.remove(p);
        productCache.remove(p);
        return true;
    }

    public void updateProduct(int id, String name, String category, double price, int quantity, int limit) {
        Product p = findProduct(id);
        if (p == null) throw new IllegalArgumentException("Product not found.");
        if (price <= 0 || quantity < 0 || limit < 0)
            throw new IllegalArgumentException("Invalid product values.");
        p.setName(name);
        p.setCategory(category);
        p.setPrice(price);
        p.setQuantity(quantity);
        p.setLowStockLimit(limit);
    }
}
