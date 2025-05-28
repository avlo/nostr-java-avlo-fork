package nostr.event.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import nostr.base.PublicKey;
import nostr.base.Relay;
import nostr.event.BaseTag;
import nostr.event.impl.GenericEvent;
import nostr.event.tag.AddressTag;
import nostr.event.tag.IdentifierTag;
import org.apache.logging.log4j.util.Strings;

import static nostr.base.IDecoder.I_DECODER_MAPPER_AFTERBURNER;

@EqualsAndHashCode(callSuper = true)
public class AddressTagFilter<T extends AddressTag> extends AbstractFilterable<T> {
  public final static String FILTER_KEY = "#a";

  public AddressTagFilter(T addressableTag) {
    super(addressableTag, FILTER_KEY);
  }

  @Override
  public Predicate<GenericEvent> getPredicate() {
    return (genericEvent) ->
        Filterable.getTypeSpecificTags(AddressTag.class, genericEvent).stream()
            .anyMatch(addressTag ->
                addressTag.equals(getAddressableTag()));
  }

  @Override
  public Object getFilterableValue() {
    String requiredAttributes = Stream.of(
            getAddressableTag().getKind(),
            getAddressableTag().getPublicKey().toHexString())
        .map(Object::toString).collect(Collectors.joining(":"));

    String identifierTagPortion = Optional.ofNullable(getAddressableTag().getIdentifierTag()).map(identifierTag ->
        String.join(":", requiredAttributes, identifierTag.getUuid())).orElse(
        Strings.concat(requiredAttributes, ":"));

    String s = Optional.ofNullable(getAddressableTag().getRelay()).map(relay ->
        String.join("\",\"", identifierTagPortion, relay.getUri())).orElse(identifierTagPortion);
    return s;
  }

  private T getAddressableTag() {
    return super.getFilterable();
  }

  public static Function<JsonNode, Filterable> fxn = node ->
      new AddressTagFilter<>(createAddressTag(node));

  protected static <T extends BaseTag> T createAddressTag(@NonNull JsonNode node) {
    ArrayNode arrayNode = I_DECODER_MAPPER_AFTERBURNER.createArrayNode();
    arrayNode.addAll(StreamSupport.stream(node.spliterator(), false).toList());

    List<JsonNode> list1 = StreamSupport.stream(arrayNode.spliterator(), false).toList();
    List<String> nodes = List.of(arrayNode.get(0).asText().split(":"));

    final AddressTag addressTag = new AddressTag();
    addressTag.setKind(Integer.valueOf(nodes.get(0)));
    addressTag.setPublicKey(new PublicKey(nodes.get(1)));

    if (nodes.size() > 2) {
      addressTag.setIdentifierTag(
          new IdentifierTag(nodes.get(2)));
    }

    if (list1.size() < 2)
      return (T) addressTag;

    Optional.ofNullable(list1.get(1)).ifPresent(s ->
        addressTag.setRelay(
            new Relay(
                s.asText().replaceAll("^\"", ""))));

    return (T) addressTag;
  }
}
