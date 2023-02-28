package com.codecool.buyourstuff.dao;

import com.codecool.buyourstuff.model.*;
import com.codecool.buyourstuff.util.BaseData;

public class DataManager {
    private static final DaoType DAO_TYPE = DaoType.DATABASE; //TODO: itt lehet állítani  a tipust

    private static final DaoImplementationSupplier daoImplementationSupplier = DaoImplementationSupplier.getInstance(DAO_TYPE);

    private static final ProductDao productDao = daoImplementationSupplier.getProductDao();
    private static final ProductCategoryDao productCategoryDao = daoImplementationSupplier.getProductCategoryDao();
    private static final SupplierDao supplierDao = daoImplementationSupplier.getSupplierDao();
    private static final CartDao cartDao = daoImplementationSupplier.getCartDao();
    private static final LineItemDao lineItemDao = daoImplementationSupplier.getLineItemDao();
    private static final UserDao userDao = daoImplementationSupplier.getUserDao();

    public static void init() {
        initCart();
        initSuppliers();
        initProductCategories();
        initProducts();
        initUsers();
    }

    public static void clear() {
        productDao.clear();
        productCategoryDao.clear();
        supplierDao.clear();
        cartDao.clear();
        lineItemDao.clear();
        userDao.clear();
    }

    private static void initCart() {
        Cart cart = new Cart();
        cartDao.add(cart);
    }

    private static void initSuppliers() {
        for (Supplier supplier : BaseData.defaultSuppliers()) {
            supplierDao.add(supplier);
        }
    }

    private static void initProductCategories() {
        for (ProductCategory productCategory : BaseData.defaultProductCategories()) {
            productCategoryDao.add(productCategory);
        }
    }

    private static void initProducts() {
        for (Product product : BaseData.defaultProducts()) {
            productDao.add(product);
        }
    }

    private static void initUsers() {
        for (User user : BaseData.defaultUsers()) {
            userDao.add(user);
        }
    }

    public static ProductDao getProductDao() {
        return productDao;
    }

    public static ProductCategoryDao getProductCategoryDao() {
        return productCategoryDao;
    }

    public static SupplierDao getSupplierDao() {
        return supplierDao;
    }

    public static CartDao getCartDao() {
        return cartDao;
    }

    public static LineItemDao getLineItemDao() {
        return lineItemDao;
    }

    public static UserDao getUserDao() {
        return userDao;
    }
}
