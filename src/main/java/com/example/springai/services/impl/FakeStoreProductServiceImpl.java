package com.example.springai.services.impl;

import com.example.springai.exceptions.ProductNotFoundException;
import com.example.springai.models.Product;
import com.example.springai.services.ProductService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class FakeStoreProductServiceImpl implements ProductService {

    private final Map<Long, Product> products = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(1L);

    @Override
    public Product createProduct(Product product) {
        long nextId = sequence.getAndIncrement();
        product.setId((int) nextId);
        products.put(nextId, product);
        return product;
    }

    @Override
    public List<Product> getProducts(int pageNumber, int pageSize) {
        List<Product> allProducts = new ArrayList<>(products.values());

        if (pageNumber < 1 || pageSize < 1) {
            return List.of();
        }

        int startIndex = (pageNumber - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, allProducts.size());

        if (startIndex >= allProducts.size()) {
            return List.of();
        }

        return allProducts.subList(startIndex, endIndex);
    }

    @Override
    public Product getProduct(Long id) {
        return products.get(id);
    }

    @Override
    public Product updateProduct(Long id, Product product) throws ProductNotFoundException {
        if (!products.containsKey(id)) {
            throw new ProductNotFoundException("Product not found with id: " + id);
        }

        Product existingProduct = products.get(id);

        if (product.getTitle() != null) {
            existingProduct.setTitle(product.getTitle());
        }
        if (product.getDescription() != null) {
            existingProduct.setDescription(product.getDescription());
        }
        if (product.getCategory() != null) {
            existingProduct.setCategory(product.getCategory());
        }
        if (product.getUrl() != null) {
            existingProduct.setUrl(product.getUrl());
        }
        existingProduct.setPrice(product.getPrice());

        return existingProduct;
    }
}
