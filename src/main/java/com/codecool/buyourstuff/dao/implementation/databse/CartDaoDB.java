package com.codecool.buyourstuff.dao.implementation.databse;

import com.codecool.buyourstuff.dao.CartDao;
import com.codecool.buyourstuff.model.Cart;
import com.codecool.buyourstuff.model.exception.DataNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDaoDB extends DaoDbConnectionData implements CartDao {
    public CartDaoDB() {
        setup();
    }

    private void setup() {
        final String SQL = "CREATE table IF NOT exists cart(" +
                "cart_id SERIAL PRIMARY KEY, " +
                "currency VARCHAR);";

        try (Connection con = DriverManager.getConnection(DB_URL, userName, pass)) {
            PreparedStatement st = con.prepareStatement(SQL);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void add(Cart cart) {
        final String SQL = "INSERT INTO cart (currency) VALUES (?)";

        try (Connection con = DriverManager.getConnection(DB_URL, userName, pass)) {
            PreparedStatement st = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);

            st.setString(1, cart.getCurrency().toString());

            st.executeUpdate();

            ResultSet resultSet = st.getGeneratedKeys();
            resultSet.next();
            long generatedId = resultSet.getLong(1);
            cart.setId((int) generatedId);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Cart find(int id) {

        final String SQL = "SELECT cart_id, currency FROM cart WHERE cart_id = ?;";

        try (Connection con = DriverManager.getConnection(DB_URL, userName, pass)) {
            PreparedStatement st = con.prepareStatement(SQL);
            st.setInt(1, id);

            ResultSet rs = st.executeQuery();

            if (!rs.next()) {
                throw new DataNotFoundException("No such cart");
            }

            Cart cart = new Cart(rs.getString(2));
            cart.setId(rs.getInt(1));

            return cart;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void remove(int id) {
        final String SQL = "DELETE FROM cart WHERE cart_id = ?;";

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
        final String SQL = "DELETE FROM cart;";

        try (Connection con = DriverManager.getConnection(DB_URL, userName, pass)) {
            PreparedStatement st = con.prepareStatement(SQL);

            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Cart> getAll() {
        final String SQL = "SELECT cart_id, currency FROM cart;";

        try (Connection con = DriverManager.getConnection(DB_URL, userName, pass
        )) {
            PreparedStatement st = con.prepareStatement(SQL);

            ResultSet rs = st.executeQuery();

            List<Cart> carts = new ArrayList<>();

            while (rs.next()) {
                Cart cart = new Cart(rs.getString(2));
                cart.setId(rs.getInt(1));

                carts.add(cart);
            }

            return carts;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
