package org.eagleinvsys.test.converters.impl;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import org.eagleinvsys.test.converters.interfaces.ConvertibleCollection;
import org.eagleinvsys.test.converters.interfaces.StandardConverter;
import org.eagleinvsys.test.converters.exception.MapsContainDifferentKeySetException;
import org.eagleinvsys.test.converters.impl.collection.ConvertibleCollectionImpl;

public class StandardCsvConverter implements StandardConverter {

    private final CsvConverter csvConverter;

    public StandardCsvConverter(CsvConverter csvConverter) {
        this.csvConverter = csvConverter;
    }

    /**
     * Converts given {@link List<Map>} to CSV and outputs result as a text to the provided {@link OutputStream}
     *
     * @param collectionToConvert collection to convert to CSV format. All maps must have the same set of keys
     * @param outputStream        output stream to write CSV conversion result as text to
     */
    @Override
    public void convert(List<Map<String, String>> collectionToConvert, OutputStream outputStream)
            throws MapsContainDifferentKeySetException {

        ConvertibleCollection convertibleCollection = ConvertibleCollectionImpl.create(collectionToConvert);


        if (collectionToConvert == null) {
            return;
        }

        csvConverter.convert(convertibleCollection, outputStream);
    }
}
