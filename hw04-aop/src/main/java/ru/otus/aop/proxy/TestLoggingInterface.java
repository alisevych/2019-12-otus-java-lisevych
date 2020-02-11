package ru.otus.aop.proxy;

import ru.otus.aop.proxy.annotations.Log;

public interface TestLoggingInterface {

    @Log
    void calculation(int param);

    void withoutLog(int paramparam);
}
