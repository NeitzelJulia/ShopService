import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductRepo {

    Map<String, Product> products = new HashMap<>();

    public void addProduct(Product product) {
        products.put(product.id(), product);
    }

    public Product getProductById(String id) {
        return products.get(id);
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }

    public boolean deleteProductById(String id) {
        return products.remove(id) != null;
    }

}
