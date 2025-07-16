import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProductRepoTest {

    private ProductRepo productRepo;
    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        productRepo = new ProductRepo();
        product1 = new Product("SKU-001", "Kaffeetasse", 399L);
        product2 = new Product("SKU-002", "Laptop", 74900L);
    }

    @Test
    void testAddAndGetProduct() {
        productRepo.addProduct(product1);
        Optional<Product> product = productRepo.getProductById("SKU-001");
        assertTrue(product.isPresent());
        assertEquals(product1, product.get());
    }

    @Test
    void addSameProductTwice_keepsSingleEntry() {
        productRepo.addProduct(product1);
        productRepo.addProduct(product1);
        assertEquals(1, productRepo.getAllProducts().size());
    }

    @Test
    void getProductByIdNonExistentReturnsEmptyOptional() {
        Optional<Product> result = productRepo.getProductById("SKU-UNKNOWN");

        assertFalse(result.isPresent(), "Expected no product for unknown SKU");
    }

    @Test
    void testDeleteProductById() {
        productRepo.addProduct(product1);
        productRepo.addProduct(product2);
        assertTrue(productRepo.deleteProductById("SKU-001"));
        assertEquals(1,productRepo.getAllProducts().size());

    }

    @Test
    void deleteNonExistentProduct() {
        productRepo.addProduct(product1);
        assertFalse(productRepo.deleteProductById("SKU-UNKNOWN"));
        assertEquals(1, productRepo.getAllProducts().size());
    }

    @Test
    void testGetAllProducts() {
        productRepo.addProduct(product1);
        productRepo.addProduct(product2);
        List<Product> products = productRepo.getAllProducts();
        assertEquals(2, products.size());
        assertTrue(products.contains(product1));
        assertTrue(products.contains(product2));
    }

    @Test
    void getAllProductsOnEmptyRepo() {
        List<Product> all = productRepo.getAllProducts();
        assertNotNull(all);
        assertTrue(all.isEmpty());
    }
}
