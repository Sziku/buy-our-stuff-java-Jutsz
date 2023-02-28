package com.codecool.buyourstuff.util;

import com.codecool.buyourstuff.model.Product;
import com.codecool.buyourstuff.model.ProductCategory;
import com.codecool.buyourstuff.model.Supplier;
import com.codecool.buyourstuff.model.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BaseData {
    private static final Supplier AMAZON = new Supplier("Amazon", "Digital content and services");
    private static final Supplier LENOVO = new Supplier("Lenovo", "Computers");
    private static final Supplier DELL = new Supplier("Dell", "Computers");
    private static final Supplier APPLE = new Supplier("Apple", "Apple Inc. is an American multinational technology company that designs, develops, and sells consumer electronics, computer software, and online services.");

    private static final ProductCategory TABLET = new ProductCategory("Tablet", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.", "Hardware");
    private static final ProductCategory LAPTOP = new ProductCategory("Laptop", "A laptop computer, commonly shortened to laptop, or called a notebook computer, is a small, portable personal computer typically having a thin LCD or LED computer screen display.", "Hardware");
    private static final ProductCategory SMARTPHONE = new ProductCategory("Smartphone", "Smartphone is a mobile phone that performs many of the functions of a computer, typically having a touchscreen interface, Internet access, and an operating system capable of running downloaded apps.", "Hardware");

    public static List<Supplier> defaultSuppliers() {
        return new ArrayList<>(List.of(AMAZON, LENOVO, DELL, APPLE));
    }

    public static List<ProductCategory> defaultProductCategories() {
        return new ArrayList<>(List.of(TABLET, LAPTOP, SMARTPHONE));
    }

    public static List<Product> defaultProducts() {
        return new ArrayList<>(List.of(
                new Product("Amazon Fire", new BigDecimal("49.99"), "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", TABLET, AMAZON),
                new Product("Lenovo IdeaPad Miix 700", new BigDecimal("479.99"), "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", TABLET, LENOVO),
                new Product("Amazon Fire HD 8", new BigDecimal("89.99"), "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", TABLET, AMAZON),
                new Product("Dell ChromeBook 11", new BigDecimal("92.86"), "USD", "11-inch ChromeBook laptop built to roll with the punches of family life. Featuring the speed, simplicity and security of Chrome in a portable design.", LAPTOP, DELL),
                new Product("iPhone 11", new BigDecimal("699.00"), "USD", "iPhone 11 is Apple's latest lower cost smartphone for 2019.", SMARTPHONE, APPLE)
        ));
    }

    public static List<User> defaultUsers() {
        return new ArrayList<>(List.of(new User("Barna", "123456")));
    }
}
