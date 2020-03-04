package ru.otus.atm;

import java.util.List;
import java.util.Map;

public class UserSession {

    private final IAtmUser atm;
    private final long cardNumber;
    private long sessionKey = -1;

    protected UserSession (IAtmUser atm, long cardNumber) {
        this.atm = atm;
        this.cardNumber = cardNumber;
        System.out.println("User session is created");
    }

    protected boolean enterPinCode(int pin) {
        sessionKey = atm.userLogin(cardNumber, pin);
        System.out.println("User entered pin code");
        if (sessionKey != -1) { // add counter of 3 attempts
            System.out.println("User is logged in");
            return true;
        }
        System.out.println("Login is rejected.");
        return false;
    }

    public Map<Nominal, Integer> getAmount(int sum){
        checkLoggedIn();
        System.out.println("Amount "+ sum + " is requested.");
        Map<Nominal, Integer> banknotesOut = atm.getAmount(sessionKey, sum);
        printBanknotesMap(banknotesOut);
        return banknotesOut;
    }

    public boolean inputBanknotes(Map<Nominal, Integer> banknotes) {
        checkLoggedIn();
        System.out.println("Input banknotes: ");
        printBanknotesMap(banknotes);
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

    private void printBanknotesMap(Map<Nominal, Integer> banknotes) {
        banknotes.forEach((n,v) -> System.out.println(n + " - "+ v));
    }

}
