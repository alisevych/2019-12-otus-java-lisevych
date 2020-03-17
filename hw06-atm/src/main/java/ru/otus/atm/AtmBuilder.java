package ru.otus.atm;

import java.util.*;

public class AtmBuilder {

    private static final AuthorizationModule authorization =  new AuthorizationModule();
    private static final Random random = new Random();
    private static Set<String> generatedIDs = new HashSet<>();

    public static AtmImpl generateAtm(State initialState, String atmID) {
        if (atmID == null) {
            atmID = generateID();
        }
        generatedIDs.add(atmID);
        return new AtmImpl(initialState, authorization, atmID);
    }

    public static List<String> getGeneratedIDs(){
        return new ArrayList<>(generatedIDs);
    }

    public static String generateID() {
        boolean idGenerated = false;
        String atmID = "";
        while (!idGenerated) {
            atmID = "atm" + random.nextInt(111111111);
            if (!generatedIDs.contains(atmID)) {
                idGenerated = true;
            }
        }
        return atmID;
    }
}
