import lombok.With;

import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@With
public record Order(
        String id,
        Map<Product, Integer> products,
        OrderStatus status,
        Instant orderTimestamp
        ) {

    public static Order empty(String id) {
        return new Order(id, Collections.emptyMap(), OrderStatus.PROCESSING, null);
    }

    public Order addProduct(Product product, int quantity) {
        Map <Product, Integer> copy =  new HashMap<>(products);
        copy.put(product, quantity);
        return new Order(id, Collections.unmodifiableMap(copy), status, orderTimestamp);
    }

    public Order removeProduct(Product product) {
        Map <Product, Integer> copy =  new HashMap<>(products);
        copy.remove(product);
        return new Order(id, Collections.unmodifiableMap(copy), status, orderTimestamp);
    }

    public Order withStatus(OrderStatus newStatus) {
        return new Order(id, products, newStatus, orderTimestamp);
    }

    public long totalInCents() {
        return products.entrySet().stream()
                .mapToLong(e -> e.getKey().priceInCents() * e.getValue())
                .sum();
    }
}
