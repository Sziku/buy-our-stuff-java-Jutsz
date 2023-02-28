package com.codecool.buyourstuff.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

public class Product extends BaseModel {

    private String name;
    private String description;
    private BigDecimal defaultPrice;
    private Currency defaultCurrency;
    private ProductCategory productCategory;
    private Supplier supplier;


    public Product(String name, BigDecimal defaultPrice, String currencyString, String description, ProductCategory productCategory, Supplier supplier) {
        this.name = name;
        this.description = description;
        this.setPrice(defaultPrice, currencyString);
        this.setSupplier(supplier);
        this.setProductCategory(productCategory);
    }

    public BigDecimal getDefaultPrice(int decimals) {
        return defaultPrice.setScale(decimals, RoundingMode.HALF_DOWN);
    }

    public String getPrice() {
        return getDefaultPrice(2) + " " + defaultCurrency.toString();
    }

    public void setPrice(BigDecimal price, String currency) {
        this.defaultPrice = price;
        this.defaultCurrency = Currency.getInstance(currency);
    }

    @Override
    public String toString() {
        return String.format("%1$s={" +
                        "id: %2$d, " +
                        "name: %3$s, " +
                        "defaultPrice: %4$s, " +
                        "defaultCurrency: %5$s, " +
                        "productCategory: %6$s, " +
                        "supplier: %7$s}",
                getClass().getSimpleName(),
                id,
                name,
                getPrice(),
                defaultCurrency,
                productCategory.getName(),
                supplier.getName()
        );
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getDefaultPrice() {
        return defaultPrice;
    }

    public void setDefaultPrice(BigDecimal defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

    public Currency getDefaultCurrency() {
        return defaultCurrency;
    }

    public void setDefaultCurrency(Currency defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}
