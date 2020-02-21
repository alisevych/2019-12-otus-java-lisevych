package ru.otus.aop.proxy;

public interface TestLoggingInterface {

    void calculation(int param);

    void withoutLog(int paramparam);

    void calculation(int param, String paramaram);

    void calculation(String param);

    void calculation1(int param);

    void calculation();
}
