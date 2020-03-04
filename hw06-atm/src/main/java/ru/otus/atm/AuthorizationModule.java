package ru.otus.atm;

import java.util.Random;

import static java.lang.Math.abs;

class AuthorizationModule implements IAuthorization{

    private Random random = new Random();

    @Override
    public long authorizeToAtmAsUser(long cardNumber, int pin) {
        return abs(random.nextLong());
    }

    @Override
    public long authorizeToAtmAsService(long secretKey) {
        return abs(random.nextLong());
    }

}
