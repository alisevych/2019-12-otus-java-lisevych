package ru.otus.atm;

import java.util.*;

public class Cells implements ICells, ICellsService{

    private Map<Nominal, Integer> cells;

    protected Cells (Map<Nominal, Integer> initialState) {
        cells = new TreeMap<>(initialState);
    }

    private void addToNominal(Nominal nominal, int quantity) {
        int quantityWas = cells.get(nominal);
        cells.put(nominal, quantityWas + quantity);
    }

    @Override
    public void inputBanknotes(Map<Nominal, Integer> banknotes) {
        List<Nominal> availableNominals = getAvailableNominals();
        banknotes.forEach((nominal, qty) -> {
            if (!availableNominals.contains(nominal)) {
                throw new RuntimeException("[ERROR] Nominal " + nominal + " is not supported in this ATM.");
            }
        });
        banknotes.forEach(this::addToNominal);
    }

    /**
     * takeAmountOut takes maximum from largest nominals first
     */
    @Override
    public Map<Nominal, Integer> takeAmountOut(int amount) {
        Map<Nominal, Integer> toTake = new TreeMap<>();
        int remainingSum = amount;
        for (Nominal nominal : getAvailableNominals()) {
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
            takeBanknotesOut(nominal, toTake.get(nominal) );
        }
    }

    private void takeBanknotesOut(Nominal nominal, int quantityToTake) {
        int quantityAvailable = getNominalQty(nominal);
        if (quantityAvailable < quantityToTake) {
            throw new RuntimeException("[ERROR] For nominal: " + nominal + " requested qty is: " + quantityToTake +
                    "; available qty is: " + quantityAvailable);
        }
        cells.put(nominal, quantityAvailable - quantityToTake);
    }

    private int getNominalQty(Nominal nominal) {
        int quantity = cells.get(nominal);
        if (quantity < 0) {
            throw new RuntimeException("[ERROR] Illegal state of cells. Nominal " + nominal
                    + " has " + quantity);
        }
        return quantity;
    }

    @Override
    public List<Nominal> getAvailableNominals() {
        List<Nominal> nominalList = new ArrayList<>(cells.keySet());
        Collections.sort(nominalList, (o1,o2) -> Integer.compare(o2.value, o1.value)); //reverse order
        return nominalList;
    }

    @Override
    public void setState (Map<Nominal, Integer> initialState) {
        cells = new TreeMap<>(initialState);
    }

    @Override
    public Map<Nominal, Integer> getState () {
        printState();
        return Map.copyOf(cells); //immutable
    }

    private void printState() {
        getAvailableNominals().forEach(nominal ->
                System.out.println(nominal + " - " + cells.get(nominal)));
    }

}
