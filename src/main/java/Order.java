import lombok.With;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@With
public record Order(
        String id,
        Map<Product, Integer> products,
        OrderStatus status) {

    public static Order empty(String id) {
        return new Order(id, Collections.emptyMap(), OrderStatus.PROCESSING);
    }

    public Order addProduct(Product product, int quantity) {
        Map <Product, Integer> copy =  new HashMap<>(products);
        copy.put(product, quantity);
        return new Order(id, Collections.unmodifiableMap(copy), status);
    }

    public Order removeProduct(Product product) {
        Map <Product, Integer> copy =  new HashMap<>(products);
        copy.remove(product);
        return new Order(id, Collections.unmodifiableMap(copy), status);
    }

    public Order withStatus(OrderStatus newStatus) {
        return new Order(id, products, newStatus);
    }

    public long totalInCents() {
        return products.entrySet().stream()
                .mapToLong(e -> e.getKey().priceInCents() * e.getValue())
                .sum();
    }
}
