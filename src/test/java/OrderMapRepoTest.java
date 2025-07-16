import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class OrderMapRepoTest {

    private OrderMapRepo orderMapRepo;
    private Order order1;
    private Order order2;

    @BeforeEach
    void setUp() {
        orderMapRepo = new OrderMapRepo();
        Product product1 = new Product("SKU-001", "Kaffeetasse", 399L);
        Product product2 = new Product("SKU-002", "Laptop", 74900L);
        order1 = new Order("ORD-001", Map.of(product1, 1), OrderStatus.PROCESSING, Instant.now());
        order2 = new Order("ORD-002", Map.of(product2, 1),  OrderStatus.PROCESSING, Instant.now());
    }

    @Test
    void addAndGetOrderById() {
        orderMapRepo.addOrder(order1);
        Order found = orderMapRepo.getOrderById("ORD-001");
        assertNotNull(found);
        assertEquals(order1, found);
    }

    @Test
    void removeExistingOrder() {
        orderMapRepo.addOrder(order1);
        orderMapRepo.addOrder(order2);

        boolean removed = orderMapRepo.deleteOrderById("ORD-001");
        assertTrue(removed);

        List<Order> all = orderMapRepo.getAllOrders();
        assertEquals(1, all.size());
        assertTrue(all.contains(order2));
        assertNull(orderMapRepo.getOrderById("ORD-001"));
    }

    @Test
    void removeNonExistingOrder() {
        boolean removed = orderMapRepo.deleteOrderById("NON-EXISTENT");
        assertFalse(removed);
    }

    @Test
    void getOrderByIdWhenNotFound() {
        Order found = orderMapRepo.getOrderById("UNKNOWN");
        assertNull(found);
    }

    @Test
    void getAllOrdersEmpty() {
        List<Order> all = orderMapRepo.getAllOrders();
        assertNotNull(all, "getAllOrders darf nicht null sein");
        assertTrue(all.isEmpty());
    }

    @Test
    void getAllOrders() {
        orderMapRepo.addOrder(order1);
        orderMapRepo.addOrder(order2);

        List<Order> all = orderMapRepo.getAllOrders();
        assertEquals(2, all.size());
        assertTrue(all.contains(order1));
        assertTrue(all.contains(order2));
    }
}