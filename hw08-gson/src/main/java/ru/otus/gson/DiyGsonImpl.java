package ru.otus.gson;

import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class DiyGsonImpl implements DiyGson {

    public String toJson(Object src) {
        if (src == null){
            return "null";
        }
        var result = new StringBuilder("{");;
        for (Field field : src.getClass().getDeclaredFields()){
            result.append("\"").append(field.getName()).append("\":");
            result.append(parseField(field, src));
            result.append(",");
        }
        return replaceLastChar(result.toString(), '}');
    }

    public <T> T fromJson(String json, Class<T> classOfT) throws JsonSyntaxException {
        if (json.equals("null")) {
            return null;
        }
        else
            throw new UnsupportedOperationException();
    }

    private String parseField (Field field, Object obj) {
        var result = new StringBuilder();
        var fieldType = field.getType();
        if (fieldType.isPrimitive()) {
            result.append(getValueFromField(field, obj));
        } else if (fieldType.equals(String.class)) { //probably same for ENUM
            result.append("\"");
            result.append(getValueFromField(field, obj));
            result.append("\"");
        } else if (fieldType.isArray()) {
            result.append(getValuesFromArray(field, obj));
        }
        return result.toString();
    }

    private String getValueFromField(Field field, Object obj) {
        if (Modifier.isPrivate(field.getModifiers()))
            field.setAccessible(true);
        try {
            return field.get(obj).toString();
        } catch ( IllegalAccessException e) {
            return null;
        }
    }

    private String getValuesFromArray(Field field, Object src){
        if (Modifier.isPrivate(field.getModifiers()))
            field.setAccessible(true);
        Object value;
        try {
            value = field.get(src);
        } catch ( IllegalAccessException e) {
            return null;
        }
        if (value == null)
            return "null";
        if (!value.getClass().isArray())
            throw new IllegalArgumentException("Array type expected but found " + value.getClass());
        StringBuilder result = new StringBuilder("[");
        for (Object element: (Object[]) value) {
            if (element.getClass().isPrimitive()) {
                result.append(element);
            } else if (element.getClass().equals(String.class)) {
                result.append("\"");
                result.append(element);
                result.append("\"");
            } else {
                //ToDo correct parsing
                //result.append(parseField(element));
                result.append(",");
            }
        }
        return replaceLastChar(result.toString(), ']');
    }

    private String replaceLastChar(String string, Character replacer) {
        if (string == null) {
            return null;
        }
        String result = "";
        if (string.length() > 1) {
            result = string.substring(0, string.length() - 1);
        }
        if (replacer == null) {
            return result;
        }
        return result + replacer;
    }

}
