package com.codecool.buyourstuff.dao.implementation.databse;

import com.codecool.buyourstuff.dao.ProductCategoryDao;
import com.codecool.buyourstuff.model.ProductCategory;
import com.codecool.buyourstuff.model.exception.DataNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryDaoDB extends DaoDbConnectionData implements ProductCategoryDao {

    public ProductCategoryDaoDB() {
        setup();
    }

    private void setup() {
        final String SQL = "CREATE TABLE IF NOT EXISTS product_category" +
                "(product_category_id SERIAL PRIMARY KEY, " +
                "product_category_name VARCHAR, " +
                "department VARCHAR, " +
                "description VARCHAR)";

        try (Connection con = DriverManager.getConnection(DB_URL, userName, pass)) {
            PreparedStatement st = con.prepareStatement(SQL);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void add(ProductCategory category) {
        final String SQL = "INSERT INTO product_category (product_category_name, " +
                "department, description) VALUES (?,?,?)";

        try (Connection con = DriverManager.getConnection(DB_URL, userName, pass)) {
            PreparedStatement st = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, category.getName());
            st.setString(2, category.getDepartment());
            st.setString(3, category.getDescription());

            st.executeUpdate();

            ResultSet resultSet = st.getGeneratedKeys();
            resultSet.next();
            long generatedId = resultSet.getLong(1);
            category.setId((int) generatedId);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public ProductCategory find(int id) {

        final String SQL = "SELECT product_category_id, product_category_name, " +
                "department, description FROM product_category WHERE product_category_id = ?;";

        try (Connection con = DriverManager.getConnection(DB_URL, userName, pass)) {
            PreparedStatement st = con.prepareStatement(SQL);
            st.setInt(1, id);

            ResultSet rs = st.executeQuery();

            if (!rs.next()) {
                throw new DataNotFoundException("No such category");
            }

            ProductCategory productCategory =
                    new ProductCategory(rs.getString(2), rs.getString(4),
                            rs.getString(3));
            productCategory.setId(rs.getInt(1));

            return productCategory;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void remove(int id) {
        final String SQL = "DELETE FROM product_category WHERE product_category_id = ?;";

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
        final String SQL = "DELETE FROM product_category;";

        try (Connection con = DriverManager.getConnection(DB_URL, userName, pass
        )) {
            PreparedStatement st = con.prepareStatement(SQL);

            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ProductCategory> getAll() {

        final String SQL = "SELECT product_category_id, product_category_name, " +
                "department, description FROM product_category ";

        try (Connection con = DriverManager.getConnection(DB_URL, userName, pass
        )) {
            PreparedStatement st = con.prepareStatement(SQL);

            ResultSet rs = st.executeQuery();

            List<ProductCategory> productCategories = new ArrayList<>();

            while (rs.next()) {
                ProductCategory productCategory =
                        new ProductCategory(rs.getString(2), rs.getString(4),
                                rs.getString(3));
                productCategory.setId(rs.getInt(1));

                productCategories.add(productCategory);
            }

            return productCategories;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }
}
