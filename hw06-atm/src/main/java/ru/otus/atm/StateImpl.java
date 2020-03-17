package ru.otus.atm;

import ru.otus.money.Nominal;
import java.util.Collections;
import java.util.Map;

import static ru.otus.helpers.TreeMapHelper.deepMapCopy;

public class StateImpl implements State {

    private final Map<Nominal, Integer> cellsState;

    public StateImpl(Map<Nominal, Integer> stateMap) {
        cellsState = deepMapCopy(stateMap);
    }

    public StateImpl(State base) {
        cellsState = base.getMapCopy();
    }

    public Map<Nominal, Integer> getImmutableMap() {
        return Collections.unmodifiableMap(cellsState);
    }

    @Override
    public Map<Nominal, Integer> getMapCopy() {
        return deepMapCopy(cellsState);
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
        return cellsState.equals(((State) o).getImmutableMap());
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
