package ru.otus.atm;

import java.util.ArrayList;
import java.util.List;

public class AtmFactory {

    private static final AuthorizationModule authorization =  new AuthorizationModule();
    private static int counter = 0;
    private static List<String> generatedIDs = new ArrayList<>();

    public static AtmImpl generateAtm(State initialState, String atmID) {
        if (atmID == null) {
            counter++;
            atmID = "atm" + counter;
        }
        generatedIDs.add(atmID);
        return new AtmImpl(initialState, authorization, atmID);
    }

    public static List<String> getGeneratedIDs(){
        return new ArrayList<>(generatedIDs);
    }
}
