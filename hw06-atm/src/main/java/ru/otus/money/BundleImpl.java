package ru.otus.money;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import static ru.otus.helpers.TreeMapHelper.deepMapCopy;

public class BundleImpl implements Bundle {

    private final Map<Nominal, Integer> banknotes;

    public BundleImpl() {
        banknotes = new TreeMap<>();
    }

    public BundleImpl(Map<Nominal, Integer> map) {
        banknotes = new TreeMap<>(map);
    }

    @Override
    public Map<Nominal, Integer> getMapCopy() {
        return deepMapCopy(banknotes);
    }

    @Override
    public Set<Nominal> getNominals() {
        return banknotes.keySet();
    }

    @Override
    public Bundle addBanknotes(Nominal nominal, int quantity) {
        int existing = 0;
        if (banknotes.containsKey(nominal)) {
            existing = banknotes.get(nominal);
        }
        banknotes.put(nominal , quantity + existing);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        banknotes.forEach((n,v) -> result.append(n).append(" - ").append(v).append("\n"));
        return result.toString();
    }

    @Override
    public boolean equals( Object o) {
        if (!o.getClass().equals(this.getClass())){
            throw new IllegalArgumentException("Object should be of class " + this.getClass());
        }
        return banknotes.equals(((BundleImpl) o).getMapCopy());
    }
}
