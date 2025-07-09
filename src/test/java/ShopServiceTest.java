import org.junit.jupiter.api.*;

import java.util.List;

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

        p1 = new Product("SKU-001", "Kaffeetasse");
        p2 = new Product("SKU-002", "Laptop");
    }

    @Test
    void submitOrder_AllProductsExist_AddsOrder() {
        productRepo.addProduct(p1);
        productRepo.addProduct(p2);

        Order order = new Order("ORD-001", List.of(p1, p2));
        boolean result = shopService.submitOrder(order);

        assertTrue(result);
        List<Order> all = orderRepo.getAllOrders();
        assertEquals(1, all.size());
        assertTrue(all.contains(order));
    }

    @Test
    void submitOrder_MissingProduct_PrintsErrorAndDoesNotAdd() {
        productRepo.addProduct(p1);

        Order order = new Order("ORD-002", List.of(p1, p2));
        boolean result = shopService.submitOrder(order);

        assertFalse(result);
        shopService.submitOrder(order);

        assertTrue(orderRepo.getAllOrders().isEmpty());
    }
}
