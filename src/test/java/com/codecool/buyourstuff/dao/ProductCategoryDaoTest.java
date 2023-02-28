package com.codecool.buyourstuff.dao;

import com.codecool.buyourstuff.model.ProductCategory;
import com.codecool.buyourstuff.model.exception.DataNotFoundException;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class ProductCategoryDaoTest {
    private static final ProductCategoryDao PRODUCT_CATEGORY_DAO = DataManager.getProductCategoryDao();

    @Test
    void testAdd() {
        ProductCategory productCategory = new ProductCategory("test", "test", "test");
        PRODUCT_CATEGORY_DAO.add(productCategory);
        assertNotEquals(0, productCategory.getId());
        PRODUCT_CATEGORY_DAO.remove(productCategory.getId());
    }

    @Test
    void testFind_validId() {
        ProductCategory productCategory = new ProductCategory("test", "test", "test");
        PRODUCT_CATEGORY_DAO.add(productCategory);

        ProductCategory result = PRODUCT_CATEGORY_DAO.find(productCategory.getId());
        assertEquals(productCategory.getId(), result.getId());
        PRODUCT_CATEGORY_DAO.remove(productCategory.getId());
    }

    @Test
    void testFind_invalidId() {
        assertThrows(DataNotFoundException.class, () -> PRODUCT_CATEGORY_DAO.find(-1));
    }

    @Test
    void testRemove() {
        ProductCategory productCategory = new ProductCategory("test", "test", "test");
        PRODUCT_CATEGORY_DAO.add(productCategory);
        assertNotNull(PRODUCT_CATEGORY_DAO.find(productCategory.getId()));

        PRODUCT_CATEGORY_DAO.remove(productCategory.getId());
        assertThrows(DataNotFoundException.class, () -> PRODUCT_CATEGORY_DAO.find(productCategory.getId()));
    }
}
