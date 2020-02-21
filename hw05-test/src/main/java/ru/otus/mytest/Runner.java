package ru.otus.mytest;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Runner {

    public enum ResultCode {

        FAILED(-1),
        SKIPPED(0),
        PASSED(1);

        int code;

        ResultCode(int code) {
            this.code = code;
        }

    }

    public Runner() {
    }

    public static void execute(Class testClass) {
        testClass.getMethods();
        List<Method> allMethods = List.of(testClass.getDeclaredMethods());
        List<int> beforeIndexes = getIndexesOfMethodsWithAnnotation(
                allMethods, Before.class);
        List<int> afterIndexes = getIndexesOfMethodsWithAnnotation(
                allMethods, After.class);
        List<int> testIndexes = getIndexesOfMethodsWithAnnotation(
                allMethods, Test.class);
        for (int testID : testIndexes) {
            //executeTest(testClass, );
        }
    }

    public static ResultCode executeTest(
            Object testObject, List<int> indexesConsequence) {
        try {
            //ToDo create object of testClass
            for (int index : indexesConsequence) {
                //ToDo invoke method
            }
        } catch (AssertionError assertionError) {
            return ResultCode.FAILED;
        } catch (Exception e) {
            return  ResultCode.SKIPPED;
        }
        return ResultCode.PASSED;
    }

    public static List<int> getIndexesOfMethodsWithAnnotation
            (List<Method> allMethods, Class<? extends Annotation> annotationClass) {
        List<int> indexes = new ArrayList<int>();
        int i = 0;
        for (Method method: allMethods) {
            if (method.isAnnotationPresent( annotationClass )) {
                indexes.add( i );
            }
            i++;
        }
        return indexes;
    }

}
