package ru.otus.mytest;

/*
 * Uncomment 1 import to switch between JUnit runner and MyTest.runner
 */

//import ru.otus.mytest.Test;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import ru.otus.mytest.Before;
import ru.otus.mytest.After;

import java.util.*;

class ArrayListTest {

    private static ArrayList<String> countryNamesList;
    private static String[] countryCodes;

    @Before
    @BeforeEach
    void setUp() {
        System.out.println("executed: set up");
        countryCodes = Locale.getISOCountries();
        fillCountryNamesList();
    }

    @Before
    @BeforeEach
    void beforeTest() {
        System.out.println("executed: before test");
    }

    @After
    @AfterEach
    void afterTest() {
        System.out.println("executed: after test");
    }

    @After
    @AfterEach
    void cleanUp() {
        System.out.println("executed: clean up");
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
    void checkConstructorFromCollection() {
        ArrayList<String> countryCodesList = new ArrayList<>(Arrays.asList(countryCodes));
        countryCodesList.forEach(code -> {assert (code.equals(""));});
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
    void checkCollectionsCopyMethod() {
        fillCountryNamesList();
        ArrayList<String> codeList = new ArrayList<>(Arrays.asList(countryCodes));
        Collections.copy(codeList, countryNamesList);
        int index = 0;
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
    void checkRemoveMethod() {
        Random random = new Random();
        int initialSize = countryNamesList.size();
        int listSize = initialSize;
        int newSize = random.nextInt(initialSize);
        int nextIndex;
        int iterations = 0;
        ArrayList<String> removedElementsList = new ArrayList<>();
        while (listSize != newSize){
            nextIndex = random.nextInt(listSize);
            removedElementsList.add(countryNamesList.remove(nextIndex));
            listSize = countryNamesList.size();
            iterations++;
        }
        assert (initialSize - newSize == iterations);
        assert(Collections.disjoint(countryNamesList, removedElementsList));
    }
}