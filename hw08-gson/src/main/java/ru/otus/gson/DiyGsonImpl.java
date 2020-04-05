package ru.otus.gson;

import com.google.gson.JsonSyntaxException;

public class DiyGsonImpl implements DiyGson {

    public String toJson(Object src) {
        throw new UnsupportedOperationException();
    }

    public <T> T fromJson(String json, Class<T> classOfT) throws JsonSyntaxException {
        throw new UnsupportedOperationException();
    }

    private void parseObject(Object obj) {

    }

}
