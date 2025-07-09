import java.util.Map;

public record Order(String id, Map<Product, Integer> products) {
}
