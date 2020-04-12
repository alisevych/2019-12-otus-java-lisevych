package ru.otus.gson;

import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class DiyGsonImpl implements DiyGson {

    public String toJson(Object src) {
        if (src == null){
            return "null";
        }
        String jsonString = "{";
        for (Field field : src.getClass().getDeclaredFields()){
            jsonString += "\"" + field.getName() + "\":";
            Class fieldType = field.getType();
            if (fieldType.equals(String.class)) { //probably same for ENUM
                jsonString += "\"";
                jsonString += getFieldValue(field, src);
                jsonString += "\"";
            }
            if (fieldType.isPrimitive()) {
                jsonString += getFieldValue(field, src);
            }
            if (fieldType.isArray()) {
                jsonString += getValuesFromArray(field, src);
            }
            jsonString += ","; // ToDo skip the last field
        }
        if (jsonString.length() > 1) {
            return jsonString.substring(0, jsonString.length() - 1) + "}"; //chop last , and add }
        }
        return jsonString;
    }

    public <T> T fromJson(String json, Class<T> classOfT) throws JsonSyntaxException {
        if (json.equals("null")) {
            return null;
        }
        else
            throw new UnsupportedOperationException();
    }

    private Object getFieldValue(Field field, Object obj) {
        if (Modifier.isPrivate(field.getModifiers()))
            field.setAccessible(true);
        try {
            return field.get(obj);
        } catch ( IllegalAccessException e) {
            return null;
        }
    }

    private Object[] getValuesFromArray(Field field, Object src){
        if (Modifier.isPrivate(field.getModifiers()))
            field.setAccessible(true);
        Object array = null;
        try {
            array = field.get(src);
        } catch ( IllegalAccessException e) {
            return null;
        }
        if (array == null)
            return null;
        if (!array.getClass().isArray())
            throw new IllegalArgumentException("Array expected but found " + array.getClass());
        //toDo parse array
    }

}
