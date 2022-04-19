package org.eagleinvsys.test.converters;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eagleinvsys.test.converters.exception.MapsContainDifferentKeySetException;
import org.eagleinvsys.test.converters.impl.CsvConverter;
import org.eagleinvsys.test.converters.impl.StandardCsvConverter;
import org.eagleinvsys.test.converters.interfaces.StandardConverter;

public class Main {

    public static void main(String[] args) throws IOException, MapsContainDifferentKeySetException {
        OutputStream fileOut = Files.newOutputStream(Paths.get("/home/ukhanova/test/test.csv"));
        StandardConverter standardConverter = new StandardCsvConverter(new CsvConverter());

        Map<String, String> map1 = new HashMap<>();
        map1.put("Project", "Test Project 1");
        map1.put("Lead", "Test Lead 1");
        map1.put("Data", "[Some,-Data_1]");

        List<Map<String, String>> collectionToConvert = new ArrayList<>();
        collectionToConvert.add(map1);

        Map<String, String> map2 = new HashMap<>();
        map2.put("Project", "Test Project 2");
        map2.put("Lead", "Test Lead 2");
        map2.put("Data", "[Some,-Data_2]");
        collectionToConvert.add(map2);

        standardConverter.convert(collectionToConvert, fileOut);
    }
}
