package ru.otus.aop.proxy;

public class TestLogging {

    @Log
    public void calculation(int param) {
        //System.out.println("executed method: calculation, param: 6");
    }

}
