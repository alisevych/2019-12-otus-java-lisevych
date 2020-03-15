package ru.otus.money;

import java.util.Map;
import java.util.Set;

public interface Bundle {

    Map<Nominal, Integer> getMap();

    Set<Nominal> getNominals();

    Bundle addBanknotes (Nominal nominal, int quantity);

}
