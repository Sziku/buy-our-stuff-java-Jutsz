package com.codecool.buyourstuff.dao;

import com.codecool.buyourstuff.model.Supplier;

import java.util.List;

public interface SupplierDao {
    void add(Supplier supplier);
    Supplier find(int id);
    void remove(int id);
    void clear();
    List<Supplier> getAll();
}
