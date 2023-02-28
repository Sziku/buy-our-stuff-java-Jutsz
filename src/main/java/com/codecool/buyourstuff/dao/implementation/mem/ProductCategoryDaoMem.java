package com.codecool.buyourstuff.dao.implementation.mem;

import com.codecool.buyourstuff.model.LineItem;
import com.codecool.buyourstuff.model.Product;
import com.codecool.buyourstuff.model.Supplier;
import com.codecool.buyourstuff.model.exception.DataNotFoundException;
import com.codecool.buyourstuff.dao.ProductCategoryDao;
import com.codecool.buyourstuff.model.ProductCategory;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryDaoMem implements ProductCategoryDao {
    private List<ProductCategory> data = new ArrayList<>();

    @Override
    public void add(ProductCategory category) {
        category.setId(data.size() + 1);
        data.add(category);

    }

    @Override
    public ProductCategory find(int id) {

        return data
                .stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElseThrow(() -> new DataNotFoundException("No such category"));
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
    public List<ProductCategory> getAll() {

        return data;
    }




}
