package ru.otus.helpers;

import ru.otus.money.Nominal;

import java.util.Map;
import java.util.TreeMap;

public class TreeMapHelper {

    public static Map<Nominal, Integer> deepMapCopy(Map<Nominal, Integer> initialMap) {
        Map<Nominal, Integer> copy = new TreeMap<>();
        for (Map.Entry<Nominal, Integer> cell : initialMap.entrySet()){
            copy.put(cell.getKey(), cell.getValue());
        }
        return copy;
    }

}
