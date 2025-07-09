import java.util.*;

public class ProductRepo {

    private final Map<String, Product> products = new HashMap<>();

    public void addProduct(Product product) {
        products.put(product.id(), product);
    }

    public Product getProductById(String id) {
        return products.get(id);
    }

    public List<Product> getAllProducts() {
        return List.copyOf(products.values());

    }

    public boolean deleteProductById(String id) {
        return products.remove(id) != null;
    }

}
