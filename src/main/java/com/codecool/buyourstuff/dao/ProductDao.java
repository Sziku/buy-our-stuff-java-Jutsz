package com.codecool.buyourstuff.dao;

import com.codecool.buyourstuff.model.Supplier;
import com.codecool.buyourstuff.model.Product;
import com.codecool.buyourstuff.model.ProductCategory;

import java.util.List;

public interface ProductDao {
    void add(Product product);
    Product find(int id);
    void remove(int id);
    void clear();
    List<Product> getBy(Supplier supplier);
    List<Product> getBy(ProductCategory productCategory);
    public List<Product> getAll();
}
