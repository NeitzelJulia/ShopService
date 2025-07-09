import java.util.List;

public class Main {
    public static void main(String[] args) {

        ProductRepo productRepo = new ProductRepo();
        productRepo.addProduct(new Product("SKU-001", "Kaffeetasse"));
        productRepo.addProduct(new Product("SKU-002", "Laptop"));
        productRepo.addProduct(new Product("SKU-003", "Wasserflasche"));

        // OrderRepoInterface orderRepo = new OrderListRepo();

        OrderRepoInterface orderRepo = new OrderMapRepo();

        ShopService shopService = new ShopService(productRepo, orderRepo);

        // Eine gültige Bestellung anlegen und platzieren
        Order order1 = new Order("ORD-1001", List.of(
                productRepo.getProductById("SKU-001"),
                productRepo.getProductById("SKU-002")
        ));
        boolean success1 = shopService.submitOrder(order1);
        System.out.printf("Order %s placed: %s%n", order1.id(), success1);

        // Eine Bestellung mit einem fehlenden Produkt anlegen
        Order order2 = new Order("ORD-1002", List.of(
                productRepo.getProductById("SKU-001"),
                new Product("SKU-999", "Unbekanntes Produkt")  // nicht im Repo
        ));
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
