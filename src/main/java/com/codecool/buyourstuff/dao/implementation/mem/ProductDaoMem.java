package com.codecool.buyourstuff.dao.implementation.mem;

import com.codecool.buyourstuff.model.exception.DataNotFoundException;
import com.codecool.buyourstuff.dao.ProductDao;
import com.codecool.buyourstuff.model.Product;
import com.codecool.buyourstuff.model.ProductCategory;
import com.codecool.buyourstuff.model.Supplier;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductDaoMem implements ProductDao {

    private List<Product> data = new ArrayList<>();

    @Override
    public void add(Product product) {
        product.setId(data.size() + 1);
        data.add(product);
    }

    @Override
    public Product find(int id) {
        return data
                .stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElseThrow(() -> new DataNotFoundException("No such product"));
    }

    @Override
    public void remove(int id) {
        data.remove(find(id));
    }

    @Override
    public void clear() {
        data = new ArrayList<>();
    }

    @Override
    public List<Product> getAll() {
        return data;
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        return data
                .stream()
                .filter(
                        t -> t.getSupplier().equals(supplier)
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        return data
                .stream()
                .filter(
                        t -> t.getProductCategory().equals(productCategory)
                )
                .collect(Collectors.toList());
    }
}
