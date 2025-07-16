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
            Product product = entry.getKey();
            int quantity = entry.getValue();

            if (productRepo.getProductById(product.id()) == null) {
                return false;
            }
        }

        orderRepo.addOrder(order);
        return true;
    }

    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orderRepo.getAllOrders().stream()
                .filter(order -> order.status() == status)
                .collect(Collectors.toList());
    }

}
