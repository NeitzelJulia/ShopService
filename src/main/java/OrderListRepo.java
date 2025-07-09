import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderListRepo implements OrderRepoInterface{

    Map<String, Order> orders = new HashMap<>();

    @Override
    public void addOrder(Order order) {
        orders.put(order.id(), order);
    }

    @Override
    public Order getOrderById(String id) {
        return orders.get(id);
    }

    @Override
    public boolean deleteOrderById(String id) {
        return orders.remove(id) != null;
    }

    @Override
    public List<Order> getAllOrders() {
        return new ArrayList<>(orders.values());
    }
}
