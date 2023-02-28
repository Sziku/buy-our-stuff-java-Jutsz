package com.codecool.buyourstuff.dao.implementation.file;

import com.codecool.buyourstuff.dao.DataManager;
import com.codecool.buyourstuff.dao.ProductCategoryDao;
import com.codecool.buyourstuff.dao.ProductDao;
import com.codecool.buyourstuff.dao.SupplierDao;
import com.codecool.buyourstuff.model.Product;
import com.codecool.buyourstuff.model.ProductCategory;
import com.codecool.buyourstuff.model.Supplier;
import com.codecool.buyourstuff.model.exception.DataNotFoundException;

import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ProductDaoFile implements ProductDao {

    private final String fileName = "src/main/java/com/codecool/buyourstuff/datafiles/products.csv";

    private List<Product> data = new ArrayList<>();

    private void readFromFile() {
        ProductCategoryDao productCategoryDao = DataManager.getProductCategoryDao();
        SupplierDao supplierDao = DataManager.getSupplierDao();
        data = new ArrayList<>();

        try (Scanner myScanner = new Scanner(new File(fileName))) {
            while (myScanner.hasNextLine()) {
                String[] line = myScanner.nextLine().split(";");

                int id = Integer.parseInt(line[0]);
                String name = line[1];
                BigDecimal defaultPrice = new BigDecimal(line[2]);
                String currencyString = line[3];
                String description = line[4];
                ProductCategory productCategory = productCategoryDao.find(Integer.parseInt(line[5]));
                Supplier supplier = supplierDao.find(Integer.parseInt(line[6]));

                Product product = new Product(name, defaultPrice, currencyString, description, productCategory, supplier);
                product.setId(id);

                data.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeToFile() {
        StringBuilder sb = new StringBuilder();
        for (Product product : data) {
            sb.append(product.getId()).append(";");
            sb.append(product.getName()).append(";");
            sb.append(product.getDefaultPrice()).append(";");
            sb.append(product.getDefaultCurrency()).append(";");
            sb.append(product.getDescription()).append(";");
            sb.append(product.getProductCategory().getId()).append(";");
            sb.append(product.getSupplier().getId()).append("\n");
        }

        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(Product product) {
        readFromFile();
        product.setId(data.size() + 1);
        data.add(product);
        writeToFile();
    }

    @Override
    public Product find(int id) {
        readFromFile();
        return data
                .stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElseThrow(() -> new DataNotFoundException("No such product"));
    }

    @Override
    public void remove(int id) {
        readFromFile();
        data.remove(find(id));
        writeToFile();
    }

    @Override
    public void clear() {
        data = new ArrayList<>();
        writeToFile();
    }

    @Override
    public List<Product> getAll() {
        readFromFile();
        return data;
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        readFromFile();
        return data
                .stream()
                .filter(
                        t -> t.getSupplier().getId() == supplier.getId()
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        readFromFile();
        return data
                .stream()
                .filter(
                        t -> t.getProductCategory().getId() == productCategory.getId()
                )
                .collect(Collectors.toList());
    }
}
