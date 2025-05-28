package nostr.event.filter;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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
        String[] nodes = node.asText().split(",");
        List<String> list = Arrays.stream(nodes[0].split(":")).toList();

        final AddressTag addressTag = new AddressTag();
        addressTag.setKind(Integer.valueOf(list.get(0)));
        addressTag.setPublicKey(new PublicKey(list.get(1)));

        if (list.size() < 3)
            return (T) addressTag;

        Optional.ofNullable(list.get(2)).ifPresent(identifierTag ->
            addressTag.setIdentifierTag(
//                new IdentifierTag(
//                identifierTag.replaceAll("\"$", ""))
                new IdentifierTag(identifierTag)
            ));

        if (!Objects.equals(2, nodes.length))
            return (T) addressTag;

        addressTag.setRelay(
            new Relay(
                nodes[1].replaceAll("^\"", "")));

        return (T) addressTag;
    }

    protected static <T extends BaseTag> T createAddressTagRxR(@NonNull JsonNode node) {
        List<JsonNode> nodeList = StreamSupport.stream(node.spliterator(), false).toList();
        List<String> requiredLIst = Arrays.stream(nodeList.get(0).asText().split(":")).toList();

        final AddressTag addressTag = new AddressTag();
        addressTag.setKind(Integer.valueOf(requiredLIst.get(0)));
        addressTag.setPublicKey(new PublicKey(requiredLIst.get(1)));

        if (requiredLIst.size() < 3)
            return (T) addressTag;

        Optional.ofNullable(requiredLIst.get(2)).ifPresent(identifierTag ->
            addressTag.setIdentifierTag(
//                new IdentifierTag(
//                identifierTag.replaceAll("\"$", ""))
                new IdentifierTag(identifierTag)
            ));

        if (!Objects.equals(2, nodeList.size()))
            return (T) addressTag;

        addressTag.setRelay(
            new Relay(
                nodeList.get(1).asText()
//                    .replaceAll("^\"", "")
            ));

        return (T) addressTag;
    }


}
