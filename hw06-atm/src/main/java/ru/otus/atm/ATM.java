package ru.otus.atm;

import javax.security.sasl.AuthenticationException;
import java.util.Random;

public class ATM implements AtmUserInterface//, AtmServiceInterface
{

    private Cells cells;
    private long userSessionKey=-1;
    private long serviceSessionKey=-1;
    private Random random = new Random();

    protected ATM (Cells cells) {
        this.cells = cells;
    }

    public long userAuthorize() throws AuthenticationException {
        if (serviceSessionKey != -1) {
            throw new AuthenticationException("ATM is on service");
        }
        userSessionKey = random.nextLong();
        return userSessionKey;
    }

    public long serviceAuthorize() {
        if (userSessionKey != -1) {
            userLogout();
        }
        serviceSessionKey = random.nextLong();
        return serviceSessionKey;
    }

    private boolean checkKey(long key) throws AuthenticationException {
        if (userSessionKey == -1) {
            throw new AuthenticationException("User is not authorized");
        }
        if (userSessionKey != key) {
            userSessionKey = -1; // drop active user session
            throw new AuthenticationException("Key is invalid");
        }
    }

    public void getAmount(long key, int sum) throws AuthenticationException{
        checkKey(key);

    }

}
