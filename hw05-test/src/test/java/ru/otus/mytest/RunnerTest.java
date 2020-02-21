package ru.otus.mytest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import static org.assertj.core.api.Assertions.assertThat;

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
    public static void setUpRunner() {
        runner = new Runner();
    }

    @BeforeAll
    public static void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @Test
    void executeArrayListTestWithRunner() {
        runner.execute(ArrayListTest.class);
        //ToDo assert that outContent is correct: order, number of tests, final count;
        assertThat(outContent.toString()).contains("executed:");
    }

    @AfterAll
    public static void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
        System.out.print(outContent.toString());
        System.out.print(errContent.toString());
    }

}