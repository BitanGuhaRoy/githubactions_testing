package com.example.springai.services;

import com.example.springai.exceptions.ProductNotFoundException;
import com.example.springai.models.Product;

import java.util.List;

public interface ProductService {

    Product createProduct(Product product);

    List<Product> getProducts(int pageNumber, int pageSize);

    Product getProduct(Long id);

    Product updateProduct(Long id, Product product) throws ProductNotFoundException;
}
