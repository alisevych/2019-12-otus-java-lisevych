package ru.otus.aop.proxy;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

class DemoTest {

    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private static final PrintStream originalOut = System.out;
    private static final PrintStream originalErr = System.err;
    private static final TestLoggingInterface testLogging = IoC.getTestLoggingInstance();


    @BeforeAll
    public static void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterAll
    public static void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
        System.out.print(outContent.toString());
        System.out.print(errContent.toString());
    }

    @Test
    @DisplayName("annotated with @Log method call with param is logged in console")
    void checkLoggingForAnnotatedMethod() {
        testLogging.calculation(6);
        assertThat(outContent.toString()).contains("executed method: calculation, param: 6\n");
    }

    @Test
    @DisplayName("annotated with @Log method call with 2 parameters is logged in console")
    void checkLoggingForAnnotatedWithTwoParametersMethod() {
        testLogging.calculation(7, "complex");
        assertThat(outContent.toString()).contains("executed method: calculation, param: 7, paramaram: complex\n");
    }

    @Test
    @DisplayName("annotated with @Log method call without parameters is logged in console")
    void checkLoggingForAnnotatedWithoutParametersMethod() {
        testLogging.calculation();
        assertThat(outContent.toString()).contains("executed method: calculation\n");
    }

    @Test
    @DisplayName("Not annotated with @Log method call is Not logged in console")
    void checkLoggingIsAbsentForNotAnnotatedMethod() {
        testLogging.withoutLog(15);
        assertThat(outContent.toString()).doesNotContain("executed method: withoutLog, paramparam: 15");
    }

}