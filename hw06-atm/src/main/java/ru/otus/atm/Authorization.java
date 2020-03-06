package ru.otus.atm;

public interface Authorization {

    long authorizeToAtmAsUser(long cardNumber, int pin);

    long authorizeToAtmAsService(long secretKey);

}
