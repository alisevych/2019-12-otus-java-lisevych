package ru.otus.mytest;

/*
 * Uncomment 1 import to switch between JUnit runner and MyTest.runner
 */

import ru.otus.mytest.Test;
import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import ru.otus.mytest.Before;
import ru.otus.mytest.After;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ArrayListTest {

    private static ArrayList<String> countryNamesList;
    private static String[] countryCodes;

    @Before
    @BeforeEach
    void setUp() {
        countryCodes = Locale.getISOCountries();
        fillCountryNamesList();
    }

    @Before
    @BeforeEach
    void beforeTest() {
    }

    @After
    @AfterEach
    void afterTest() {
    }

    @After
    @AfterEach
    void cleanUp() {
    }

    @After
    void after2() {
    }

    static void fillCountryNamesList(){
        int sizeNeeded = countryCodes.length;
        countryNamesList = new ArrayList<>(sizeNeeded);
        Locale myLocale = new Locale("ru", "RU");
        Locale locale;
        for (String countryCode : countryCodes){
            locale = new Locale("ru", countryCode);
            countryNamesList.add(locale.getDisplayCountry(myLocale));
        }
    }

    @Test
    void checkSizeAfterAddElement() {
        int initialSize = countryNamesList.size();
        countryNamesList.add("Швамбрания");
        assert (initialSize + 1 == countryNamesList.size());
    }

    @Test
    @DisplayName("SPOILT - check constructor from collection")
    void checkConstructorFromCollection() {
        ArrayList<String> countryCodesList = new ArrayList<>(Arrays.asList(countryCodes));
        countryCodesList.forEach(code -> {assert (code.equals("!"));});
    }

    @Test
    void checkCollectionsAddAllMethod() {
        int initialSize = countryNamesList.size();
        String added1 = "Страна Лиллипутов", added2 = "Страна Великанов", added3 = "Страна Снежной королевы";
        int numberOfElementsAdded = 3;
        Collections.addAll(countryNamesList, added1, added2, added3);
        assert (countryNamesList.contains(added1));
        assert (countryNamesList.contains(added2));
        assert (countryNamesList.contains(added3));
        assert (initialSize + numberOfElementsAdded == countryNamesList.size());
    }

    @Test
    @DisplayName("SPOILT - check other exception - test must be BLOCKED")
    void checkCollectionsCopyMethod() {
        fillCountryNamesList();
        ArrayList<String> codeList = new ArrayList<>(Arrays.asList(countryCodes));
        Collections.copy(codeList, countryNamesList);
        int index = 0;
        countryNamesList.get(1000);
        for (Object newElement : codeList){
            assert (newElement == countryNamesList.get(index++));
        }
    }

    @Test
    void checkCollectionsSortMethod() {
        Collections.sort(countryNamesList, Collections.reverseOrder());
        String previousName = "яяяяяяяя";
        for (String countryName : countryNamesList){
            assert(countryName.compareTo(previousName) <= 0);
            previousName = countryName;
        }
    }

    @Test
    @DisplayName("SPOILT - check Remove method")
    void checkRemoveMethod() {
        Random random = new Random();
        int initialSize = countryNamesList.size();
        int listSize = initialSize;
        int newSize = random.nextInt(initialSize);
        int nextIndex;
        int iterations = 0;
        ArrayList<String> removedElementsList = new ArrayList<>();
        while (listSize != newSize){
            iterations++;
            nextIndex = random.nextInt(listSize);
            removedElementsList.add(countryNamesList.remove(nextIndex));
            listSize = countryNamesList.size();
        }
        // Added last removed element back to countryNamesList
        countryNamesList.add(removedElementsList.get(iterations - 1));
        assert  (initialSize - newSize == iterations);
        assert (Collections.disjoint(countryNamesList, removedElementsList));
        System.out.println("Where are the errors?");
    }

    @Test
    void checkAssertionError() {
        assertTrue(false);
    }
}