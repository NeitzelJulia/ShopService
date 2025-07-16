import java.util.*;

public class ProductRepo {

    private final Map<String, Product> products = new HashMap<>();

    public void addProduct(Product product) {
        products.put(product.id(), product);
    }

    public Optional<Product> getProductById(String id) {

        return Optional.ofNullable(products.get(id));
    }

    public List<Product> getAllProducts() {
        return List.copyOf(products.values());

    }

    public boolean deleteProductById(String id) {
        return products.remove(id) != null;
    }

}
