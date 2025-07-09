import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {

    private ProductRepo productRepo;
    private OrderListRepo orderRepo;
    private ShopService shopService;

    private Product p1;
    private Product p2;

    @BeforeEach
    void setUp() {
        productRepo = new ProductRepo();
        orderRepo   = new OrderListRepo();
        shopService = new ShopService(productRepo, orderRepo);

        p1 = new Product("SKU-001", "Kaffeetasse", 399L);
        p2 = new Product("SKU-002", "Laptop", 74900L);
    }

    @Test
    void submitOrder_AllProductsExist_AddsOrder() {
        productRepo.addProduct(p1);
        productRepo.addProduct(p2);

        Order order = new Order("ORD-001", Map.ofEntries(
                Map.entry(p1, 1),
                Map.entry(p2, 1)
        ));
        boolean result = shopService.submitOrder(order);

        assertTrue(result);
        List<Order> all = orderRepo.getAllOrders();
        assertEquals(1, all.size());
        assertTrue(all.contains(order));
    }

    @Test
    void submitOrder_MissingProduct_PrintsErrorAndDoesNotAdd() {
        productRepo.addProduct(p1);

        Order order = new Order(
                "ORD-002",
                Map.ofEntries(
                        Map.entry(p1,1),
                        Map.entry(p2,1)
                )
        );

        boolean result = shopService.submitOrder(order);

        assertFalse(result);
        shopService.submitOrder(order);

        assertTrue(orderRepo.getAllOrders().isEmpty());
    }
}
