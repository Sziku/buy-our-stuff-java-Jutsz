package com.codecool.buyourstuff.dao.implementation.mem;

import com.codecool.buyourstuff.dao.implementation.databse.DaoDbConnectionData;
import com.codecool.buyourstuff.model.exception.DataNotFoundException;
import com.codecool.buyourstuff.dao.LineItemDao;
import com.codecool.buyourstuff.model.Cart;
import com.codecool.buyourstuff.model.LineItem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LineItemDaoMem implements LineItemDao {

    private List<LineItem> data = new ArrayList<>();

    @Override
    public void add(LineItem lineItem) {
        data.add(lineItem);
    }

    @Override
    public void remove(LineItem lineItem) {
        data.remove(lineItem);
    }

    @Override
    public void clear() {
        data = new ArrayList<>();
    }

    @Override
    public void update(LineItem lineItem, int quantity) {
        data
                .stream()
                .filter(
                        item -> item.getId() == lineItem.getId()
                )
                .findFirst()
                .ifPresent(
                        item -> item.setQuantity(quantity)
                );
    }

    @Override
    public LineItem find(int id) {
        return data
                .stream()
                .filter(item -> item.getId() == id)
                .findFirst()
                .orElseThrow(() -> new DataNotFoundException("No such line-item"));
    }

    @Override
    public List<LineItem> getBy(Cart cart) {
        return data
                .stream()
                .filter(
                        lineItem -> lineItem.getCartId() == cart.getId()
                )
                .collect(Collectors.toList());
    }
}
