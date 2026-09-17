public class InventoryTest {
    public static void main(String[] args) {
        Inventory inventory = new Inventory();
        Product p = new Product(999, "Test Product", "Other", 100.0, 5, 2);

        inventory.addProduct(p);

        if (inventory.findProduct(999) == null)
            throw new AssertionError("Product was not added.");

        inventory.stockIn(999, 5);
        if (p.getQuantity() != 10)
            throw new AssertionError("Stock-in test failed.");

        inventory.stockOut(999, 3);
        if (p.getQuantity() != 7)
            throw new AssertionError("Stock-out test failed.");

        boolean failed = false;
        try {
            inventory.stockOut(999, 20);
        } catch (IllegalArgumentException e) {
            failed = true;
        }

        if (!failed)
            throw new AssertionError("Insufficient stock validation failed.");

        System.out.println("All validation tests passed.");
    }
}
