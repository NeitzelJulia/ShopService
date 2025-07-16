import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ShopService {

    private final ProductRepo productRepo;
    private final OrderRepoInterface orderRepo;

    public ShopService(ProductRepo productRepo, OrderRepoInterface orderRepo) {
        this.productRepo = productRepo;
        this.orderRepo = orderRepo;
    }

    public boolean submitOrder(Order order) {
        for (Map.Entry<Product, Integer> entry : order.products().entrySet()) {
            String productId = entry.getKey().id();

            productRepo.getProductById(productId)
                    .orElseThrow(() -> new ProductNotFoundException(productId));
        }

        Order timestampedOrder = order.withOrderTimestamp(Instant.now());
        orderRepo.addOrder(timestampedOrder);
        return true;
    }

    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orderRepo.getAllOrders().stream()
                .filter(order -> order.status() == status)
                .collect(Collectors.toList());
    }

    public Order updateOrderStatus(String orderId, OrderStatus newStatus) {
        Order order = orderRepo.getOrderById(orderId);
        if (order == null) {
            throw new OrderNotFoundException(orderId);
        }

        Order updatedOrder = order.withStatus(newStatus);

        orderRepo.deleteOrderById(orderId);
        orderRepo.addOrder(updatedOrder);
        return updatedOrder;
    }
}
