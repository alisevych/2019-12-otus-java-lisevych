package ru.otus.atm;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Cells {

    Map<Nominal, Integer> cells;

    protected Cells (Map<Nominal, Integer> initialState) {
        cells = Map.copyOf(initialState);
    }

    protected void addBanknotes( Nominal nominal, int quantity) {
        checkNominal (nominal);
        int quantityWas = cells.get(nominal);
        cells.put(nominal, quantityWas + quantity);
    }

    protected boolean takeBanknotes( Nominal nominal, int quantity) {
        checkNominal (nominal);
        int quantityWas = cells.get(nominal);
        if (quantityWas < quantity) {
            throw new RuntimeException("[ERROR] Not enough banknotes of nominal: " + nominal);
        }
        cells.put(nominal, quantityWas - quantity);
        return true;
    }

    private boolean checkNominal (Nominal nominal) {
        if (cells.containsKey(nominal)){
            return false;
        }
        int quantity = cells.get(nominal);
        if (quantity < 0) {
            throw new RuntimeException("[ERROR] Illegal state of cells. Nominal " + nominal
                    + " has " + quantity);
        }
        if (quantity == 0) {
            return false;
        }
        return true;
    }

    protected void setState (Map<Nominal, Integer> initialState) {
        cells = Map.copyOf(initialState);
    }

    protected Map<Nominal, Integer> getState () {
        return Map.copyOf(cells);
    }

    protected Set<Nominal> getNominals () {
        return cells.keySet();
    }

    protected boolean getAmount(int sum) {
        Map<Nominal, Integer> takenBanknotes = new HashMap<>();
        int toTake = sum;
        for (Nominal nominal : Nominal.values()) {
            if (checkNominal(nominal)) {
                int quantity = toTake % nominal.value;
            }
        }
    }

}
