package ru.otus.mytest;

import java.lang.annotation.Annotation;
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
        int iRun=0, iFailed=0, iSkipped=0, iPassed=0;
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
            } else if (testResult == ResultCode.SKIPPED) {
                iSkipped++;
            } else {
                throw new IllegalStateException("Test result is not recognized: " + testResult);
            }
        }
        outputStatistics (iRun, iPassed, iSkipped, iFailed);
    }

    private ResultCode executeTest( Class testClass, List<Method> methodsSequence) {
        try {
            Object instance = testClass.getDeclaredConstructors()[0].newInstance();
            for (Method method : methodsSequence) {
                String methodAbbrv = methodToString(method);
                System.out.println("run: " + methodAbbrv);
                method.invoke(instance);
            }
        } catch (AssertionError assertionError) {
            System.out.println("Failed: ");
            assertionError.printStackTrace();
            return ResultCode.FAILED;
        } catch (Exception exception) {
            System.out.println("Skipped: " + exception);
            exception.printStackTrace();
            return ResultCode.SKIPPED;
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

    private void outputStatistics (int iRun, int iPassed, int iSkipped, int iFailed){
        System.out.println("=================================================");
        System.out.println("         Total tests run: " + iRun);
        System.out.println("         Passed:          " + iPassed);
        System.out.println("         Skipped:         " + iSkipped);
        System.out.println("         Failed:          " + iFailed);
        System.out.println("=================================================");
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
        SKIPPED(0),
        PASSED(1);

        int code;

        ResultCode(int code) {
            this.code = code;
        }

    }

}
