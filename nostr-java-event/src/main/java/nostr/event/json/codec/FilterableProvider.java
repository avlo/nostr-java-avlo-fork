package nostr.event.json.codec;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.StreamSupport;
import lombok.NonNull;
import lombok.SneakyThrows;
import nostr.base.IDecoder;
import nostr.event.filter.AddressTagFilter;
import nostr.event.filter.AuthorFilter;
import nostr.event.filter.EventFilter;
import nostr.event.filter.Filterable;
import nostr.event.filter.GenericTagQueryFilter;
import nostr.event.filter.GeohashTagFilter;
import nostr.event.filter.HashtagTagFilter;
import nostr.event.filter.IdentifierTagFilter;
import nostr.event.filter.KindFilter;
import nostr.event.filter.ReferencedEventFilter;
import nostr.event.filter.ReferencedPublicKeyFilter;
import nostr.event.filter.SinceFilter;
import nostr.event.filter.UntilFilter;
import nostr.event.filter.VoteTagFilter;
import org.apache.logging.log4j.util.Strings;

public class FilterableProvider {
  protected static List<Filterable> getFilterFunction(@NonNull JsonNode node, @NonNull String type) {
    return switch (type) {
      case ReferencedPublicKeyFilter.FILTER_KEY -> getFilterable(node, ReferencedPublicKeyFilter.fxn);
      case ReferencedEventFilter.FILTER_KEY -> getFilterable(node, ReferencedEventFilter.fxn);
      case IdentifierTagFilter.FILTER_KEY -> getFilterable(node, IdentifierTagFilter.fxn);
      case AddressTagFilter.FILTER_KEY -> getFilterableMulti(node, AddressTagFilter.fxn);
      case GeohashTagFilter.FILTER_KEY -> getFilterable(node, GeohashTagFilter.fxn);
      case HashtagTagFilter.FILTER_KEY -> getFilterable(node, HashtagTagFilter.fxn);
      case VoteTagFilter.FILTER_KEY -> getFilterable(node, VoteTagFilter.fxn);
      case AuthorFilter.FILTER_KEY -> getFilterable(node, AuthorFilter.fxn);
      case EventFilter.FILTER_KEY -> getFilterable(node, EventFilter.fxn);
      case KindFilter.FILTER_KEY -> getFilterable(node, KindFilter.fxn);
      case SinceFilter.FILTER_KEY -> SinceFilter.fxn.apply(node);
      case UntilFilter.FILTER_KEY -> UntilFilter.fxn.apply(node);
      default -> getFilterable(node, GenericTagQueryFilter.fxn(type));
    };
  }

  private static List<Filterable> getFilterable(JsonNode jsonNode, Function<JsonNode, Filterable> filterFunction) {
    return StreamSupport.stream(jsonNode.spliterator(), false).map(filterFunction).toList();
  }

  @SneakyThrows
  private static List<Filterable> getFilterableMulti(JsonNode jsonNode, Function<JsonNode, Filterable> filterFunction) {
    List<Filterable> list = new ArrayList<>();
    JsonNode jsonNode1 = getJsonNode1(jsonNode);
    Iterator<JsonNode> iterator = jsonNode1.iterator();
    iterator.forEachRemaining(node ->
    {
      Filterable applied = filterFunction.apply(node);
      list.add(applied);
    });
    return list;
  }

  private static JsonNode getJsonNode1(JsonNode jsonNode) throws JsonProcessingException {
    String string = jsonNode.toString();
    String prefix = Strings.concat("[", string);
    String postfix = Strings.concat(prefix, "]");
    JsonNode jsonNode1 = IDecoder.I_DECODER_MAPPER_AFTERBURNER.readTree(postfix);
    boolean b = string.startsWith("[[");
    if (b) {
      return jsonNode;
    }
    return jsonNode1;
  }
}
