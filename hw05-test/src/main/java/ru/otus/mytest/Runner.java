package ru.otus.mytest;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Runner {

    public Runner() {
    }

    public void execute(Class testClass)  {
        List<Method> allMethods = List.of(testClass.getDeclaredMethods());
        List<Method> beforeMethods = getMethodsWithAnnotation( allMethods, Before.class);
        List<Method> afterMethods = getMethodsWithAnnotation( allMethods, After.class);
        List<Method> testMethods = getMethodsWithAnnotation( allMethods, Test.class);
        int iRun=0, iFailed=0, iBlocked=0, iPassed=0;
        for (Method test : testMethods) {
            List<Method> methodsToRun = new ArrayList<>();
            methodsToRun.addAll(beforeMethods);
            methodsToRun.add(test);
            methodsToRun.addAll(afterMethods);
            System.out.println("Test: " + methodToString(test));
            ResultCode testResult = executeTest(testClass, methodsToRun);
            iRun++;
            if (testResult == ResultCode.PASSED) {
                iPassed++;
            } else if (testResult == ResultCode.FAILED) {
                iFailed++;
            } else if (testResult == ResultCode.BLOCKED) {
                iBlocked++;
            } else {
                throw new IllegalStateException("Test result is not recognized: " + testResult);
            }
        }
        outputStatistics (iRun, iPassed, iBlocked, iFailed);
    }

    private ResultCode executeTest( Class testClass, List<Method> methodsSequence) {
        try {
            Object instance = testClass.getDeclaredConstructors()[0].newInstance();
            for (Method method : methodsSequence) {
                String methodAbbrv = methodToString(method);
                System.out.println("run: " + methodAbbrv);
                method.invoke(instance);
            }
        } catch (AssertionError exception) {
            System.out.println("Failed: " + exception.getCause() + "\n");
            return ResultCode.FAILED;
        } catch (InvocationTargetException exception) {
            if (exception.getCause().toString().contains("Assertion")) {
                System.out.println("Failed: " + exception.getCause() + "\n");
                return ResultCode.FAILED;
            } else {
                System.out.println("Blocked: " + exception.getCause() + "\n");
                return ResultCode.BLOCKED;
            }
        } catch (Exception exception) {
            System.out.println("Blocked: " + exception.getCause() + "\n");
            return ResultCode.BLOCKED;
        }
        System.out.println("Passed.\n");
        return ResultCode.PASSED;
    }

    private  List<Method> getMethodsWithAnnotation( List<Method> allMethods,
                                                    Class<? extends Annotation> annotationClass) {
        List<Method> result = new ArrayList<>();
        for (Method method: allMethods) {
            if (method.isAnnotationPresent( annotationClass )) {
                result.add( method );
            }
        }
        return result;
    }

    private void outputStatistics (int iRun, int iPassed, int iBlocked, int iFailed){
        System.out.println("=================================================");
        System.out.println("         Total tests run: " + iRun);
        System.out.println("         Passed:          " + iPassed);
        System.out.println("         Blocked:         " + iBlocked);
        System.out.println("         Failed:          " + iFailed);
        System.out.println("=================================================\n");
    }

    private String methodToString(Method method) {
        String result = method.getName();
        for (Class<?> type : method.getParameterTypes()){
            result = String.join(",", result, type.getName());
        }
        return result;
    }

    public enum ResultCode {

        FAILED(-1),
        BLOCKED(0),
        PASSED(1);

        int code;

        ResultCode(int code) {
            this.code = code;
        }

    }

}
