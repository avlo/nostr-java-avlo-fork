package nostr.event.json.codec;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import nostr.base.IDecoder;
import nostr.event.BaseMessage;
import nostr.event.message.OkMessage;
import nostr.event.message.ReqMessageRxR;

@NoArgsConstructor
public class BaseMessageDecoderRxR<T extends BaseMessage> implements IDecoder<T> {
  public static final int COMMAND_INDEX = 0;
  public static final int ARG_INDEX = 1;

  @Override
  public T decode(@NonNull String jsonString) throws JsonProcessingException {
    ValidNostrJsonStructure validNostrJsonStructure = validateProperlyFormedJson(jsonString);
    String command = validNostrJsonStructure.getCommand();
    Object subscriptionId = validNostrJsonStructure.getSubscriptionId();

    return switch (command) {
//          client <-> relay messages
//            missing client <-> relay handlers
//            case "COUNT" -> CountMessage.decode(subscriptionId);

//            client -> relay messages
      case "REQ" -> ReqMessageRxR.decode(subscriptionId, jsonString);

//            relay -> client handlers
      case "OK" -> OkMessage.decode(jsonString);
//            missing relay -> client handlers
//            case "CLOSED" -> Closed.message.decode(subscriptionId);

      default ->
          throw new IllegalArgumentException(String.format("Invalid JSON command [%s] in JSON string [%s] ", command, jsonString));
    };
  }

  private ValidNostrJsonStructure validateProperlyFormedJson(@NonNull String jsonString) throws JsonProcessingException {
    return new ValidNostrJsonStructure(
        I_DECODER_MAPPER_AFTERBURNER.readTree(jsonString).get(COMMAND_INDEX).asText(),
        I_DECODER_MAPPER_AFTERBURNER.readTree(jsonString).get(ARG_INDEX).asText());
  }

  private record ValidNostrJsonStructure(
      @NonNull String getCommand,
      @NonNull Object getSubscriptionId) {
  }
}
