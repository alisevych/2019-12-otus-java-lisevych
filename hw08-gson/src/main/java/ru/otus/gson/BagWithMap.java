package ru.otus.gson;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class BagWithMap {
  private final int value1;
  private final Map<String, String> map;
  private final String value3;

  BagWithMap(int value1, Map<String, String> map, String value3) {
    this.value1 = value1;
    this.map = map;
    this.value3 = value3;
  }

  @Override
  public String toString() {
    return "BagWithMap{" +
        "value1=" + value1 +
        ", map='" + map +
        ", value3=" + value3 +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    BagWithMap that = (BagWithMap) o;
    Set<Map.Entry<String , String>> mapEntries = map.entrySet();
    Set<Map.Entry<String , String>> mapEntries2 = that.map.entrySet();
    return value1 == that.value1 &&
            value3.equals(that.value3) &&
            mapEntries.containsAll(mapEntries2) &&
            mapEntries2.containsAll(mapEntries);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value1, map, value3);
  }
}
