import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class OrderListRepoTest {

    private OrderListRepo orderListRepo;
    private Order order1;
    private Order order2;

    @BeforeEach
    void setUp() {
        orderListRepo = new OrderListRepo();
        Product product1 = new Product("SKU-001", "Kaffeetasse", 399L);
        Product product2 = new Product("SKU-002", "Laptop", 74900L);
        order1 = new Order("ORD-001", Map.of(product1, 1));
        order2 = new Order("ORD-002", Map.of(product2, 1));
    }

    @Test
    void addAndGetOrderById() {
        orderListRepo.addOrder(order1);
        Order found = orderListRepo.getOrderById("ORD-001");
        assertNotNull(found);
        assertEquals(order1, found);
    }

    @Test
    void removeExistingOrder() {
        orderListRepo.addOrder(order1);
        orderListRepo.addOrder(order2);

        boolean removed = orderListRepo.deleteOrderById("ORD-001");
        assertTrue(removed);

        List<Order> all = orderListRepo.getAllOrders();
        assertEquals(1, all.size());
        assertTrue(all.contains(order2));
        assertNull(orderListRepo.getOrderById("ORD-001"));
    }

    @Test
    void removeNonExistingOrder() {
        boolean removed = orderListRepo.deleteOrderById("NON-EXISTENT");
        assertFalse(removed);
    }

    @Test
    void getOrderByIdWhenNotFound() {
        Order found = orderListRepo.getOrderById("UNKNOWN");
        assertNull(found);
    }

    @Test
    void getAllOrdersEmpty() {
        List<Order> all = orderListRepo.getAllOrders();
        assertNotNull(all, "getAllOrders darf nicht null sein");
        assertTrue(all.isEmpty());
    }

    @Test
    void getAllOrders() {
        orderListRepo.addOrder(order1);
        orderListRepo.addOrder(order2);

        List<Order> all = orderListRepo.getAllOrders();
        assertEquals(2, all.size());
        assertTrue(all.contains(order1));
        assertTrue(all.contains(order2));
    }
}