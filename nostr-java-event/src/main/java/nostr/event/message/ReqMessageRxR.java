package nostr.event.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import java.time.temporal.ValueRange;
import java.util.List;
import java.util.stream.IntStream;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.ToString;
import nostr.base.Command;
import nostr.event.BaseMessage;
import nostr.event.filter.FiltersRxR;
import nostr.event.json.codec.FiltersDecoderRxR;
import nostr.event.json.codec.FiltersEncoderRxR;

import static nostr.base.Encoder.ENCODER_MAPPED_AFTERBURNER;
import static nostr.base.IDecoder.I_DECODER_MAPPER_AFTERBURNER;

/**
 * @author squirrel
 */
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class ReqMessageRxR extends BaseMessage {
    public static final int FILTERS_START_INDEX = 2;

    @JsonProperty
    private final String subscriptionId;

    @JsonProperty
    private final List<FiltersRxR> filtersList;

    public ReqMessageRxR(@NonNull String subscriptionId, @NonNull FiltersRxR... filtersList) {
        this(subscriptionId, List.of(filtersList));
    }

    public ReqMessageRxR(@NonNull String subscriptionId, @NonNull List<FiltersRxR> filtersList) {
        super(Command.REQ.name());
        validateSubscriptionId(subscriptionId);
        this.subscriptionId = subscriptionId;
        this.filtersList = filtersList;
    }

    @Override
    public String encode() throws JsonProcessingException {
        var encoderArrayNode = JsonNodeFactory.instance.arrayNode();
        encoderArrayNode
          .add(getCommand())
          .add(getSubscriptionId());

        filtersList.stream()
          .map(FiltersEncoderRxR::new)
          .map(FiltersEncoderRxR::encode)
          .map(ReqMessageRxR::createJsonNode)
          .forEach(encoderArrayNode::add);

        String s = ENCODER_MAPPED_AFTERBURNER.writeValueAsString(encoderArrayNode);
        return s;
    }

    public static <T extends BaseMessage> T decode(@NonNull Object subscriptionId, @NonNull String jsonString) throws JsonProcessingException {
        validateSubscriptionId(subscriptionId.toString());
        List<String> jsonFiltersList = getJsonFiltersList(jsonString);
        List<FiltersRxR> list = jsonFiltersList.stream().map(filtersList ->
        {
            FiltersRxR decode = new FiltersDecoderRxR().decode(filtersList);
            return decode;
        }).toList();
        
        T t = (T) new ReqMessageRxR(
            subscriptionId.toString(),
            list);
        return t;
    }

    private static JsonNode createJsonNode(String jsonNode) {
        try {
            return ENCODER_MAPPED_AFTERBURNER.readTree(jsonNode);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(String.format("Malformed encoding ReqMessage json: [%s]", jsonNode), e);
        }
    }

    private static void validateSubscriptionId(String subscriptionId) {
        if (!ValueRange.of(1, 64).isValidIntValue(subscriptionId.length())) {
            throw new IllegalArgumentException(String.format("SubscriptionId length must be between 1 and 64 characters but was [%d]", subscriptionId.length()));
        }
    }

    private static List<String> getJsonFiltersList(String jsonString) throws JsonProcessingException {
        List<String> list = IntStream.range(FILTERS_START_INDEX, I_DECODER_MAPPER_AFTERBURNER.readTree(jsonString).size())
            .mapToObj(idx -> readTree(jsonString, idx)).toList();
        return list;
    }

    @SneakyThrows
    private static String readTree(String jsonString, int idx) {
        String string = I_DECODER_MAPPER_AFTERBURNER.readTree(jsonString).get(idx).toString();
        return string;
    }
}
