package org.eagleinvsys.test.converters.impl.collection;

import java.util.Map;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.eagleinvsys.test.converters.interfaces.ConvertibleMessage;

@AllArgsConstructor
@NoArgsConstructor
public class ConvertibleMessageImpl implements ConvertibleMessage {

    Map<String, String> elements;

    @Override
    public String getElement(String elementId) {
        return Optional.of(elements.get(elementId))
                .orElse(StringUtils.EMPTY);
    }
}
