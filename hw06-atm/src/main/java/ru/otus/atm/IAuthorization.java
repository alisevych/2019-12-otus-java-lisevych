package ru.otus.atm;

public interface IAuthorization {

    long authorizeToAtmAsUser();

    long authorizeToAtmAsService();

}
