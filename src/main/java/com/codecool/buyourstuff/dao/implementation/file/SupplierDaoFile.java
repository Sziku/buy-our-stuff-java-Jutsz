package com.codecool.buyourstuff.dao.implementation.file;

import com.codecool.buyourstuff.dao.SupplierDao;
import com.codecool.buyourstuff.model.Supplier;
import com.codecool.buyourstuff.model.exception.DataNotFoundException;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SupplierDaoFile implements SupplierDao {
    private final String fileName = "src/main/java/com/codecool/buyourstuff/datafiles/suppliers.csv";

    private List<Supplier> data = new ArrayList<>();

    private void readFromFile() {
        data = new ArrayList<>();

        try (Scanner myScanner = new Scanner(new File(fileName))) {
            while (myScanner.hasNextLine()) {
                String[] line = myScanner.nextLine().split(";");

                int id = Integer.parseInt(line[0]);
                String name = line[1];
                String description = line[2];

                Supplier supplier = new Supplier(name, description);
                supplier.setId(id);

                data.add(supplier);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeToFile() {
        StringBuilder sb = new StringBuilder();
        for (Supplier supplier : data) {
            sb.append(supplier.getId()).append(";");
            sb.append(supplier.getName()).append(";");
            sb.append(supplier.getDescription()).append("\n");
        }

        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(Supplier supplier) {
        readFromFile();
        supplier.setId(data.size() + 1);
        data.add(supplier);
        writeToFile();
    }

    @Override
    public Supplier find(int id) {
        readFromFile();
        return data
                .stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElseThrow(() -> new DataNotFoundException("No such supplier"));
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
    public List<Supplier> getAll() {
        readFromFile();
        return data;
    }
}
