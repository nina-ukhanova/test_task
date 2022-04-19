package org.eagleinvsys.test.converters;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eagleinvsys.test.converters.exception.MapsContainDifferentKeySetException;
import org.eagleinvsys.test.converters.impl.CsvConverter;
import org.eagleinvsys.test.converters.impl.collection.ConvertibleCollectionImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvConverterTests {

    private static final String FILE_NAME = "src/test/resources/test.csv";

    OutputStream fileOutPutStream;
    CsvConverter csvConverter = new CsvConverter();

    @BeforeEach
    public void setUp() {

        try {
            fileOutPutStream = new FileOutputStream(FILE_NAME);
        } catch (FileNotFoundException exception) {
            System.out.printf("Файл %s не найден", FILE_NAME);
        }
    }

    @Test
    public void checkErrorIfMapsHaveDifferentSetOfKeys() {

        Map<String, String> testMap1 = new HashMap<>();
        testMap1.put("Project", "Test Project 1");

        List<Map<String, String>> collectionToConvert = new ArrayList<>();
        collectionToConvert.add(testMap1);

        Map<String, String> testMap2 = new HashMap<>();
        testMap2.put("Project", "Test Project 2");
        testMap2.put("Lead", "Test Lead 2");
        collectionToConvert.add(testMap2);

        assertThrows(MapsContainDifferentKeySetException.class,
                () -> csvConverter.convert(ConvertibleCollectionImpl.create(collectionToConvert), fileOutPutStream));
    }

}
