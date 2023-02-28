package com.codecool.buyourstuff.dao.implementation.file;

import com.codecool.buyourstuff.dao.ProductCategoryDao;
import com.codecool.buyourstuff.model.ProductCategory;
import com.codecool.buyourstuff.model.exception.DataNotFoundException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryDaoFile implements ProductCategoryDao {
    private String PATH = "src/main/java/com/codecool/buyourstuff/datafiles/ProductCategory.csv";
    private List<ProductCategory> data = new ArrayList<>();

    @Override
    public void add(ProductCategory category) {
        load();
        category.setId(data.size() + 1);
        data.add(category);
        save();
    }

    @Override
    public ProductCategory find(int id) {
        load();
        return data
                .stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElseThrow(() -> new DataNotFoundException("No such category"));
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
    public List<ProductCategory> getAll() {
       load();
        return data;
    }

    private void save(){

        File file = new File(PATH);
        try{
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            StringBuilder s = new StringBuilder();

            for (ProductCategory productCategory : data){

                s.append(productCategory .getId()); //0
                s.append(";");
                s.append(productCategory .getName()); //1
                s.append(";");
                s.append(productCategory .getDescription()); //2
                s.append(";");
                s.append(productCategory .getDepartment()); //3
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

    private void load(){
        data = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(PATH));
            String st =  br.readLine();
            while (st !=null){
                String[] loadRow = st.split(";");

                int proId = Integer.parseInt(loadRow[0]);
                String proName = loadRow[1];
                String proDesc = loadRow[2];
                String proDepartment = loadRow[3];

                ProductCategory productCategory = new ProductCategory(proName,proDesc,proDepartment);
                productCategory.setId(proId);

                data.add(productCategory);

                st =  br.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
