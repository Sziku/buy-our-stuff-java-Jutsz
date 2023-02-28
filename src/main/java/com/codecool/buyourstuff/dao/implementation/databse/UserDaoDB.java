package com.codecool.buyourstuff.dao.implementation.databse;

import com.codecool.buyourstuff.dao.CartDao;
import com.codecool.buyourstuff.dao.DataManager;
import com.codecool.buyourstuff.dao.UserDao;
import com.codecool.buyourstuff.model.Cart;
import com.codecool.buyourstuff.model.User;
import com.codecool.buyourstuff.model.exception.DataNotFoundException;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDaoDB extends DaoDbConnectionData implements UserDao {

    public UserDaoDB() {
        setup();
    }

    private void setup() {
        final String SQL = "CREATE TABLE IF NOT EXISTS users( " +
                "user_id SERIAL PRIMARY KEY, " +
                "user_name VARCHAR, " +
                "password VARCHAR, " +
                "cart_id INT, " +
                "CONSTRAINT fk_cart " +
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
    public void add(User user) {
        CartDao cartDao = DataManager.getCartDao();
        Cart cart = new Cart();
        cartDao.add(cart);

        final String SQL = "INSERT INTO users(user_name, password, cart_id) VALUES (?,?,?)";

        try (Connection con = DriverManager.getConnection(DB_URL, userName, pass)) {
            PreparedStatement st = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, user.getName());
            st.setString(2, user.getPassword());
            st.setInt(3, cart.getId());

            st.executeUpdate();

            ResultSet resultSet = st.getGeneratedKeys();
            resultSet.next();
            long generatedId = resultSet.getLong(1);
            user.setId((int) generatedId);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User find(String name, String password) {

        final String SQL = "SELECT user_id, user_name, password, cart_id FROM users" +
                " WHERE user_name = ?;";

        try (Connection con = DriverManager.getConnection(DB_URL, userName, pass)) {
            PreparedStatement st = con.prepareStatement(SQL);
            st.setString(1, name);

            ResultSet rs = st.executeQuery();

            if (!rs.next()) {
                throw new DataNotFoundException("No such user");
            }

            int id = rs.getInt(1);
            String savedName = rs.getString(2);
            String savedPassword = rs.getString(3);
            int cartId = rs.getInt(4);

            if (savedName.equals(name) && BCrypt.checkpw(password, savedPassword)) {
                User user = new User(name, password);
                user.setId(id);
                user.setCartId(cartId);
                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    @Override
    public void clear() {
        final String SQL = "DELETE FROM users;";

        try (Connection con = DriverManager.getConnection(DB_URL, userName, pass
        )) {
            PreparedStatement st = con.prepareStatement(SQL);

            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean isNameAvailable(String username) {
        final String SQL = "SELECT user_name FROM users" +
                " WHERE user_name = ? ;";

        try (Connection con = DriverManager.getConnection(DB_URL, userName, pass
        )) {
            PreparedStatement st = con.prepareStatement(SQL);
            st.setString(1, userName);

            ResultSet rs = st.executeQuery();

            return !rs.next();


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
