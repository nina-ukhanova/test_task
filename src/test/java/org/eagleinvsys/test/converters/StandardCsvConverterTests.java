package org.eagleinvsys.test.converters;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.eagleinvsys.test.converters.impl.CsvConverter;
import org.eagleinvsys.test.converters.impl.StandardCsvConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StandardCsvConverterTests {

    private static final String FILE_NAME = "/home/ukhanova/test/test1.csv";
    private static final String FILE_TO_COMPARE_NAME = "/home/ukhanova/test/test2.csv";

    OutputStream fileOutPutStream;
    StandardConverter standardConverter;

    @BeforeEach
    public void setUp() {

        try {
            fileOutPutStream = new FileOutputStream(FILE_NAME);
        } catch (FileNotFoundException exception) {
            System.out.println(String.format("File %s is not found", FILE_NAME));
        }
        standardConverter = new StandardCsvConverter(new CsvConverter());
    }

    @Test
    public void checkCsvConversionSuccessful() {

        Map<String, String> testMap1 = new HashMap<>();
        testMap1.put("Project", "Test Project 1");
        testMap1.put("Lead", " Test Lead 1");

        List<Map<String, String>> collectionToConvert = new ArrayList<>();
        collectionToConvert.add(testMap1);

        Map<String, String> testMap2 = new HashMap<>();
        testMap2.put("Project", "Test Project 2");
        testMap2.put("Lead", "Test Lead 2");
        collectionToConvert.add(testMap2);

        standardConverter.convert(collectionToConvert, fileOutPutStream);

        try {
            assertTrue(FileUtils.contentEquals(new File(FILE_NAME), new File(FILE_TO_COMPARE_NAME)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
