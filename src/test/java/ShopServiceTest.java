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

        Order order = Order.empty("ORD-001")
                .addProduct(p1, 1)
                .addProduct(p2, 1);

        boolean result = shopService.submitOrder(order);

        assertTrue(result);
        List<Order> all = orderRepo.getAllOrders();
        assertEquals(1, all.size());
        assertTrue(all.contains(order));
    }

    @Test
    void submitOrder_MissingProduct_PrintsErrorAndDoesNotAdd() {
        productRepo.addProduct(p1);

        Order order = Order.empty("ORD-002")
                .addProduct(p1, 1)
                .addProduct(p2, 1);

        assertThrows(
                ProductNotFoundException.class,
                () -> shopService.submitOrder(order)
        );

        assertTrue(orderRepo.getAllOrders().isEmpty());
    }

    @Test
    void getOrdersByStatus_returnsOnlyMatchingStatus() {
        Order order1 = new Order("ORD-001", Map.of(p1, 1), OrderStatus.PROCESSING);
        Order order2 = new Order("ORD-002", Map.of(p2, 1), OrderStatus.IN_DELIVERY);
        Order order3 = new Order("ORD-003", Map.of(p1, 2), OrderStatus.PROCESSING);

        orderRepo.addOrder(order1);
        orderRepo.addOrder(order2);
        orderRepo.addOrder(order3);

        List<Order> processingOrders = shopService.getOrdersByStatus(OrderStatus.PROCESSING);

        assertEquals(2, processingOrders.size());
        assertTrue(processingOrders.contains(order1));
        assertTrue(processingOrders.contains(order3));

        List<Order> inDeliveryOrders = shopService.getOrdersByStatus(OrderStatus.IN_DELIVERY);
        assertEquals(1, inDeliveryOrders.size());
        assertTrue(inDeliveryOrders.contains(order2));

        List<Order> completedOrders = shopService.getOrdersByStatus(OrderStatus.COMPLETED);
        assertNotNull(completedOrders);
        assertTrue(completedOrders.isEmpty());
    }

    @Test
    void updateOrder_existingOrder_changesStatus() {
        Order original = Order.empty("ORD-10");
        orderRepo.addOrder(original);

        Order updated = shopService.updateOrderStatus("ORD-10", OrderStatus.COMPLETED);

        assertEquals(OrderStatus.COMPLETED, updated.status());
        assertEquals(updated, orderRepo.getOrderById("ORD-10"));
    }

    @Test
    void updateOrder_nonExistingOrder_throws() {
        assertThrows(OrderNotFoundException.class,
                () -> shopService.updateOrderStatus("UNKNOWN", OrderStatus.COMPLETED));
    }
}
