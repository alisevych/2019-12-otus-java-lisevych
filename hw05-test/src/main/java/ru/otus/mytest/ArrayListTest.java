package ru.otus.mytest;

import ru.otus.mytest.runner.*;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.*;

class ArrayListTest {

    private static ArrayList<String> countryNamesList;
    private static String[] countryCodes;

    @BeforeAll
    private static void setUp() {
        countryCodes = Locale.getISOCountries();
        fillCountryNamesList();
    }

    @BeforeEach
    private void beforeTest1() {
        System.out.println("Before another test 1 ===============");
    }

    @BeforeEach
    private void beforeTest2() {
        System.out.println("=============== 2 again: Before each test");
    }

    @AfterEach
    private void afterTest1() {
        System.out.println("After another test 1 ===============");
    }

    @AfterEach
    private void afterTest2() {
        System.out.println("=============== 2 again: After each test");
    }

    @AfterAll
    private static void cleanUp() {
        countryCodes = null;
        countryNamesList = null;
    }

    private static void fillCountryNamesList(){
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
    void iterateAndPrintElements() {
        countryNamesList.forEach(System.out::println);
    }

    @Test
    void checkSizeAfterAddElement() {
        int initialSize = countryNamesList.size();
        countryNamesList.add("Швамбрания");
        assertEquals(initialSize + 1, countryNamesList.size());
    }

    @Test
    void checkConstructorFromCollection() {
        ArrayList<String> countryCodesList = new ArrayList<>(Arrays.asList(countryCodes));
        countryCodesList.forEach(code -> assertNotEquals(code,""));
    }

    @Test
    void checkCollectionsAddAllMethod() {
        int initialSize = countryNamesList.size();
        String added1 = "Страна Лиллипутов", added2 = "Страна Великанов", added3 = "Страна Снежной королевы";
        int numberOfElementsAdded = 3;
        Collections.addAll(countryNamesList, added1, added2, added3);
        assertTrue(countryNamesList.contains(added1));
        assertTrue(countryNamesList.contains(added2));
        assertTrue(countryNamesList.contains(added3));
        assertEquals(initialSize + numberOfElementsAdded, countryNamesList.size());
    }

    @Test
    void checkCollectionsCopyMethod() {
        fillCountryNamesList();
        ArrayList<String> codeList = new ArrayList<>(Arrays.asList(countryCodes));
        Collections.copy(codeList, countryNamesList);
        int index = 0;
        for (Object newElement : codeList){
            assertEquals(newElement, countryNamesList.get(index++));
        }
    }

    @Test
    void checkCollectionsSortMethod() {
        Collections.sort(countryNamesList, Collections.reverseOrder());
        String previousName = "яяяяяяяя";
        for (String countryName : countryNamesList){
            assertTrue(countryName.compareTo(previousName) <= 0);
            previousName = countryName;
        }
    }

    @Test
    void checkRemoveMethod() {
        Random random = new Random();
        int initialSize = countryNamesList.size();
        int listSize = initialSize;
        int newSize = random.nextInt(initialSize);
        System.out.println("Initial size: " + initialSize + ". New size : " + newSize);
        int nextIndex;
        int iterations = 0;
        ArrayList<String> removedElementsList = new ArrayList<>();
        while (listSize != newSize){
            nextIndex = random.nextInt(listSize);
            removedElementsList.add(countryNamesList.remove(nextIndex));
            listSize = countryNamesList.size();
            iterations++;
        }
        assertEquals(initialSize - newSize, iterations);
        assertTrue(Collections.disjoint(countryNamesList, removedElementsList));
    }
}