package nostr.event.json.codec;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import nostr.base.IDecoder;
import nostr.event.impl.GenericEventImpl;

/**
 *
 * @author eric
 */
@Data
@AllArgsConstructor
public class GenericEventDecoder implements IDecoder<GenericEventImpl> {

    private final String jsonEvent;

    @Override
    public GenericEventImpl decode() {
        try {
            var mapper = new ObjectMapper();
            mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
            return mapper.readValue(jsonEvent, GenericEventImpl.class);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }
}
