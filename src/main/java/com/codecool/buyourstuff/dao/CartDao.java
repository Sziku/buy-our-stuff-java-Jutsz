package com.codecool.buyourstuff.dao;

import com.codecool.buyourstuff.model.Cart;
import com.codecool.buyourstuff.model.Product;

import java.util.List;


public interface CartDao {
    void add(Cart cart);
    Cart find(int id);
    void remove(int id);
    void clear();
    public List<Cart> getAll();
}
