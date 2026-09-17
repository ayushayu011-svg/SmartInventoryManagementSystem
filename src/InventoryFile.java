import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class InventoryFile {
    private final Path folder = Paths.get("data");

    public InventoryFile() {
        try {
            Files.createDirectories(folder);
        } catch (IOException e) {
            throw new RuntimeException("Unable to create data directory.", e);
        }
    }

    public void saveProducts(List<Product> products) {
        Path file = folder.resolve("products.csv");
        try (BufferedWriter writer = Files.newBufferedWriter(file)) {
            writer.write("id,name,category,price,quantity,lowStockLimit");
            writer.newLine();
            for (Product p : products) {
                writer.write(p.toCsv());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Unable to save products.", e);
        }
    }

    public ArrayList<Product> loadProducts() {
        ArrayList<Product> result = new ArrayList<>();
        Path file = folder.resolve("products.csv");
        if (!Files.exists(file)) return result;

        try (BufferedReader reader = Files.newBufferedReader(file)) {
            String line;
            boolean first = true;
            while ((line = reader.readLine()) != null) {
                if (first) { first = false; continue; }
                if (line.isBlank()) continue;
                String[] a = line.split(",", -1);
                if (a.length >= 6) {
                    result.add(new Product(
                        Integer.parseInt(a[0]), a[1], a[2],
                        Double.parseDouble(a[3]), Integer.parseInt(a[4]),
                        Integer.parseInt(a[5])
                    ));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Unable to read products.csv.", e);
        }
        return result;
    }

    public void saveTransactions(List<Transaction> list) {
        Path file = folder.resolve("transactions.csv");
        try (BufferedWriter writer = Files.newBufferedWriter(file)) {
            writer.write("id,productId,type,quantity,time");
            writer.newLine();
            for (Transaction t : list) {
                writer.write(t.toCsv());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Unable to save transactions.", e);
        }
    }

    public void saveOrders(List<Order> list) {
        Path file = folder.resolve("orders.csv");
        try (BufferedWriter writer = Files.newBufferedWriter(file)) {
            writer.write("id,productId,quantity,total,time");
            writer.newLine();
            for (Order o : list) {
                writer.write(o.toCsv());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Unable to save orders.", e);
        }
    }
}
