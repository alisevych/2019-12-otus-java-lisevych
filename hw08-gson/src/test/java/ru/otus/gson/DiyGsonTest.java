package ru.otus.gson;

import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class DiyGsonTest {

    private final Gson gson = new Gson();

    @DisplayName("Object passed toJson() and Object returned back fromJson() are equal")
    @Test
    void toAndFromWithPrimitives() {
        BagOfPrimitives obj = new BagOfPrimitives(22, "test0", 10);
        System.out.println(obj);

        String json = gson.toJson(obj);
        System.out.println(json);

        BagOfPrimitives obj2 = gson.fromJson(json, BagOfPrimitives.class);
        System.out.println(obj2);
        System.out.println(obj.equals(obj2));
        assertTrue(obj.equals(obj2));
    }

    @Test
    void toAndFromWithArray() {
        String[] array = new String[]{"one", "two", "three"};
        BagWithArray obj = new BagWithArray(12, array, "Test1");
        System.out.println(obj);

        String json = gson.toJson(obj);
        System.out.println(json);

        BagWithArray obj2 = gson.fromJson(json, BagWithArray.class);
        System.out.println(obj2);
        System.out.println(obj.equals(obj2));
        assertTrue(obj.equals(obj2));
    }

    @Test
    void toAndFromWithList() {
        List<String> stringList = Arrays.asList( "one", "two", "three");
        BagWithList obj = new BagWithList(12, stringList, "Test2");
        System.out.println(obj);

        String json = gson.toJson(obj);
        System.out.println(json);

        BagWithList obj2 = gson.fromJson(json, BagWithList.class);
        System.out.println(obj2);
        System.out.println(obj.equals(obj2));
        assertTrue(obj.equals(obj2));
    }

    @Test
    void toAndFromWithMap() {
        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("1", "one");
        stringMap.put("2", "two");
        stringMap.put("3", "three");
        BagWithMap obj = new BagWithMap(13, stringMap, "Test3");
        System.out.println(obj);

        String json = gson.toJson(obj);
        System.out.println(json);

        BagWithMap obj2 = gson.fromJson(json, BagWithMap.class);
        System.out.println(obj2);
        System.out.println(obj.equals(obj2));
        assertTrue(obj.equals(obj2));
    }

    @Test
    void toAndFromWith2Maps() {
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

        var obj2 = gson.fromJson(json, BagWithTwoMaps.class);
        System.out.println(obj2);
        System.out.println(obj.equals(obj2));
        assertTrue(obj.equals(obj2));
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