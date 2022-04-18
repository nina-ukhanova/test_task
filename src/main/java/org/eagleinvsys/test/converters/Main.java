package org.eagleinvsys.test.converters;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eagleinvsys.test.converters.impl.CsvConverter;
import org.eagleinvsys.test.converters.impl.StandardCsvConverter;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        OutputStream fileOut = new FileOutputStream("/home/ukhanova/test/test.csv");
        StandardConverter standardConverter = new StandardCsvConverter(new CsvConverter());

        Map<String, String> map1 = new HashMap<>();
        map1.put("Project", "Test Project 1");
        map1.put("Lead", " Test Lead 1");

        List<Map<String, String>> collectionToConvert = new ArrayList<>();
        collectionToConvert.add(map1);

        Map<String, String> map2 = new HashMap<>();
        map2.put("Project", "Test Project 2");
        map2.put("Lead", "Test Lead 2");
        collectionToConvert.add(map2);

        standardConverter.convert(collectionToConvert, fileOut);
    }
}
