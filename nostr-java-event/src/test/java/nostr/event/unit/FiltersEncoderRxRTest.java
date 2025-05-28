//package nostr.event.unit;
//
//import lombok.extern.slf4j.Slf4j;
//import nostr.base.PublicKey;
//import nostr.event.filter.AddressTagFilterRxR;
//import nostr.event.filter.FiltersRxR;
//import nostr.event.json.codec.FiltersEncoderRxR;
//import nostr.event.tag.AddressTagRxR;
//import nostr.event.tag.IdentifierTag;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@Slf4j
//public class FiltersEncoderRxRTest {
//
//  @Test
//  public void testAddressableTagFilterKindAndPublicKey() {
//    log.info("testAddressableTagFilterKindAndPublicKey");
//
//    Integer kind = 1;
//    String author = "f1b419a95cb0233a11d431423b41a42734e7165fcab16081cd08ef1c90e0be75";
//
//    AddressTagRxR addressTag = new AddressTagRxR();
//    addressTag.setKind(kind);
//    addressTag.setPublicKey(new PublicKey(author));
//
//    FiltersEncoderRxR encoder = new FiltersEncoderRxR(new FiltersRxR(new AddressTagFilterRxR<>(addressTag)));
//    String encodedFilters = encoder.encode();
//    String addressableTag = String.join(":", String.valueOf(kind), author) + ":";
//
//    assertEquals("{\"#a\":[\"" + addressableTag + "\"]}", encodedFilters);
//  }
//
//  @Test
//  public void testMultipleAddressableTagFilterEncoder() {
//    log.info("testMultipleAddressableTagFilterEncoder");
//
//    Integer kind = 1;
//    String author = "f1b419a95cb0233a11d431423b41a42734e7165fcab16081cd08ef1c90e0be75";
//    String uuidValue1 = "UUID-1";
//    String uuidValue2 = "UUID-2";
//
//    String addressableTag1 = String.join(":", String.valueOf(kind), author, uuidValue1);
//    String addressableTag2 = String.join(":", String.valueOf(kind), author, uuidValue2);
//
//    AddressTagRxR addressTag1 = new AddressTagRxR();
//    addressTag1.setKind(kind);
//    addressTag1.setPublicKey(new PublicKey(author));
//    addressTag1.setIdentifierTag(new IdentifierTag(uuidValue1));
//
//    AddressTagRxR addressTag2 = new AddressTagRxR();
//    addressTag2.setKind(kind);
//    addressTag2.setPublicKey(new PublicKey(author));
//    addressTag2.setIdentifierTag(new IdentifierTag(uuidValue2));
//
//    FiltersEncoderRxR encoder = new FiltersEncoderRxR(new FiltersRxR(
//        new AddressTagFilterRxR<>(addressTag1),
//        new AddressTagFilterRxR<>(addressTag2)));
//
//    String encoded = encoder.encode();
//    String addressableTags = String.join("\",\"", addressableTag1, addressableTag2);
//    assertEquals("{\"#a\":[\"" + addressableTags + "\"]}", encoded);
//  }
//}
