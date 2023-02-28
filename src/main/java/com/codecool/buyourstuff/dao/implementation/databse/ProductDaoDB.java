package com.codecool.buyourstuff.dao.implementation.databse;

import com.codecool.buyourstuff.dao.DataManager;
import com.codecool.buyourstuff.dao.ProductCategoryDao;
import com.codecool.buyourstuff.dao.ProductDao;
import com.codecool.buyourstuff.dao.SupplierDao;
import com.codecool.buyourstuff.model.Product;
import com.codecool.buyourstuff.model.ProductCategory;
import com.codecool.buyourstuff.model.Supplier;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoDB extends DaoDbConnectionData implements ProductDao {

    public ProductDaoDB() {
        setup();
    }

    private void setup() {
        final String SQL = "CREATE table IF NOT EXISTS product( " +
                "product_id SERIAL PRIMARY KEY, " +
                "product_name VARCHAR, " +
                "description VARCHAR, " +
                "default_price INT, " +
                "currency VARCHAR, " +
                "product_category_id INT, " +
                "supplier_id INT, " +
                "CONSTRAINT fk_product_category " +
                "      FOREIGN KEY(product_category_id) " +
                "      REFERENCES product_category(product_category_id), " +
                "CONSTRAINT fk_supplier " +
                "      FOREIGN KEY(supplier_id) " +
                "      REFERENCES supplier(supplier_id));";

        System.out.println(DB_URL);
        System.out.println(userName);
        System.out.println(pass);
        try (Connection con = DriverManager.getConnection(DB_URL, userName, pass)) {
            PreparedStatement st = con.prepareStatement(SQL);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void add(Product product) {
        final String SQL = "INSERT INTO product (product_name, description, default_price, " +
                "currency, product_category_id, supplier_id) VALUES (?,?,?,?,?,?)";

        try (Connection con = DriverManager.getConnection(DB_URL, userName, pass)) {
            PreparedStatement st = con.prepareStatement(SQL);
            st.setString(1, product.getName());
            st.setString(2, product.getDescription());
            st.setInt(3, product.getDefaultPrice().intValue());
            st.setString(4, product.getDefaultCurrency().toString());
            st.setInt(5, product.getProductCategory().getId());
            st.setInt(6, product.getSupplier().getId());

            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Product find(int id) {
        ProductCategoryDao productCategoryDao = DataManager.getProductCategoryDao();
        SupplierDao supplierDao = DataManager.getSupplierDao();

        final String SQL = "SELECT product_id, product_name, default_price, currency, description, " +
                "product_category_id, supplier_id FROM product WHERE product_id = ?";

        try (Connection con = DriverManager.getConnection(DB_URL, userName, pass)) {
            PreparedStatement st = con.prepareStatement(SQL);
            st.setInt(1, id);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String name = rs.getString(2);
                BigDecimal defaultPrice = new BigDecimal(rs.getInt(3));
                String currency = rs.getString(4);
                String description = rs.getString(5);
                ProductCategory productCategory = productCategoryDao.find(rs.getInt(6));
                Supplier supplier = supplierDao.find(rs.getInt(7));

                Product product = new Product(name, defaultPrice, currency, description, productCategory, supplier);
                product.setId(id);

                return product;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void remove(int id) {
        final String SQL = "DELETE FROM product WHERE product_id = ?;";

        try (Connection con = DriverManager.getConnection(DB_URL, userName, pass)) {
            PreparedStatement st = con.prepareStatement(SQL);

            st.setInt(1, id);

            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clear() {
        final String SQL = "DELETE FROM product;";

        try (Connection con = DriverManager.getConnection(DB_URL, userName, pass)) {
            PreparedStatement st = con.prepareStatement(SQL);

            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        ProductCategoryDao productCategoryDao = DataManager.getProductCategoryDao();

        List<Product> products = new ArrayList<>();

        int supplierId = supplier.getId();

        final String SQL = "SELECT product_id, product_name, default_price, currency, description, " +
                "product_category_id, supplier_id FROM product WHERE supplier_id = ?";

        try (Connection con = DriverManager.getConnection(DB_URL, userName, pass)) {
            PreparedStatement st = con.prepareStatement(SQL);
            st.setInt(1, supplierId);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                BigDecimal defaultPrice = new BigDecimal(rs.getInt(3));
                String currency = rs.getString(4);
                String description = rs.getString(5);
                ProductCategory productCategory = productCategoryDao.find(rs.getInt(6));

                Product product = new Product(name, defaultPrice, currency, description, productCategory, supplier);
                product.setId(id);

                products.add(product);
            }

            return products;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        SupplierDao supplierDao = DataManager.getSupplierDao();

        List<Product> products = new ArrayList<>();

        int productCategoryId = productCategory.getId();

        final String SQL = "SELECT product_id, product_name, default_price, currency, description, " +
                "product_category_id, supplier_id FROM product WHERE product_category_id = ?";

        try (Connection con = DriverManager.getConnection(DB_URL, userName, pass)) {
            PreparedStatement st = con.prepareStatement(SQL);
            st.setInt(1, productCategoryId);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                BigDecimal defaultPrice = new BigDecimal(rs.getInt(3));
                String currency = rs.getString(4);
                String description = rs.getString(5);
                Supplier supplier = supplierDao.find(rs.getInt(7));

                Product product = new Product(name, defaultPrice, currency, description, productCategory, supplier);
                product.setId(id);

                products.add(product);
            }

            return products;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Product> getAll() {
        ProductCategoryDao productCategoryDao = DataManager.getProductCategoryDao();
        SupplierDao supplierDao = DataManager.getSupplierDao();

        List<Product> products = new ArrayList<>();

        final String SQL = "SELECT product_id, product_name, default_price, currency, description, " +
                "product_category_id, supplier_id FROM product";

        try (Connection con = DriverManager.getConnection(DB_URL, userName, pass)) {
            PreparedStatement st = con.prepareStatement(SQL);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                BigDecimal defaultPrice = new BigDecimal(rs.getInt(3));
                String currency = rs.getString(4);
                String description = rs.getString(5);
                ProductCategory productCategory = productCategoryDao.find(rs.getInt(6));
                Supplier supplier = supplierDao.find(rs.getInt(7));

                Product product = new Product(name, defaultPrice, currency, description, productCategory, supplier);
                product.setId(id);

                products.add(product);
            }

            return products;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
