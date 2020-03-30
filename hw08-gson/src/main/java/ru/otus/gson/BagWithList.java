package ru.otus.gson;

import java.util.*;

public class BagWithList {
  private final int value1;
  private final List<String> stringList;
  private final String value3;

  BagWithList(int value1, List<String> stringList1, String value3) {
    this.value1 = value1;
    this.stringList = stringList1;
    this.value3 = value3;
  }

  @Override
  public String toString() {
    return "BagWithList{" +
        "value1=" + value1 +
        ", stringList='" + stringList +
        ", value3=" + value3 +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    BagWithList that = (BagWithList) o;
    return value1 == that.value1 &&
            value3.equals(that.value3) &&
            stringList.containsAll(that.stringList) &&
            that.stringList.containsAll(stringList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value1, stringList, value3);
  }
}
