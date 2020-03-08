package ru.otus.atm;

import java.util.Map;

public class ServiceSession {

    private final AtmService atm;
    private long sessionKey = -1;

    protected ServiceSession(AtmService atm, long serviceKey) {
        this.atm = atm;
        this.sessionKey = atm.serviceLogin(serviceKey);
        if (this.sessionKey == -1) {
            throw new RuntimeException("[ERROR] Try to connect as Service with illegal key.");
        }
        System.out.println("Service session is opened.");
    }

    protected Map<Nominal, Integer> getState() {
        checkLoggedIn();
        return atm.getState(sessionKey);
    }

    protected boolean setState(Map<Nominal, Integer> cellState){
        checkLoggedIn();
        return atm.setState(sessionKey, cellState);
    }

    protected void closeSession() {
        checkLoggedIn();
        atm.serviceLogout(sessionKey);
        sessionKey = -1;
        System.out.println("Service session is closed.");
    }

    private void checkLoggedIn(){
        if (sessionKey == -1 ) {
            throw new RuntimeException("[ERROR] You are not yet logged in as Service.");
        }
    }

}
