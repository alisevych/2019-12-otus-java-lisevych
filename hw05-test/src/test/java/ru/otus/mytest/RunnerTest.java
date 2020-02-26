package ru.otus.mytest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;

import ru.otus.mytest.Runner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class RunnerTest {

    private static Runner runner;

    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private static final PrintStream originalOut = System.out;
    private static final PrintStream originalErr = System.err;

    @BeforeAll
    private static void setUpRunner() {
        runner = new Runner();
    }

    @BeforeAll
    private static void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @Test
    void executeArrayListTestWithRunner() {
        runner.execute(ArrayListTest.class);
        assert (outContent.toString()).contains("Total tests run: 7");
        assert (outContent.toString()).contains("Passed:          3");
        assert (outContent.toString()).contains("Blocked:         1");
        assert (outContent.toString()).contains("Failed:          3");
    }

    @Test
    void executeArrayListTestWithRunnerAgain() {
        executeArrayListTestWithRunner(); // to check counters
    }

    @AfterAll
    private static void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
        System.out.print(outContent.toString());
    }

}