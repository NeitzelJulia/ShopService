public class ShopService {

    private final ProductRepo productRepo;
    private final OrderRepoInterface orderListRepo;

    public ShopService(ProductRepo productRepo, OrderListRepo orderListRepo) {
        this.productRepo = productRepo;
        this.orderListRepo = orderListRepo;
    }

    public boolean submitOrder(Order order) {
        for (Product product : order.products()) {
            if (productRepo.getProductById(product.id()) == null) {
                return false;
            }
        }

        orderListRepo.addOrder(order);
        return true;
    }

}
