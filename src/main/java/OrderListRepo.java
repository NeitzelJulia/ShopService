import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderListRepo implements OrderRepoInterface{

    Map<String, Order> orders = new HashMap<>();

    public void addOrder(Order order) {
        orders.put(order.id(), order);
    }

    public Order getOrderById(String id) {
        return orders.get(id);
    }

    public boolean deleteOrderById(String id) {
        return orders.remove(id) != null;
    }

    public List<Order> getAllOrders() {
        return new ArrayList<>(orders.values());
    }
}
