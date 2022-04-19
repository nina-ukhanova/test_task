package org.eagleinvsys.test.converters.impl.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.eagleinvsys.test.converters.interfaces.ConvertibleCollection;
import org.eagleinvsys.test.converters.interfaces.ConvertibleMessage;
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

        List<Set<String>> elementIdList = collectionToConvert.stream().map(Map::keySet).collect(Collectors.toList());

        for (Map<String, String> elementsMap : collectionToConvert) {
            for (Set<String> elementIds : elementIdList) {
                if (!compareSets(elementsMap.keySet(), elementIds)) {
                    throw new MapsContainDifferentKeySetException("Maps in the list should contain the same key sets");
                }
            }
        }

        convertibleCollection.setHeaders(collectionToConvert.get(0).keySet());
        for (Map<String, String> elementsMap : collectionToConvert) {
              convertibleCollection.addToRecords(new ConvertibleMessageImpl(elementsMap));
        }

        return convertibleCollection;
    }

    public static boolean compareSets(Set<String> set1, Set<String> set2) {
        return set1.equals(set2);
    }
}
