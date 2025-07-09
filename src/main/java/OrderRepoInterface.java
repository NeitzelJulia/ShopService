import java.util.List;

public interface OrderRepoInterface {

    void addOrder(Order order);

    boolean deleteOrderById(String id);

    Order getOrderById(String id);

    List<Order> getAllOrders();
}
