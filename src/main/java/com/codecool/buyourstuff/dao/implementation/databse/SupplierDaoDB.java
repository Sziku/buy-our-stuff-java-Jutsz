package com.codecool.buyourstuff.dao.implementation.databse;

import com.codecool.buyourstuff.dao.SupplierDao;
import com.codecool.buyourstuff.model.ProductCategory;
import com.codecool.buyourstuff.model.Supplier;
import com.codecool.buyourstuff.model.exception.DataNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDaoDB extends DaoDbConnectionData implements SupplierDao {
    public SupplierDaoDB() {
        setup();
    }

    private void setup() {
        final String SQL = "CREATE TABLE IF NOT EXISTS supplier( " +
                "supplier_id SERIAL PRIMARY KEY, " +
                "supplier_name VARCHAR, " +
                "description VARCHAR);";

        try (Connection con = DriverManager.getConnection(DB_URL, userName, pass)) {
            PreparedStatement st = con.prepareStatement(SQL);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void add(Supplier supplier) {
        final String SQL = "INSERT INTO supplier(supplier_name, description) VALUES (?,?)";

        try (Connection con = DriverManager.getConnection(DB_URL, userName, pass)) {
            PreparedStatement st = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, supplier.getName());
            st.setString(2, supplier.getDescription());

            st.executeUpdate();

            ResultSet resultSet = st.getGeneratedKeys();
            resultSet.next();
            long generatedId = resultSet.getLong(1);
            supplier.setId((int) generatedId);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Supplier find(int id) {

        final String SQL = "SELECT supplier_id, supplier_name, " +
                "description FROM supplier WHERE supplier_id = ?;";

        try (Connection con = DriverManager.getConnection(DB_URL, userName, pass)) {
            PreparedStatement st = con.prepareStatement(SQL);
            st.setInt(1, id);

            ResultSet rs = st.executeQuery();

            if (!rs.next()) {
                throw new DataNotFoundException("No such supplier");
            }

            Supplier supplier = new Supplier(rs.getString(2), rs.getString(3));
            supplier.setId(rs.getInt(1));

            return supplier;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void remove(int id) {
        final String SQL = "DELETE FROM supplier WHERE supplier_id = ?;";

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
        final String SQL = "DELETE FROM supplier;";

        try (Connection con = DriverManager.getConnection(DB_URL, userName, pass)) {
            PreparedStatement st = con.prepareStatement(SQL);

            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Supplier> getAll() {
        final String SQL = "SELECT supplier_id, supplier_name, description FROM supplier;";

        try (Connection con = DriverManager.getConnection(DB_URL, userName, pass)) {
            PreparedStatement st = con.prepareStatement(SQL);

            ResultSet rs = st.executeQuery();

            List<Supplier> suppliers = new ArrayList<>();

            while (rs.next()) {
                Supplier supplier = new Supplier(rs.getString(2), rs.getString(3));
                supplier.setId(rs.getInt(1));
                suppliers.add(supplier);
            }

            return suppliers;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
