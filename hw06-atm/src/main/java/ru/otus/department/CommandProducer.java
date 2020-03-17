package ru.otus.department;

import ru.otus.atm.commands.CommandListener;
import ru.otus.atm.commands.Command;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sergey
 * created on 11.09.18.
 * updated by @alisevych
 */
class CommandProducer {

  private final List<CommandListener> listeners = new ArrayList<>();

  void addListener(CommandListener listener) {
    listeners.add(listener);
  }

  void removeListener(CommandListener listener) {
    listeners.remove(listener);
  }

  void send(long key, Command command) {
    listeners.forEach(listener -> listener.onCommand(key, command));
  }
}
