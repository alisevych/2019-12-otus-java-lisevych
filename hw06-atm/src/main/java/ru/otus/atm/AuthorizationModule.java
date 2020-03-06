package ru.otus.atm;

import java.util.Random;

import static java.lang.Math.abs;

class AuthorizationModule implements Authorization {

    private Random random = new Random();
    private static final long specialServiceKey = 1234567890;

    @Override
    public long authorizeToAtmAsUser(long cardNumber, int pin) {
        return abs(random.nextLong());
    }

    @Override
    public long authorizeToAtmAsService(long secretKey) {
        if (secretKey == specialServiceKey) {
            return abs(random.nextLong());
        }
        return -1;
    }

}
