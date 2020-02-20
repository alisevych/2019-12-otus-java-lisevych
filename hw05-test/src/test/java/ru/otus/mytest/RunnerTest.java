package ru.otus.mytest;

import org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.*;

import ru.otus.mytest.Runner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class RunnerTest {

    private Runner runner;

    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private static final PrintStream originalOut = System.out;
    private static final PrintStream originalErr = System.err;

    @BeforeAll
    public void setUpRunner() {
        runner = new Runner();
    }

    @BeforeAll
    public void setUpStreams() {
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
    void executeArrayListTestWithRunner() {
        runner.execute(ArrayListTest.class);
    }
}