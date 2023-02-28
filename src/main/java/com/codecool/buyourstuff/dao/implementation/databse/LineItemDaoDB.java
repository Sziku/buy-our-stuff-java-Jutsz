package com.codecool.buyourstuff.dao.implementation.databse;

import com.codecool.buyourstuff.dao.DataManager;
import com.codecool.buyourstuff.dao.LineItemDao;
import com.codecool.buyourstuff.dao.ProductDao;
import com.codecool.buyourstuff.model.*;
import com.codecool.buyourstuff.model.exception.DataNotFoundException;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LineItemDaoDB extends DaoDbConnectionData implements LineItemDao {

    public LineItemDaoDB() {
        setup();
    }

    private void setup() {
        final String SQL = "CREATE TABLE IF NOT exists line_item" +
                "(line_item_id SERIAL PRIMARY KEY, " +
                "quantity INT, " +
                "product_id INT, " +
                "cart_id INT, " +
                "CONSTRAINT fk_product " +
                "      FOREIGN KEY(product_id) " +
                "      REFERENCES product(product_id), " +
                "CONSTRAINT FK_cart " +
                "      FOREIGN KEY(cart_id) " +
                "      REFERENCES cart(cart_id));";

        try (Connection con = DriverManager.getConnection(DB_URL, userName, pass)) {
            PreparedStatement st = con.prepareStatement(SQL);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void add(LineItem lineItem) {
        final String SQL = "INSERT INTO line_item (quantity, product_id, cart_id) VALUES (?,?,?)";

        try (Connection con = DriverManager.getConnection(DB_URL, userName, pass)) {
            PreparedStatement st = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, lineItem.getQuantity());
            st.setInt(2, lineItem.getProduct().getId());
            st.setInt(3, lineItem.getCartId());

            st.executeUpdate();

            ResultSet resultSet = st.getGeneratedKeys();
            resultSet.next();
            long generatedId = resultSet.getLong(1);
            lineItem.setId((int) generatedId);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(LineItem lineItem) {
        final String SQL = "DELETE FROM line_item WHERE line_item_id = ?;";

        try (Connection con = DriverManager.getConnection(DB_URL, userName, pass)) {
            PreparedStatement st = con.prepareStatement(SQL);

            st.setInt(1, lineItem.getId());

            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void clear() {
        final String SQL = "DELETE FROM line_item;";

        try (Connection con = DriverManager.getConnection(DB_URL, userName, pass)) {
            PreparedStatement st = con.prepareStatement(SQL);

            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(LineItem lineItem, int quantity) {
        final String SQL = "UPDATE line_item SET quantity = ? WHERE line_item_id = ?;";

        try (Connection con = DriverManager.getConnection(DB_URL, userName, pass)) {
            PreparedStatement st = con.prepareStatement(SQL);
            st.setInt(1, quantity);
            st.setInt(2, lineItem.getId());

            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public LineItem find(int id) {
        ProductDao productDaoDB = DataManager.getProductDao();
        final String SQL = "SELECT line_item_id, quantity, product_id, cart_id FROM line_item Where line_item_id = ?";

        try (Connection con = DriverManager.getConnection(DB_URL, userName, pass)) {
            PreparedStatement st = con.prepareStatement(SQL);
            st.setInt(1, id);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Product product = productDaoDB.find(rs.getInt(3));
                int cartId = rs.getInt(4);
                int quantity = rs.getInt(2);

                LineItem lineItem = new LineItem(product, cartId, quantity);
                lineItem.setId(id);
                return lineItem;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<LineItem> getBy(Cart cart) {
        ProductDao productDaoDB = DataManager.getProductDao();
        final String SQL = "SELECT line_item_id, quantity, product_id, cart_id " +
                "FROM line_item Where cart_id = ? ORDER BY line_item_id";

        try (Connection con = DriverManager.getConnection(DB_URL, userName, pass)) {
            PreparedStatement st = con.prepareStatement(SQL);
            st.setInt(1, cart.getId());

            ResultSet rs = st.executeQuery();

            List<LineItem> lineItems = new ArrayList<>();

            while (rs.next()) {
                int id = rs.getInt(1);
                Product product = productDaoDB.find(rs.getInt(3));
                int cartId = rs.getInt(4);
                int quantity = rs.getInt(2);

                LineItem lineItem = new LineItem(product, cartId, quantity);
                lineItem.setId(id);

                lineItems.add(lineItem);
            }
            return lineItems;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
