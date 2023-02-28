package com.codecool.buyourstuff.model;

import com.codecool.buyourstuff.util.Error;
import java.lang.reflect.Field;

public abstract class BaseModel {

    protected int id;

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getSimpleName()).append("={");
        for (Field field : this.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value;
            try {
                value = field.get(this);
                if (value != null) {
                    stringBuilder.append(field.getName()).append(": ");
                    stringBuilder.append(value).append(", ");
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(Error.COULD_NOT_READ_FIELD, e);
            }
        }
        stringBuilder.setLength(stringBuilder.length() - 2);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
