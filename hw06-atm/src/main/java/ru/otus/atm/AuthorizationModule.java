package ru.otus.atm;

import java.util.Random;

import static java.lang.Math.abs;

class AuthorizationModule {

    Random random = new Random();

    long authorizeAsUser() {
        return abs(random.nextLong());
    }

    long authorizeAsService() {
        return abs(random.nextLong());
    }

}
