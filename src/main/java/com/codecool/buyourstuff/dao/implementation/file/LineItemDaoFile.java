package com.codecool.buyourstuff.dao.implementation.file;

import com.codecool.buyourstuff.dao.LineItemDao;
import com.codecool.buyourstuff.model.*;
import com.codecool.buyourstuff.model.exception.DataNotFoundException;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LineItemDaoFile implements LineItemDao {
    private String PATH = "src/main/java/com/codecool/buyourstuff/datafiles/lineitem.csv";
    private List<LineItem> data = new ArrayList<>();

    @Override
    public void add(LineItem lineItem) {
        load();
        lineItem.setId(data.size() + 1);
        data.add(lineItem);
        save();
    }

    @Override
    public void remove(LineItem lineItem) {
        load();
        data.removeIf(item -> item.getId() == lineItem.getId());
        save();
    }

    @Override
    public void clear() {
        data = new ArrayList<>();
        save();
    }

    @Override
    public void update(LineItem lineItem, int quantity) {
        load();
        data
                .stream()
                .filter(
                        item -> item.getId() == lineItem.getId()
                )
                .findFirst()
                .ifPresent(
                        item -> item.setQuantity(quantity)
                );
        save();
    }

    @Override
    public LineItem find(int id) {
        load();
        return data
                .stream()
                .filter(item -> item.getId() == id)
                .findFirst()
                .orElseThrow(() -> new DataNotFoundException("No such line-item"));
    }

    @Override
    public List<LineItem> getBy(Cart cart) {
        load();
        return data
                .stream()
                .filter(
                        lineItem -> lineItem.getCartId() == cart.getId()
                )
                .collect(Collectors.toList());
    }

    private void save() {

        File file = new File(PATH);
        try {
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            StringBuilder s = new StringBuilder();

            for (LineItem lineItem : data) {
                s.append(lineItem.getCartId()); //0
                s.append(";");
                s.append(lineItem.getId()); //1
                s.append(";");
                s.append(lineItem.getQuantity()); //2
                s.append(";");
                s.append(lineItem.getProduct().getId()); //3
                s.append(";");
                s.append(lineItem.getProduct().getDefaultPrice()); //4
                s.append(";");
                s.append(lineItem.getProduct().getDefaultCurrency()); //5
                s.append(";");
                s.append(lineItem.getProduct().getDescription()); //6
                s.append(";");
                s.append(lineItem.getProduct().getName()); //7
                s.append(";");
                s.append(lineItem.getProduct().getProductCategory().getId()); //8
                s.append(";");
                s.append(lineItem.getProduct().getSupplier().getId()); //9
                s.append(";");
                s.append(lineItem.getProduct().getSupplier().getName()); //10
                s.append(";");
                s.append(lineItem.getProduct().getSupplier().getDescription()); //11
                s.append(";");
                s.append(lineItem.getProduct().getProductCategory().getName()); //12
                s.append(";");
                s.append(lineItem.getProduct().getProductCategory().getDescription()); //13
                s.append(";");
                s.append(lineItem.getProduct().getProductCategory().getDepartment()); //14
                s.append("\n");
            }
            bw.write(s.toString());
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private void load() {
        data = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(PATH));
            String st = br.readLine();
            while (st != null) {
                String[] loadRow = st.split(";");

                int id = Integer.parseInt(loadRow[1]);
                int cartId = Integer.parseInt(loadRow[0]);
                int quantity = Integer.parseInt(loadRow[2]);

                int productId = Integer.parseInt(loadRow[3]);
                ;
                String name = loadRow[7];
                BigDecimal defaultPrice = new BigDecimal(loadRow[4]);
                String currencyString = loadRow[5];
                String description = loadRow[6];

                int proId = Integer.parseInt(loadRow[8]);
                String proName = loadRow[12];
                String proDesc = loadRow[13];
                String proDepartment = loadRow[14];

                ProductCategory productCategory = new ProductCategory(proName, proDesc, proDepartment);
                productCategory.setId(proId);

                int supId = Integer.parseInt(loadRow[9]);
                String suppname = loadRow[10];
                String suppDist = loadRow[11];
                Supplier supplier = new Supplier(suppname, suppDist);
                supplier.setId(supId);


                Product product = new Product(name, defaultPrice, currencyString, description, productCategory, supplier);
                product.setId(productId);

                LineItem lineItem = new LineItem(product, cartId, quantity);
                lineItem.setId(id);

                data.add(lineItem);
                st = br.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
