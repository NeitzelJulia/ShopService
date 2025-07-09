import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductRepoTest {

    private ProductRepo productRepo;
    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        productRepo = new ProductRepo();
        product1 = new Product("001", "Kaffeetasse");
        product2 = new Product("002", "Laptop");
    }

    @Test
    void testAddAndGetProduct() {
        productRepo.addProduct(product1);
        Product product = productRepo.getProductById("001");
        assertEquals(product1, product);
    }

    @Test
    void testDeleteProductById() {
        productRepo.addProduct(product1);
        productRepo.addProduct(product2);
        productRepo.deleteProductById("001");
        assertEquals(1,productRepo.getAllProducts().size());
        assertThrows(IllegalArgumentException.class, () -> productRepo.getProductById("001"));
    }

    @Test
    void deleteNonExistentProduct() {
        assertThrows(IllegalArgumentException.class, () -> productRepo.deleteProductById("999"));
    }

    @Test
    void testGetAllProducts() {
        productRepo.addProduct(product1);
        productRepo.addProduct(product2);
        List<Product> products = productRepo.getAllProducts();
        assertEquals(2, products.size());
        assertEquals(product1, products.get(0));
        assertEquals(product2, products.get(1));
    }
}
