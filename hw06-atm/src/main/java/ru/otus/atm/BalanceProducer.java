package ru.otus.atm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sergey
 * created on 11.09.18.
 * updated by @alisevych
 */
public class BalanceProducer {

  private final List<BalanceListener> listeners = new ArrayList<>();

  public void addListener(BalanceListener listener) {
    listeners.add(listener);
  }

  public void removeListener(BalanceListener listener) {
    listeners.remove(listener);
  }

  public void sendBalance(String atmID, long balance) {
    listeners.forEach(listener -> listener.addAtmBalance( atmID, balance));
  }
}
