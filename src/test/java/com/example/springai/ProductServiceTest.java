package com.example.springai;

import com.example.springai.exceptions.ProductNotFoundException;
import com.example.springai.models.Product;
import com.example.springai.services.impl.FakeStoreProductServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {

    @Test
    void shouldCreateProduct() {
        FakeStoreProductServiceImpl service = new FakeStoreProductServiceImpl();

        Product product = new Product();
        product.setTitle("Laptop");
        product.setPrice(1200.5f);
        product.setDescription("Gaming laptop");
        product.setCategory("electronics");
        product.setUrl("https://example.com/laptop");

        Product savedProduct = service.createProduct(product);

        assertNotNull(savedProduct);
        assertEquals(2, savedProduct.getId());
        assertEquals("Laptop", savedProduct.getTitle());
    }

    @Test
    void shouldReturnProductById() {
        FakeStoreProductServiceImpl service = new FakeStoreProductServiceImpl();

        Product product = new Product();
        product.setTitle("Phone");
        product.setPrice(999.0f);
        product.setDescription("Smartphone");
        product.setCategory("electronics");
        product.setUrl("https://example.com/phone");

        service.createProduct(product);

        Product result = service.getProduct(1L);

        assertNotNull(result);
        assertEquals("Phone", result.getTitle());
    }

    @Test
    void shouldReturnAllProducts() {
        FakeStoreProductServiceImpl service = new FakeStoreProductServiceImpl();

        Product first = new Product();
        first.setTitle("Laptop");
        first.setPrice(1200.5f);
        first.setDescription("Gaming laptop");
        first.setCategory("electronics");
        first.setUrl("https://example.com/laptop");

        Product second = new Product();
        second.setTitle("Phone");
        second.setPrice(999.0f);
        second.setDescription("Smartphone");
        second.setCategory("electronics");
        second.setUrl("https://example.com/phone");

        service.createProduct(first);
        service.createProduct(second);

        List<Product> products = service.getProducts(1, 10);

        assertEquals(2, products.size());
        assertEquals("Laptop", products.get(0).getTitle());
    }

    @Test
    void shouldUpdateExistingProduct() throws ProductNotFoundException {
        FakeStoreProductServiceImpl service = new FakeStoreProductServiceImpl();

        Product product = new Product();
        product.setTitle("Old Name");
        product.setPrice(100f);
        product.setDescription("Old description");
        product.setCategory("clothing");
        product.setUrl("https://example.com/old");
        service.createProduct(product);

        Product updated = new Product();
        updated.setTitle("New Name");
        updated.setPrice(200f);
        updated.setDescription("New description");
        updated.setCategory("fashion");
        updated.setUrl("https://example.com/new");

        Product result = service.updateProduct(1L, updated);

        assertEquals("New Name", result.getTitle());
        assertEquals(200f, result.getPrice());
    }

    @Test
    void shouldThrowWhenProductDoesNotExist() {
        FakeStoreProductServiceImpl service = new FakeStoreProductServiceImpl();

        ProductNotFoundException exception = assertThrows(
                ProductNotFoundException.class,
                () -> service.updateProduct(999L, new Product())
        );

        assertTrue(exception.getMessage().contains("Product not found"));
    }
}
