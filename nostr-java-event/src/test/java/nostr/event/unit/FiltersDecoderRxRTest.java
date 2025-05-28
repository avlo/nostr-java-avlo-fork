package nostr.event.unit;

import lombok.extern.slf4j.Slf4j;
import nostr.base.PublicKey;
import nostr.base.Relay;
import nostr.event.filter.AddressTagFilterRxR;
import nostr.event.filter.FiltersRxR;
import nostr.event.json.codec.FiltersDecoderRxR;
import nostr.event.tag.AddressTagRxR;
import nostr.event.tag.IdentifierTag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class FiltersDecoderRxRTest {

  @Test
  public void testAddressTagFiltersKindPublicKey() {
    log.info("testAddressTagFiltersKindPublicKey");

    Integer kind = 1;
    String author = "f1b419a95cb0233a11d431423b41a42734e7165fcab16081cd08ef1c90e0be75";

    String joined = String.join(":", String.valueOf(kind), author) + ":";

    String manualJoined = "1:f1b419a95cb0233a11d431423b41a42734e7165fcab16081cd08ef1c90e0be75:";

    AddressTagRxR addressTag = new AddressTagRxR();
    addressTag.setKind(kind);
    addressTag.setPublicKey(new PublicKey(author));

    String expected = "{\"#a\":[\"" + manualJoined + "\"]}";
    FiltersRxR decodedFilters = new FiltersDecoderRxR().decode(expected);

    FiltersRxR expectedFilters = new FiltersRxR(
        new AddressTagFilterRxR<>(addressTag));
    assertEquals(
        expectedFilters,
        decodedFilters);
  }

  @Test
  public void testAddressTagFiltersKindPublicKeyIdentifierTag() {
    log.info("testAddressTagFiltersKindPublicKeyIdentifierTag");

    Integer kind = 1;
    String author = "f1b419a95cb0233a11d431423b41a42734e7165fcab16081cd08ef1c90e0be75";
    String uuidValue1 = "UUID-1";

    String joined = String.join(":", String.valueOf(kind), author, uuidValue1);

    String manualJoined = "1:f1b419a95cb0233a11d431423b41a42734e7165fcab16081cd08ef1c90e0be75:UUID-1";

    AddressTagRxR addressTag = new AddressTagRxR();
    addressTag.setKind(kind);
    addressTag.setPublicKey(new PublicKey(author));
    addressTag.setIdentifierTag(new IdentifierTag(uuidValue1));

    String expected = "{\"#a\":[\"" + manualJoined + "\"]}";
    FiltersRxR decodedFilters = new FiltersDecoderRxR().decode(expected);

    assertEquals(
        new FiltersRxR(
            new AddressTagFilterRxR<>(addressTag)),
        decodedFilters);
  }

  @Test
  public void testAddressableTagFiltersWithRelayDecoder() {
    log.info("testAddressableTagFiltersWithRelayDecoder");

    Integer kind = 1;
    String author = "f1b419a95cb0233a11d431423b41a42734e7165fcab16081cd08ef1c90e0be75";
    String uuidValue1 = "UUID-1";
    Relay relay = new Relay("ws://localhost:5555");

    String joined = String.join(":", String.valueOf(kind), author, uuidValue1);

    String manualJoined = "1:f1b419a95cb0233a11d431423b41a42734e7165fcab16081cd08ef1c90e0be75:UUID-1";

    AddressTagRxR addressTag = new AddressTagRxR();
    addressTag.setKind(kind);
    addressTag.setPublicKey(new PublicKey(author));
    addressTag.setIdentifierTag(new IdentifierTag(uuidValue1));
    addressTag.setRelay(relay);

    String expected = String.join(",", manualJoined, relay.getUri());

    String manualExpected = String.join("\",\"", manualJoined, relay.getUri());
    String addressableTag = "{\"#a\":[\"" + manualExpected + "\"]}";
    FiltersRxR decodedFilters = new FiltersDecoderRxR().decode(addressableTag);

    assertEquals(
        new FiltersRxR(
            new AddressTagFilterRxR<>(addressTag)),
        decodedFilters);
  }
}
