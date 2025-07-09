import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public record Order(String id, Map<Product, Integer> products) {

    public static Order empty(String id) {
        return new Order(id, Collections.emptyMap());
    }

    public Order addProduct(Product product, int quantity) {
        Map <Product, Integer> copy =  new HashMap<>(products);
        copy.put(product, quantity);
        return new Order(id, Collections.unmodifiableMap(copy));
    }

    public Order removeProduct(Product product) {
        Map <Product, Integer> copy =  new HashMap<>(products);
        copy.remove(product);
        return new Order(id, Collections.unmodifiableMap(copy));
    }

    public long totalInCents() {
        return products.entrySet().stream()
                .mapToLong(e -> e.getKey().priceInCents() * e.getValue())
                .sum();
    }
}
