package ru.otus.atm;

import ru.otus.money.Bundle;
import ru.otus.money.Nominal;

import java.util.List;

public class UserSession {

    private final AtmUser atm;
    private final long cardNumber;
    private long sessionKey = -1;

    public UserSession (AtmUser atm, long cardNumber) {
        this.atm = atm;
        this.cardNumber = cardNumber;
        System.out.println("User session is created");
    }

    public boolean enterPinCode(int pin) {
        sessionKey = atm.userLogin(cardNumber, pin);
        System.out.println("User entered pin code");
        if (sessionKey != -1) { // add counter of 3 attempts
            System.out.println("User is logged in");
            return true;
        }
        System.out.println("Login is rejected.");
        return false;
    }

    public Bundle getAmount(int sum){
        checkLoggedIn();
        System.out.println("Amount "+ sum + " is requested.");
        Bundle banknotesOut = atm.getAmount(sessionKey, sum);
        System.out.println(banknotesOut.toString());
        return banknotesOut;
    }

    public boolean inputBanknotes(Bundle banknotes) {
        checkLoggedIn();
        System.out.println("Input banknotes: ");
        System.out.println(banknotes.toString());
        return atm.inputBanknotes(sessionKey, banknotes);
    }

    public List<Nominal> getAvailableNominals(){
        checkLoggedIn();
        return atm.getAvailableNominals(sessionKey);
    }

    public void returnCard(){
        System.out.println("Take your card.");
        atm.userLogout();
    }

    private void checkLoggedIn(){
        if (sessionKey == -1 ) {
            throw new RuntimeException("[ERROR] You are not yet logged in.");
        }
    }

}
