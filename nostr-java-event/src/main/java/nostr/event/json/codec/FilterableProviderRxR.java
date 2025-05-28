//package nostr.event.json.codec;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonNode;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import java.util.function.Function;
//import lombok.NonNull;
//import lombok.SneakyThrows;
//import nostr.base.IDecoder;
//import nostr.event.filter.AddressTagFilterRxR;
//import nostr.event.filter.FilterableRxR;
//import org.apache.logging.log4j.util.Strings;
//
//public class FilterableProviderRxR {
//  protected static List<FilterableRxR> getFilterFunction(@NonNull JsonNode node, @NonNull String type) {
//    return switch (type) {
//      case AddressTagFilterRxR.FILTER_KEY -> getFilterableMulti(node, AddressTagFilterRxR.fxn);
//      default -> null;
//    };
//  }
//
//  @SneakyThrows
//  private static List<FilterableRxR> getFilterableMulti(JsonNode jsonNode, Function<JsonNode, FilterableRxR> filterFunction) {
//    List<FilterableRxR> list = new ArrayList<>();
//    JsonNode jsonNode1 = getJsonNode1(jsonNode);
//    Iterator<JsonNode> iterator = jsonNode1.iterator();
//    iterator.forEachRemaining(node ->
//    {
//      FilterableRxR applied = filterFunction.apply(node);
//      list.add(applied);
//    });
//    return list;
//  }
//
//  private static JsonNode getJsonNode1(JsonNode jsonNode) throws JsonProcessingException {
//    String string = jsonNode.toString();
//    String prefix = Strings.concat("[", string);
//    String postfix = Strings.concat(prefix, "]");
//    JsonNode jsonNode1 = IDecoder.I_DECODER_MAPPER_AFTERBURNER.readTree(postfix);
//    boolean b = string.startsWith("[[");
//    if (b) {
//      return jsonNode;
//    }
//    return jsonNode1;
//  }
//}
