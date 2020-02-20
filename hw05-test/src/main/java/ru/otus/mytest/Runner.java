package ru.otus.mytest;

import java.util.ArrayList;
import java.util.List;

public class Runner {

    private List<Class<?>> testClasses = new ArrayList<>();

    public Runner() {
    }

    public Runner(Class<?>... vararg) {
        testClasses = List.of(vararg);
    }

    public void executeAll(){
        testClasses.forEach(this::execute);
    }

    public void execute(Class testClass) {
        //ToDo Implement consequence of steps
    }

}
