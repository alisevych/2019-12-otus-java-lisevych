package ru.otus.atm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AtmFactory {

    private static final AuthorizationModule authorization =  new AuthorizationModule();
    private static int counter = 0;
    private static List<String> generatedIDs = new ArrayList<>();

    public static AtmImpl getAtm(Map<Nominal, Integer> initialState, String atmID) {
        CellsImpl cells = new CellsImpl(initialState);
        if (atmID == null) {
            counter++;
            atmID = "atm" + counter;
        }
        generatedIDs.add(atmID);
        return new AtmImpl(cells, authorization, atmID);
    }

}
