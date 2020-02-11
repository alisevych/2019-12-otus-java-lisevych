package ru.otus.aop.proxy;

import ru.otus.aop.proxy.annotations.Log;

public class TestLogging implements TestLoggingInterface {

    @Override
    @Log
    public void calculation(int param) {
        //System.out.println("executed method: calculation, param: 6");
    }

    @Override
    public void withoutLog(int paramparam) {
    }
}
