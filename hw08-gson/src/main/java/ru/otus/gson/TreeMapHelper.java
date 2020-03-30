package ru.otus.gson;

import java.util.Map;
import java.util.TreeMap;

public class TreeMapHelper<K,V> {

    public Map<K, V> deepMapCopy(Map<K, V> initialMap) {
        var copy = new TreeMap<K,V>();
        for (var cell : initialMap.entrySet()){
            copy.put(cell.getKey(), cell.getValue());
        }
        return copy;
    }

}
