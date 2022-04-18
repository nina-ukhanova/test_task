package org.eagleinvsys.test.converters.impl.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.eagleinvsys.test.converters.ConvertibleCollection;
import org.eagleinvsys.test.converters.ConvertibleMessage;
import org.eagleinvsys.test.converters.exception.MapsContainDifferentKeySetException;

@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ConvertibleCollectionImpl implements ConvertibleCollection {

    private Collection<String> headers;
    private Collection<ConvertibleMessage> records;

    @Override
    public Collection<String> getHeaders() {
        return headers;
    }

    @Override
    public Iterable<ConvertibleMessage> getRecords() {
        return records;
    }

    public void addToHeaders(String header) {
        if (CollectionUtils.isEmpty(headers)) {
            headers = new ArrayList<>();
        }
        headers.add(header);
    }

    public void addToRecords(ConvertibleMessage message) {
        if (CollectionUtils.isEmpty(records)) {
            records = new ArrayList<>();
        }

        records.add(message);
    }

    public static ConvertibleCollection create(List<Map<String, String>> collectionToConvert)
            throws MapsContainDifferentKeySetException {
        ConvertibleCollectionImpl convertibleCollection = new ConvertibleCollectionImpl();

        if (CollectionUtils.isEmpty(collectionToConvert)) {
            return convertibleCollection;
        }

        for (Map<String, String> elementsMap : collectionToConvert) {
            for (Map<String, String> mapToCompare : collectionToConvert) {
                if (!compareMaps(elementsMap, mapToCompare)) {
                    throw new MapsContainDifferentKeySetException();
                }
            }
        }

        convertibleCollection.setHeaders(collectionToConvert.get(0).keySet());
        for (Map<String, String> elementsMap : collectionToConvert) {
              convertibleCollection.addToRecords(new ConvertibleMessageImpl(elementsMap));
        }

        return convertibleCollection;

    }

    public static boolean compareMaps(Map<String, String> map1, Map<String, String> map2) {
        return map1.keySet().equals(map2.keySet());
    }
}
