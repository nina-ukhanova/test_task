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
import org.eagleinvsys.test.converters.exception.MapsContainDifferentKeySetException;
import org.eagleinvsys.test.converters.impl.CsvConverter;
import org.eagleinvsys.test.converters.impl.StandardCsvConverter;
import org.eagleinvsys.test.converters.interfaces.StandardConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StandardCsvConverterTests {

    private static final String FILE_NAME = "src/test/resources/test.csv";
    private static final String FILE_TO_COMPARE_FOR_FIRST_TEST = "src/test/resources/fileToCompareForFirstTest.csv";
    private static final String FILE_TO_COMPARE_FOR_SECOND_TEST = "src/test/resources/fileToCompareForSecondTest.csv";

    OutputStream fileOutPutStream;
    StandardConverter standardConverter;

    @BeforeEach
    public void setUp() {

        try {
            fileOutPutStream = new FileOutputStream(FILE_NAME);
        } catch (FileNotFoundException exception) {
            System.out.printf("Файл %s не найден", FILE_NAME);

        }
        standardConverter = new StandardCsvConverter(new CsvConverter());
    }

    @Test
    public void checkCsvConversionSuccessful() {

        Map<String, String> testMap1 = new HashMap<>();
        testMap1.put("Project", "Test Project 1");
        testMap1.put("Lead", "Test Lead 1");

        List<Map<String, String>> collectionToConvert = new ArrayList<>();
        collectionToConvert.add(testMap1);

        Map<String, String> testMap2 = new HashMap<>();
        testMap2.put("Project", "Test Project 2");
        testMap2.put("Lead", "Test Lead 2");
        collectionToConvert.add(testMap2);

        try {
            standardConverter.convert(collectionToConvert, fileOutPutStream);
            assertTrue(FileUtils.contentEquals(new File(FILE_NAME), new File(FILE_TO_COMPARE_FOR_FIRST_TEST)));
        } catch (MapsContainDifferentKeySetException | IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Test
    public void checkCsvConversionIsSuccessfulWhenElementsHaveSpecialCharacters() {

        Map<String, String> testMap1 = new HashMap<>();
        testMap1.put("Project", "Test Project 1");
        testMap1.put("Lead", "Test Lead 1");
        testMap1.put("Data", "[Some,-Data_1]");

        List<Map<String, String>> collectionToConvert = new ArrayList<>();
        collectionToConvert.add(testMap1);

        Map<String, String> testMap2 = new HashMap<>();
        testMap2.put("Project", "Test Project 2");
        testMap2.put("Lead", "Test Lead 2");
        testMap2.put("Data", "[Some,-Data_2]");
        collectionToConvert.add(testMap2);

        try {
            standardConverter.convert(collectionToConvert, fileOutPutStream);
            assertTrue(FileUtils.contentEquals(new File(FILE_NAME), new File(FILE_TO_COMPARE_FOR_SECOND_TEST)));
        } catch (MapsContainDifferentKeySetException | IOException exception) {
            throw new RuntimeException(exception);
        }
    }

}
