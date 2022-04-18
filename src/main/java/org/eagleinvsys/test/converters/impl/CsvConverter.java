package org.eagleinvsys.test.converters.impl;

import org.apache.commons.lang3.StringUtils;
import org.eagleinvsys.test.converters.Converter;
import org.eagleinvsys.test.converters.ConvertibleCollection;
import org.eagleinvsys.test.converters.ConvertibleMessage;

import java.io.IOException;
import java.io.OutputStream;

public class CsvConverter implements Converter {

    /**
     * Converts given {@link ConvertibleCollection} to CSV and outputs result as a text to the provided {@link OutputStream}
     *
     * @param collectionToConvert collection to convert to CSV format
     * @param outputStream        output stream to write CSV conversion result as text to
     */
    @Override
    public void convert(ConvertibleCollection collectionToConvert, OutputStream outputStream) {
        // TODO: implement
        StringBuilder resultString = new StringBuilder(StringUtils.join(
                collectionToConvert.getHeaders(), ","));
        resultString.append("\n");
        try {
            for (ConvertibleMessage message : collectionToConvert.getRecords()) {
                for (String header : collectionToConvert.getHeaders()) {
                  resultString.append(message.getElement(header)).append(",");
                }
                resultString.append("\n");
            }
            outputStream.write(resultString.toString().getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
