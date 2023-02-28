package com.codecool.buyourstuff.dao.implementation.file;

import com.codecool.buyourstuff.dao.CartDao;
import com.codecool.buyourstuff.model.*;
import com.codecool.buyourstuff.model.exception.DataNotFoundException;

import java.io.*;
import java.util.*;

public class CartDaoFile implements CartDao {

    private  String PATH = "src/main/java/com/codecool/buyourstuff/datafiles/Carts.txt";
    private List<Cart> data = new ArrayList<>();

    @Override
    public void add(Cart cart) {
        load();
        cart.setId(data.size() + 1);
        data.add(cart);
       save();
    }

    @Override
    public Cart find(int id) {
        load();
        return data
                .stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElseThrow(() -> new DataNotFoundException("No such cart"));

    }

    @Override
    public void remove(int id) {
        load();
        data.remove(find(id));
        save();
    }

    @Override
    public void clear() {
        data = new ArrayList<>();
        save();
    }

    @Override
    public List<Cart> getAll() {
        load();
        return data;
    }
    private void save(){

        File file = new File(PATH);
        try{
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            StringBuilder s = new StringBuilder();

            for (Cart cart : data){
                s.append(cart.getId());
                s.append(";");
                s.append(cart.getCurrency());
                s.append("\n");
            }
            bw.write(s.toString());
            bw.close();
        }
        catch (IOException e){
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private  void load(){

            data = new ArrayList<>();

            try {
                BufferedReader br = new BufferedReader(new FileReader(PATH));
                String st = br.readLine();
                while (st != null) {
                    String[] loadRow = st.split(";");

                    int id = Integer.parseInt(loadRow[0]);
                    String currency = loadRow[1];
                    System.out.println(id);
                    System.out.println(currency);

                    Cart cart = new Cart(currency);
                    cart.setId(id);

                    data.add(cart);

                    st = br.readLine();
                }
                System.out.println(data);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }
}
