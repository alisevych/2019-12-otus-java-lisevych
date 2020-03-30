package ru.otus.gson;

import java.util.Map;
import java.util.Objects;

public class BagWithTwoMaps {
  private final int value1;
  private final Map<String, String> map1;
  private final Map<String, Integer> map2;
  private final String value3;

  BagWithTwoMaps(int value1, Map<String, String> map1, Map<String, Integer> map2, String value3) {
    this.value1 = value1;
    var helper = new TreeMapHelper();
    this.map1 = helper.deepMapCopy(map1);
    this.map2 = helper.deepMapCopy(map2);
    this.value3 = value3;
  }

  @Override
  public String toString() {
    return "BagWithMap{" +
        "value1=" + value1 +
        ", map1='" + map1 +
        ", map2='" + map2 +
        ", value3=" + value3 +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    BagWithTwoMaps that = (BagWithTwoMaps) o;
    var map1Entries1 = map1.entrySet();
    var map1Entries2 = that.map1.entrySet();
    var map2Entries1 = map2.entrySet();
    var map2Entries2 = that.map2.entrySet();
    return value1 == that.value1 &&
            value3.equals(that.value3) &&
            map1Entries1.containsAll(map1Entries2) &&
            map1Entries2.containsAll(map1Entries1) &&
            map2Entries1.containsAll(map2Entries2) &&
            map2Entries2.containsAll(map2Entries1);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value1, map1, map2, value3);
  }
}
