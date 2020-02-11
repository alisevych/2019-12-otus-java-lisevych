package ru.otus.aop.proxy;

import ru.otus.aop.proxy.annotations.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;

class IoC {

  static TestLoggingInterface getTestLoggingInstance() {
    InvocationHandler handler = new LoggerMethodsWrapperHandler(new TestLogging());
    return (TestLoggingInterface) Proxy.newProxyInstance(IoC.class.getClassLoader(),
        new Class<?>[]{TestLoggingInterface.class}, handler);
  }

  static class LoggerMethodsWrapperHandler implements InvocationHandler {

        private final TestLoggingInterface testObject;

        LoggerMethodsWrapperHandler(TestLoggingInterface testObject) {
            this.testObject = testObject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getAnnotation(Log.class) != null) {
            String logRecord = "executed method: " + method.getName();
            int i = 0;
            for (Parameter parameter : method.getParameters()) {
                String logParam = String.join(": ", parameter.getName(), args[i].toString());
                logRecord = String.join( ", ", logRecord, logParam);
                i++;
            }
            System.out.println(logRecord);
        }
        return method.invoke(testObject, args);
    }

    @Override
    public String toString() {
      return "LoggerMethodsWrapperHandler{" +
                 "testObject=" + testObject +
                 '}';
    }
  }

}
