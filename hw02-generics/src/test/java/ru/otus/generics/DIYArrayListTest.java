package ru.otus.generics;

import org.junit.jupiter.api.*;

import java.util.*;

@DisplayName("DIYArrayList must")
class DIYArrayListTest {

    private static DIYArrayList<String> countryNamesList;
    private static String[] countryCodes;

    @BeforeAll
    private static void setUp() {
        countryCodes = Locale.getISOCountries();
        fillCountryNamesList();
    }

    private static void fillCountryNamesList(){
        int sizeNeeded = countryCodes.length;
        countryNamesList = new DIYArrayList<>(sizeNeeded);
        Locale myLocale = new Locale("ru", "RU");
        Locale locale;
        for (String countryCode : countryCodes){
            locale = new Locale("ru", countryCode);
            countryNamesList.add(locale.getDisplayCountry(myLocale));
        }
    }

    @DisplayName("iterate through list and print elements")
    @Test
    void iterateAndPrintElements() {
        countryNamesList.forEach(System.out::println);
    }

    @DisplayName("add an element and return correct size")
    @Test
    void checkSizeAfterAddElement() {
        int initialSize = countryNamesList.size();
        countryNamesList.add("Швамбрания");
        Assertions.assertEquals(initialSize + 1, countryNamesList.size());
    }

    @DisplayName("construct itself from another Collection")
    @Test
    void checkConstructorFromCollection() {
        DIYArrayList<String> countryCodesList = new DIYArrayList<>(Arrays.asList(countryCodes));
        countryCodesList.forEach(code -> Assertions.assertNotEquals(code,""));
    }

    @DisplayName("addAll elements with Collections.addAll(Collection<? super T> c, T... elements)")
    @Test
    void checkCollectionsAddAllMethod() {
        int initialSize = countryNamesList.size();
        String added1 = "Страна Лиллипутов", added2 = "Страна Великанов", added3 = "Страна Снежной королевы";
        int numberOfElementsAdded = 3;
        Collections.addAll(countryNamesList, added1, added2, added3);
        Assertions.assertTrue(countryNamesList.contains(added1));
        Assertions.assertTrue(countryNamesList.contains(added2));
        Assertions.assertTrue(countryNamesList.contains(added3));
        Assertions.assertEquals(initialSize + numberOfElementsAdded, countryNamesList.size());
    }

    @DisplayName("copy list with Collections.copy(List<? super T> dest, List<? extends T> src)")
    @Test
    void checkCollectionsCopyMethod() {
        fillCountryNamesList();
        DIYArrayList<String> codeList = new DIYArrayList<>(Arrays.asList(countryCodes));
        Collections.copy(codeList, countryNamesList);
        int index = 0;
        for (Object newElement : codeList){
            Assertions.assertEquals(newElement, countryNamesList.get(index++));
        }
    }

    @DisplayName("sort list with Collections.sort(List<T> list, Comparator<? super T> c)")
    @Test
    void checkCollectionsSortMethod() {
        Collections.sort(countryNamesList, Collections.reverseOrder());
        String previousName = "яяяяяяяя";
        for (String countryName : countryNamesList){
            Assertions.assertTrue(countryName.compareTo(previousName) <= 0);
            previousName = countryName;
        }
    }

    @DisplayName("remove elements")
    @Test
    void checkRemoveMethod() {
        Random random = new Random();
        int initialSize = countryNamesList.size();
        int listSize = initialSize;
        int newSize = random.nextInt(initialSize);
        System.out.println("Initial size: " + initialSize + ". New size : " + newSize);
        int nextIndex;
        int iterations = 0;
        DIYArrayList<String> removedElementsList = new DIYArrayList<>();
        while (listSize != newSize){
            nextIndex = random.nextInt(listSize);
            removedElementsList.add(countryNamesList.remove(nextIndex));
            listSize = countryNamesList.size();
            iterations++;
        }
        Assertions.assertEquals(initialSize - newSize, iterations);
        Assertions.assertTrue(Collections.disjoint(countryNamesList, removedElementsList));
    }
}