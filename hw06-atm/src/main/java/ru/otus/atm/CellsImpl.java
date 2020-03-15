package ru.otus.atm;

import ru.otus.money.Bundle;
import ru.otus.money.BundleImpl;
import ru.otus.money.Nominal;

import java.util.*;

public class CellsImpl implements Cells {

    private Map<Nominal, Integer> cells;

    protected CellsImpl(State initialState) {
        cells = new TreeMap<>(initialState.getMap());
    }

    private void addToNominal(Nominal nominal, int quantity) {
        int quantityWas = cells.get(nominal);
        cells.put(nominal, quantityWas + quantity);
    }

    @Override
    public boolean inputBanknotes(Bundle banknotes) {
        if (!cells.keySet().containsAll(banknotes.getNominals())){
            System.out.println("[ERROR] There are unsupported nominals in banknotes. \nPlease take your money back.");
            return false;
        }
        banknotes.getMap().forEach(this::addToNominal);
        return true;
    }

    /**
     * takeAmountOut takes maximum from largest nominals first
     */
    @Override
    public Bundle takeAmountOut(int amount) {
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
            throw new RuntimeException("[ERROR] Could not get required sum from CellsImpl.");
        }
        if (remainingSum < 0){
            throw new IllegalStateException("[ERROR] CellsImpl. Remaining sum is : " + remainingSum);
        }
        withdrawAmount(toTake);
        return new BundleImpl(toTake);
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
    public boolean setState (State cellsState) {
        cells = new TreeMap<>(cellsState.getMap());
        return true;
    }

    @Override
    public State getState () {
        return new StateImpl(cells);
    }

}
