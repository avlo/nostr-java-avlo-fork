//package nostr.event.json.codec;
//
//import com.fasterxml.jackson.databind.node.ObjectNode;
//import nostr.base.Encoder;
//import nostr.event.filter.FiltersRxR;
//
//public record FiltersEncoderRxR(FiltersRxR filters) implements Encoder {
//
//  @Override
//  public String encode() {
//    ObjectNode root = ENCODER_MAPPED_AFTERBURNER.createObjectNode();
//
//    filters.getFiltersMap().forEach((key, filterableList) ->
//        root.setAll(
//            filterableList
//                .stream()
//                .map(filterable ->
//                         filterable.toObjectNode(root))
//                .toList()
//                .getFirst()));
//
//    return root.toString();
//  }
//}
