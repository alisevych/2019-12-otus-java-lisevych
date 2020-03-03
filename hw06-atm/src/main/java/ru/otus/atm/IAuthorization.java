package ru.otus.atm;

public interface IAuthorization {

    long authorizeAsUser();
    long authorizeAsService();

}
