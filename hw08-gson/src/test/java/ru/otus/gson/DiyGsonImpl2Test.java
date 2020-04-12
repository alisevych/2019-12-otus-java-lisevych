package ru.otus.gson;

import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class DiyGsonImpl2Test {

    private final Gson gson = new Gson();
    private final DiyGson myGson = new DiyGsonImpl();

    @DisplayName("My impl of toJson() and Gson impl results are equal")
    @Test
    void toJsonPrimitives() {
        BagOfPrimitives obj = new BagOfPrimitives(22, "test0", 10);
        System.out.println(obj);

        String json = gson.toJson(obj);
        System.out.println(json);
        String myJson = myGson.toJson(obj);
        System.out.println(myJson);

        assertEquals(myJson, json);
    }

    @Test
    void toJsonWithArray() {
        String[] array = new String[]{"one", "two", "three"};
        BagWithArray obj = new BagWithArray(12, array, "Test1");
        System.out.println(obj);

        String json = gson.toJson(obj);
        System.out.println(json);
        String myJson = myGson.toJson(obj);
        System.out.println(myJson);

        assertEquals(myJson, json);
    }

    @Test
    void toJsonWithList() {
        List<String> stringList = Arrays.asList( "one", "two", "three");
        BagWithList obj = new BagWithList(12, stringList, "Test2");
        System.out.println(obj);

        String json = gson.toJson(obj);
        System.out.println(json);
        String myJson = myGson.toJson(obj);
        System.out.println(myJson);

        assertEquals(myJson, json);
    }

    @Test
    void toJsonWithMap() {
        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("1", "one");
        stringMap.put("2", "two");
        stringMap.put("3", "three");
        BagWithMap obj = new BagWithMap(13, stringMap, "Test3");
        System.out.println(obj);

        String json = gson.toJson(obj);
        System.out.println(json);
        String myJson = myGson.toJson(obj);
        System.out.println(myJson);

        assertEquals(myJson, json);
    }

    @Test
    void toJsonWith2Maps() {
        Map<String, String> stringMap = new TreeMap<>();
        stringMap.put("1", "one");
        stringMap.put("2", "two");
        stringMap.put("3", "three");
        Map<String, Integer> stringIntMap = new HashMap<>();
        stringIntMap.put("1", 1);
        stringIntMap.put("2", 2);
        stringIntMap.put("3", 3);
        stringIntMap.put("4", 4);
        stringIntMap.put("0", 0);
        var obj = new BagWithTwoMaps(25, stringMap, stringIntMap, "Test4");
        System.out.println(obj);

        String json = gson.toJson(obj);
        System.out.println(json);
        String myJson = myGson.toJson(obj);
        System.out.println(myJson);

        assertEquals(myJson, json);
    }

    @Test
    void nullToAndFromJson() {
        BagOfPrimitives obj = null;
        String json = gson.toJson(obj);
        System.out.println(json);

        BagOfPrimitives obj2 = gson.fromJson(json, BagOfPrimitives.class);
        System.out.println(obj2);
        assertEquals(json , "null");
        assertNull(obj2);
    }
}