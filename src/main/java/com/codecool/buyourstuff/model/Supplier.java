package com.codecool.buyourstuff.model;


public class Supplier extends BaseModel {
    private final String name;
    private final String description;

    public Supplier(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String toString() {
        return String.format("%1$s={" +
                        "id: %2$d, " +
                        "name: %3$s, " +
                        "description: %4$s}",
                getClass().getSimpleName(),
                id,
                name,
                description
        );
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
