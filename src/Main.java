import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final Inventory inventory = new Inventory();
    private static final InventoryFile file = new InventoryFile();
    private static final ArrayList<Order> orders = new ArrayList<>();

    public static void main(String[] args) {
        for (Product p : file.loadProducts()) inventory.addProduct(p);

        System.out.println("==============================================");
        System.out.println("     SMART INVENTORY MANAGEMENT SYSTEM");
        System.out.println("==============================================");

        boolean running = true;
        while (running) {
            System.out.println("\n1. Product Management");
            System.out.println("2. Stock Management");
            System.out.println("3. Order Management");
            System.out.println("4. Reports");
            System.out.println("5. Exit");

            int choice = readInt("Enter choice: ");

            try {
                switch (choice) {
                    case 1 -> productMenu();
                    case 2 -> stockMenu();
                    case 3 -> orderMenu();
                    case 4 -> reportMenu();
                    case 5 -> running = false;
                    default -> System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        System.out.println("Thank you for using the system.");
    }

    private static void productMenu() {
        System.out.println("\n--- Product Management ---");
        System.out.println("1. Add Product");
        System.out.println("2. View Products");
        System.out.println("3. Search Product");
        System.out.println("4. Update Product");
        System.out.println("5. Delete Product");

        switch (readInt("Enter choice: ")) {
            case 1 -> addProduct();
            case 2 -> viewProducts();
            case 3 -> searchProduct();
            case 4 -> updateProduct();
            case 5 -> deleteProduct();
            default -> System.out.println("Invalid choice.");
        }
    }

    private static void addProduct() {
        int id = readPositiveInt("Product ID: ");
        String name = readText("Name: ");
        String category = readText("Category: ");
        double price = readPositiveDouble("Price: ");
        int quantity = readNonNegativeInt("Quantity: ");
        int limit = readNonNegativeInt("Low-stock limit: ");

        inventory.addProduct(new Product(id, name, category, price, quantity, limit));
        save();
        System.out.println("Product added successfully.");
    }

    private static void viewProducts() {
        if (inventory.getProducts().isEmpty()) {
            System.out.println("No products available.");
            return;
        }
        System.out.printf("%-5s %-22s %-15s %10s %8s %10s%n",
                "ID", "Name", "Category", "Price", "Qty", "Low Limit");
        for (Product p : inventory.getProducts()) System.out.println(p);
    }

    private static void searchProduct() {
        String text = readText("Enter name or category to search: ");
        List<Product> result = inventory.search(text);
        if (result.isEmpty()) System.out.println("No matching products.");
        else for (Product p : result) System.out.println(p);
    }

    private static void updateProduct() {
        int id = readPositiveInt("Product ID to update: ");
        String name = readText("New name: ");
        String category = readText("New category: ");
        double price = readPositiveDouble("New price: ");
        int quantity = readNonNegativeInt("New quantity: ");
        int limit = readNonNegativeInt("New low-stock limit: ");

        inventory.updateProduct(id, name, category, price, quantity, limit);
        save();
        System.out.println("Product updated successfully.");
    }

    private static void deleteProduct() {
        int id = readPositiveInt("Product ID to delete: ");
        if (inventory.deleteProduct(id)) {
            save();
            System.out.println("Product deleted successfully.");
        } else {
            System.out.println("Product not found.");
        }
    }

    private static void stockMenu() {
        System.out.println("\n--- Stock Management ---");
        System.out.println("1. Stock In");
        System.out.println("2. Stock Out");
        System.out.println("3. Low Stock Products");

        switch (readInt("Enter choice: ")) {
            case 1 -> {
                inventory.stockIn(readPositiveInt("Product ID: "), readPositiveInt("Quantity: "));
                save();
                System.out.println("Stock added successfully.");
            }
            case 2 -> {
                inventory.stockOut(readPositiveInt("Product ID: "), readPositiveInt("Quantity: "));
                save();
                System.out.println("Stock removed successfully.");
            }
            case 3 -> {
                List<Product> low = inventory.lowStockProducts();
                if (low.isEmpty()) System.out.println("No low-stock products.");
                else for (Product p : low) System.out.println(p);
            }
            default -> System.out.println("Invalid choice.");
        }
    }

    private static void orderMenu() {
        System.out.println("\n--- Order Management ---");
        System.out.println("1. Place Order");
        System.out.println("2. Check Availability");
        System.out.println("3. Order History");

        switch (readInt("Enter choice: ")) {
            case 1 -> placeOrder();
            case 2 -> checkAvailability();
            case 3 -> {
                if (orders.isEmpty()) System.out.println("No orders in this session.");
                else for (Order o : orders) System.out.println(o);
            }
            default -> System.out.println("Invalid choice.");
        }
    }

    private static void placeOrder() {
        int productId = readPositiveInt("Product ID: ");
        int quantity = readPositiveInt("Quantity: ");
        Product p = inventory.findProduct(productId);

        if (p == null) throw new IllegalArgumentException("Product not found.");
        if (quantity > p.getQuantity()) throw new IllegalArgumentException("Insufficient stock.");

        inventory.orderStockOut(productId, quantity);
        Order order = new Order(orders.size() + 1, productId, quantity, p.getPrice() * quantity);
        orders.add(order);
        save();
        System.out.println("Order placed successfully.");
        System.out.println(order);
    }

    private static void checkAvailability() {
        int id = readPositiveInt("Product ID: ");
        Product p = inventory.findProduct(id);
        if (p == null) System.out.println("Product not found.");
        else System.out.println("Available quantity: " + p.getQuantity());
    }

    private static void reportMenu() {
        System.out.println("\n--- Reports ---");
        System.out.println("1. Inventory Summary");
        System.out.println("2. Low Stock Report");
        System.out.println("3. Transaction History");
        System.out.println("4. Inventory Value");

        switch (readInt("Enter choice: ")) {
            case 1 -> {
                System.out.println("Products: " + inventory.getProducts().size());
                int units = 0;
                for (Product p : inventory.getProducts()) units += p.getQuantity();
                System.out.println("Total units: " + units);
                System.out.printf("Inventory value: Rs. %.2f%n", inventory.totalValue());
            }
            case 2 -> {
                List<Product> low = inventory.lowStockProducts();
                if (low.isEmpty()) System.out.println("No low-stock products.");
                else for (Product p : low) System.out.println(p);
            }
            case 3 -> {
                if (inventory.getTransactions().isEmpty()) System.out.println("No transactions.");
                else for (Transaction t : inventory.getTransactions()) System.out.println(t);
            }
            case 4 -> System.out.printf("Total inventory value: Rs. %.2f%n", inventory.totalValue());
            default -> System.out.println("Invalid choice.");
        }
    }

    private static void save() {
        file.saveProducts(inventory.getProducts());
        file.saveTransactions(inventory.getTransactions());
        file.saveOrders(orders);
    }

    private static String readText(String message) {
        while (true) {
            System.out.print(message);
            String s = sc.nextLine().trim();
            if (!s.isEmpty()) return s;
            System.out.println("Input cannot be empty.");
        }
    }

    private static int readInt(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private static int readPositiveInt(String message) {
        while (true) {
            int n = readInt(message);
            if (n > 0) return n;
            System.out.println("Value must be positive.");
        }
    }

    private static int readNonNegativeInt(String message) {
        while (true) {
            int n = readInt(message);
            if (n >= 0) return n;
            System.out.println("Value cannot be negative.");
        }
    }

    private static double readPositiveDouble(String message) {
        while (true) {
            try {
                System.out.print(message);
                double n = Double.parseDouble(sc.nextLine().trim());
                if (n > 0) return n;
            } catch (NumberFormatException ignored) {}
            System.out.println("Please enter a positive number.");
        }
    }
}
