package com.codecool.buyourstuff.dao.implementation.file;

import com.codecool.buyourstuff.dao.CartDao;
import com.codecool.buyourstuff.dao.DataManager;
import com.codecool.buyourstuff.dao.UserDao;
import com.codecool.buyourstuff.model.Cart;
import com.codecool.buyourstuff.model.User;
import com.codecool.buyourstuff.model.exception.DataNotFoundException;
import org.mindrot.jbcrypt.BCrypt;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class UserDaoFile implements UserDao {
    private final String fileName = "src/main/java/com/codecool/buyourstuff/datafiles/users.csv";

    private void writeToFile(User user) {
        StringBuilder sb = new StringBuilder();
        sb.append(user.getId()).append(";");
        sb.append(user.getName()).append(";");
        sb.append(user.getPassword()).append(";");
        sb.append(user.getCartId()).append("\n");

        try (FileWriter fileWriter = new FileWriter(fileName, true)) {
            fileWriter.write(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(User user) {
        if (isNameAvailable(user.getName())) {
            CartDao cartDao = DataManager.getCartDao();
            Cart cart = new Cart();
            cartDao.add(cart);

            user.setCartId(cart.getId());
            user.setId(getNextId());
            writeToFile(user);
        }
    }

    private int getNextId() {
        int id = 0;
        try (Scanner myScanner = new Scanner(new File(fileName))) {
            while (myScanner.hasNextLine()) {
                String[] line = myScanner.nextLine().split(";");
                id = Integer.parseInt(line[0]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id + 1;
    }

    @Override
    public User find(String name, String password) {
        try (Scanner myScanner = new Scanner(new File(fileName))) {
            while (myScanner.hasNextLine()) {
                String[] line = myScanner.nextLine().split(";");

                int id = Integer.parseInt(line[0]);
                String savedName = line[1];
                String savedPassword = line[2];
                int cartId = Integer.parseInt(line[3]);

                if (savedName.equals(name) && BCrypt.checkpw(password, savedPassword)) {
                    User user = new User(name, password);
                    user.setId(id);
                    user.setCartId(cartId);
                    return user;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new DataNotFoundException("No such user");
    }

    @Override
    public void clear() {
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isNameAvailable(String name) {
        try (Scanner myScanner = new Scanner(new File(fileName))) {
            while (myScanner.hasNextLine()) {
                String[] line = myScanner.nextLine().split(";");

                String savedName = line[1];

                if (savedName.equals(name)) {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
