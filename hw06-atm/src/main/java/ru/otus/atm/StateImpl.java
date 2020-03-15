package ru.otus.atm;

import ru.otus.money.Nominal;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class StateImpl implements State {

    private final Map<Nominal, Integer> cellsState;

    public StateImpl(Map<Nominal, Integer> state) {
        cellsState = new TreeMap<>(state);
    }

    public StateImpl(StateImpl base) {
        cellsState = base.getMap();
    }

    public Map<Nominal, Integer> getMap() {
        return Collections.unmodifiableMap(cellsState);
    }

    public long calculateBalance() {
        long resultSum = 0;
        for( Map.Entry<Nominal, Integer> oneCell : cellsState.entrySet()) {
            resultSum += oneCell.getKey().value * oneCell.getValue();
        }
        return resultSum;
    }

    @Override
    public boolean equals( Object o) {
        if (!o.getClass().equals(this.getClass())){
            throw new IllegalArgumentException("Object should be of class " + this.getClass());
        }
        return cellsState.equals(((State) o).getMap());
    }

    @Override
    public String toString() {
        return "State{" +
                cellsState +
                '}';
    }

    public void print() {
        cellsState.forEach( (nominal, quantity) ->
                System.out.println(nominal + " - " + quantity));
    }

}
