package ru.otus.aop.proxy;

import ru.otus.aop.proxy.annotations.Log;

public class TestLogging implements TestLoggingInterface {

    @Override
    @Log
    public void calculation(int param) {
    }

    @Override
    public void withoutLog(int paramparam) {
    }

    @Override
    @Log
    public void calculation(int param, String paramaram) {
    }

    @Override
    @Log
    public void calculation(String param) {
    }

    @Override
    @Log
    public void calculation1(int param) {
    }

    @Override
    @Log
    public void calculation() {
    }

}
