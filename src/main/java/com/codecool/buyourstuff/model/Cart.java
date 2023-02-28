package com.codecool.buyourstuff.model;

import com.codecool.buyourstuff.dao.DataManager;
import com.codecool.buyourstuff.dao.LineItemDao;

import java.util.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Cart extends BaseModel {

    private final Currency currency;

    public Cart(String currencyString) {
        this.currency = Currency.getInstance(currencyString);
    }

    public Cart() {
        this.currency = Currency.getInstance("USD");
    }

    public List<LineItem> getItems() {
        return items();
    }

    public void add(Product product) {
        LineItemDao lineItemDao = DataManager.getLineItemDao();
        if (product.getDefaultCurrency() == currency) {
            Optional<LineItem> match;
            match = getItem(product);
            if (match.isPresent()) {
                LineItem item = match.get();
                lineItemDao.update(item, item.getQuantity() + 1);
            } else {
                lineItemDao.add(new LineItem(product, id, 1));
            }
        }
    }

    public void remove(Product product) {
        LineItemDao lineItemDao = DataManager.getLineItemDao();
        Optional<LineItem> match;
        match = getItem(product);
        if (match.isPresent()) {
            LineItem item = match.get();
            if (item.getQuantity() > 1) {
                lineItemDao.update(item, item.getQuantity() - 1);
            } else {
                lineItemDao.remove(item);
            }
        }
    }

    public BigDecimal getTotalPrice() {
        return items()
                .stream()
                .map(
                        item -> item.getProduct()
                                .getDefaultPrice()
                                .multiply(new BigDecimal(item.getQuantity()))
                )
                .reduce(BigDecimal::add)
                .orElse(new BigDecimal(0));
    }

    public BigDecimal getTotalPrice(int decimals) {
        return getTotalPrice().setScale(decimals, RoundingMode.HALF_DOWN);
    }

    public int size() {
        return items()
                .stream()
                .mapToInt(LineItem::getQuantity)
                .sum();
    }

    private Optional<LineItem> getItem(Product product) {
        List<LineItem> items = DataManager.getLineItemDao().getBy(this);
        return items
                .stream()
                .filter(
                        item -> item.getProduct().getId() == product.getId()
                )
                .findFirst();
    }

    private List<LineItem> items() {
        return DataManager.getLineItemDao().getBy(this);
    }

    @Override
    public String toString() {
        return String.format("items: %1$s, currency: %2$s", items(), currency);
    }

    public Currency getCurrency() {
        return currency;
    }
}
