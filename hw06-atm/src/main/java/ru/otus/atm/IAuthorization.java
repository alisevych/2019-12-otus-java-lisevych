package ru.otus.atm;

public interface IAuthorization {

    long authorizeToAtmAsUser(long cardNumber, int pin);

    long authorizeToAtmAsService(long secretKey);

}
