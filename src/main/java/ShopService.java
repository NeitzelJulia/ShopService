import java.util.Map;

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

}
