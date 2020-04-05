package ru.otus.gson;

import com.google.gson.JsonSyntaxException;

public interface DiyGson {

    String toJson(Object src);

    <T> T fromJson(String json, Class<T> classOfT) throws JsonSyntaxException;

}
