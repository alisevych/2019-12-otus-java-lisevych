package ru.otus.atm;

import java.util.*;

public class Cells implements ICells, ICellsService{

    Map<Nominal, Integer> cells;

    protected Cells (Map<Nominal, Integer> initialState) {
        cells = new TreeMap<>(initialState);
    }

    @Override
    public void addBanknotes( Nominal nominal, int quantity) {
        if (!isNominalPresent (nominal)) {
            throw new RuntimeException("[ERROR] Can't add banknotes to not existing cell. Nominal " + nominal);
        }
        int quantityWas = cells.get(nominal);
        cells.put(nominal, quantityWas + quantity);
    }

    /**
     * getAmount outputs the maximum qty of largest nominals
     */
    @Override
    public Map<Nominal, Integer> getAmount(int amount) {
        Map<Nominal, Integer> toTake = new TreeMap<>();
        int remainingSum = amount;
        for (Nominal nominal : getAvailableNominals()) {
            //ToDo verify logic
            int quantityNeeded = remainingSum / nominal.value;
            int quantityToTake = 0;
            if (quantityNeeded > 0) {
                int nominalAvailable = getNominalQty(nominal);
                if (nominalAvailable >= quantityNeeded) {
                    quantityToTake = quantityNeeded;
                }
                if ((nominalAvailable < quantityNeeded) && (nominalAvailable > 0)) {
                    quantityToTake = nominalAvailable;
                }
                if (quantityToTake > 0) {
                    toTake.put(nominal, quantityToTake);
                    remainingSum -= nominal.value * quantityToTake;
                }
            }
        }
        if (remainingSum > 0) {
            throw new RuntimeException("[ERROR] Could not get required sum from Cells.");
        }
        if (remainingSum < 0){
            throw new IllegalStateException("[ERROR] Cells. Remaining sum is : " + remainingSum);
        }
        withdrawAmount(toTake);
        return toTake;
    }

    private void withdrawAmount(Map<Nominal, Integer> toTake) {
        for (Nominal nominal : toTake.keySet()) {
            takeBanknotes(nominal, toTake.get(nominal) );
        }
    }

    private void takeBanknotes( Nominal nominal, int quantityToTake) {
        int quantityAvailable = getNominalQty(nominal);
        if (quantityAvailable < quantityToTake) {
            throw new RuntimeException("[ERROR] For nominal: " + nominal + " requested qty is: " + quantityToTake +
                    "; available qty is: " + quantityAvailable);
        }
        cells.put(nominal, quantityAvailable - quantityToTake);
    }

    private boolean isNominalPresent(Nominal nominal) {
        return cells.containsKey(nominal);
    }

    private int getNominalQty(Nominal nominal) {
        int quantity = cells.get(nominal);
        if (quantity < 0) {
            throw new RuntimeException("[ERROR] Illegal state of cells. Nominal " + nominal
                    + " has " + quantity);
        }
        return quantity;
    }

    protected List<Nominal> getAvailableNominals() {
        List<Nominal> nominalsList = new ArrayList<>(cells.keySet());
        Collections.sort(nominalsList, (o1,o2) -> Integer.compare(o2.value, o1.value));
        return nominalsList;
    }

    @Override
    public void setState (Map<Nominal, Integer> initialState) {
        cells = Map.copyOf(initialState);
    }

    @Override
    public Map<Nominal, Integer> getState () {
        return Map.copyOf(cells);
    }

}
