package ru.otus.generics;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DIYArrayList must")
class DIYArrayListTest {

    @BeforeEach
    void setUp() {
        DIYArrayList<String> testedList = new DIYArrayList<>();
        //ToDo create DIYArrayList object and fill it with 20+ elements
    }

    @AfterEach
    void tearDown() {
        //ToDo tear up created objects
    }

    @DisplayName("behave correctly for method Collections.addAll(Collection<? super T> c, T... elements)")
    @Test
    void addAll() {
        //ToDo rename test correctly
    }
}