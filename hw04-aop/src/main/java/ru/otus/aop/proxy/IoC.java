package ru.otus.aop.proxy;

import ru.otus.aop.proxy.annotations.Log;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

class IoC {

    static TestLoggingInterface getTestLoggingInstance() {
        InvocationHandler handler = new LoggerMethodsWrapperHandler(new TestLogging());
        return (TestLoggingInterface) Proxy.newProxyInstance(IoC.class.getClassLoader(),
            new Class<?>[]{TestLoggingInterface.class}, handler);
    }

    static class LoggerMethodsWrapperHandler implements InvocationHandler {

        private final TestLogging testObject;
        private final List<String> logMethodsAbbrv = new ArrayList<>();

        LoggerMethodsWrapperHandler(TestLogging testObject) {
            this.testObject = testObject;
            findMethodsWithLogAnnotation();
        }

        private void findMethodsWithLogAnnotation() {
            Method[] allMethods = testObject.getClass().getDeclaredMethods();
            for (Method method : allMethods) {
                if (method.getAnnotation(Log.class) != null) {
                    logMethodsAbbrv.add(methodToString(method));
                }
            }
        }

        private String methodToString(Method method) {
            String result = method.getName();
            for (Class<?> type : method.getParameterTypes()){
                result = String.join(",", result, type.getName());
            }
            return result;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (logMethodsAbbrv.contains(methodToString(method))) {
                logMethodInvokation(method, args);
            }
            return method.invoke(testObject, args);
        }

        private void logMethodInvokation(Method method, Object[] args) {
            String logRecord = "executed method: " + method.getName();
            int i = 0;
            for (Parameter parameter : method.getParameters()) {
                String logParam = String.join(": ", parameter.getName(), args[i].toString());
                logRecord = String.join(", ", logRecord, logParam);
                i++;
            }
            System.out.println(logRecord);
        }

        @Override
        public String toString() {
            return "LoggerMethodsWrapperHandler{" +
                    "testObject=" + testObject +
                    '}';
        }

    }

}
