package ru.otus.generics;

import org.junit.jupiter.api.*;

import java.util.*;

@DisplayName("DIYArrayList must")
class DIYArrayListTest {

    private static ArrayList<String> countryNamesList;
    private static String[] countryCodes;

    @BeforeEach
    private void setUp() {
        countryCodes = Locale.getISOCountries();
        countryNamesList = new ArrayList<>();
        Locale myLocale = new Locale("ru", "RU");
        Locale locale;
        for (String countryCode : countryCodes){
            locale = new Locale("ru", countryCode);
            countryNamesList.add(locale.getDisplayCountry(myLocale));
        }
    }

    @AfterAll
    private static void tearDown() {
        countryCodes = null;
        countryNamesList.clear();
        countryNamesList = null;
    }

    @DisplayName("print values with forEach")
    @Test
    void printArrayList() {
        countryNamesList.forEach(System.out::println);
    }

    @DisplayName("return correct size after add an element")
    @Test
    void checkSizeAfterAddElement() {
        int initialSize = countryNamesList.size();
        countryNamesList.add("Shvambrania");
        Assertions.assertEquals(initialSize + 1, countryNamesList.size());
    }

    @DisplayName("construct itself from another Collection")
    @Test
    void checkConstructorfromCollection() {
        DIYArrayList<String> countryCodesList = new DIYArrayList<>(Arrays.asList(countryCodes));
        countryCodesList.forEach(System.out::println);
    }

    @DisplayName("correctly addAll elements with Collections.addAll(Collection<? super T> c, T... elements)")
    @Test
    void checkCollectionsAddAllMethod() {
        int initialSize = countryNamesList.size();
        String added1 = "Added1", added2 = "Added2", added3 = "Added3";
        int numberOfElementsAdded = 3;
        Collections.addAll(countryNamesList, added1, added2, added3);
        Assertions.assertTrue(countryNamesList.contains(added1));
        Assertions.assertTrue(countryNamesList.contains(added2));
        Assertions.assertTrue(countryNamesList.contains(added3));
        Assertions.assertEquals(initialSize + numberOfElementsAdded, countryNamesList.size());
    }

    @DisplayName("correctly copy list with Collections.copy(List<? super T> dest, List<? extends T> src)")
    @Test
    void checkCollectionsCopyMethod() {
        ArrayList<String> newList = new ArrayList<>(Arrays.asList(countryCodes));
        Collections.copy(newList, countryNamesList);
        Assertions.assertEquals(countryNamesList, newList);
        int index = 0;
        for (Object newElement : newList){
            Assertions.assertEquals(newElement, countryNamesList.get(index++));
        }
    }

    @DisplayName("correctly sort list with Collections.sort(List<T> list, Comparator<? super T> c)")
    @Test
    void checkCollectionsSortMethod() {
        Collections.sort(countryNamesList, Collections.reverseOrder());
        String previousName = "яяяяяяяя";
        for (String countryName : countryNamesList){
            //System.out.println(countryName);
            //System.out.println(countryName.compareTo(previousName));
            Assertions.assertTrue(countryName.compareTo(previousName) <= 0);
            previousName = countryName;
        }
    }
}