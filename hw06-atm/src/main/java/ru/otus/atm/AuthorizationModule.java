package ru.otus.atm;

import java.util.Random;

import static java.lang.Math.abs;

class AuthorizationModule implements IAuthorization{

    private Random random = new Random();

    @Override
    public long authorizeToAtmAsUser() {
        return abs(random.nextLong());
    }

    @Override
    public long authorizeToAtmAsService() {
        return abs(random.nextLong());
    }

}
