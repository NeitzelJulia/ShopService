import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderListRepo implements OrderRepoInterface{

    private final List<Order> orders = new ArrayList<>();

    @Override
    public void addOrder(Order order) {
        orders.add(order);
    }

    @Override
    public Order getOrderById(String id) {
        for (Order order : orders) {
            if (order.id().equals(id)) {
                return order;
            }
        }
        return null;
    }

    @Override
    public boolean deleteOrderById(String id) {
        return orders.removeIf(o -> o.id().equals(id));

    }

    @Override
    public List<Order> getAllOrders() {
        return Collections.unmodifiableList(orders);
    }
}
