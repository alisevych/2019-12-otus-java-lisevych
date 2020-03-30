package ru.otus.gson;

import java.util.Arrays;
import java.util.Objects;

public class BagWithArray {
  private final int value1;
  private final String[] array;
  private final String value3;

  BagWithArray(int value1, String[] array2, String value3) {
    this.value1 = value1;
    this.array = array2;
    this.value3 = value3;
  }

  @Override
  public String toString() {
    return "BagWithArray{" +
        "value1=" + value1 +
        ", array='" + array + '\'' +
        ", value3=" + value3 +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    BagWithArray that = (BagWithArray) o;
    return value1 == that.value1 &&
            value3.equals(that.value3) &&
            Arrays.equals(array, that.array);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value1, array, value3);
  }
}
