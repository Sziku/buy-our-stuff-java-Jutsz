package com.codecool.buyourstuff.dao.implementation.mem;

import com.codecool.buyourstuff.dao.CartDao;
import com.codecool.buyourstuff.model.exception.DataNotFoundException;
import com.codecool.buyourstuff.model.Cart;

import java.util.ArrayList;
import java.util.List;

public class CartDaoMem implements CartDao {

    private List<Cart> data = new ArrayList<>();

    @Override
    public void add(Cart cart) {
        cart.setId(data.size() + 1);
        data.add(cart);
    }

    @Override
    public Cart find(int id) {
        return data
                .stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElseThrow(() -> new DataNotFoundException("No such cart"));
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
    public List<Cart> getAll() {
        return data;
    }
}
