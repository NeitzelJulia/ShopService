import java.util.NoSuchElementException;

public class Main {
    public static void main(String[] args) {

        ProductRepo productRepo = new ProductRepo();
        productRepo.addProduct(new Product("SKU-001", "Kaffeetasse", 399L));
        productRepo.addProduct(new Product("SKU-002", "Laptop", 74900L));
        productRepo.addProduct(new Product("SKU-003", "Wasserflasche", 1295L));

        // OrderRepoInterface orderRepo = new OrderListRepo();

        OrderRepoInterface orderRepo = new OrderMapRepo();

        ShopService shopService = new ShopService(productRepo, orderRepo);

        // Eine gültige Bestellung anlegen und platzieren
        Order order1 = Order.empty("ORD-1001")
                .addProduct(productRepo.getProductById("SKU-001")
                                        .orElseThrow(() -> new NoSuchElementException("Product not found")),
                        1)
                .addProduct(productRepo.getProductById("SKU-002")
                                        .orElseThrow(() -> new NoSuchElementException("Product not found")),
                        2);

        boolean success1 = shopService.submitOrder(order1);
        System.out.printf("Order %s placed: %s%n", order1.id(), success1);

        // Eine Bestellung mit einem fehlenden Produkt anlegen
        Order order2 = Order.empty("ORD-1002")
                .addProduct(productRepo.getProductById("SKU-001")
                                        .orElseThrow(() -> new NoSuchElementException("Product not found")),
                        1)
                .addProduct(new Product("SKU-UNKNOWN", "unknown Product", 123L), 2);

        boolean success2 = shopService.submitOrder(order2);
        System.out.printf("Order %s placed: %s%n", order2.id(), success2);

        System.out.println("Orders:");
        for (Order o : orderRepo.getAllOrders()) {
            System.out.printf("Order %s: %d items%n",
                    o.id(),
                    o.products().size());
        }
    }
}
