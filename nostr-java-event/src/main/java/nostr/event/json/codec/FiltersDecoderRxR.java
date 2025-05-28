package nostr.event.json.codec;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.NonNull;
import lombok.SneakyThrows;
import nostr.event.filter.FilterableRxR;
import nostr.event.filter.FiltersRxR;

import static nostr.base.IEvent.MAPPER_AFTERBURNER;

@Data
public class FiltersDecoderRxR implements FDecoder<FiltersRxR> {

  @SneakyThrows
  public FiltersRxR decode(@NonNull String jsonFiltersList) {
    final List<FilterableRxR> filterables = new ArrayList<>();

    Iterator<Map.Entry<String, JsonNode>> fields = MAPPER_AFTERBURNER.readTree(jsonFiltersList).fields();
    fields.forEachRemaining(node ->
        filterables.addAll(
            FilterableProviderRxR.getFilterFunction(
                node.getValue(),
                node.getKey())));

    return new FiltersRxR(filterables);
  }
}
