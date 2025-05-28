package nostr.api.unit;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import nostr.base.PublicKey;
import nostr.base.Relay;
import nostr.event.filter.AddressTagFilterRxR;
import nostr.event.filter.FiltersRxR;
import nostr.event.json.codec.BaseMessageDecoderRxR;
import nostr.event.message.ReqMessageRxR;
import nostr.event.tag.AddressTagRxR;
import nostr.event.tag.IdentifierTag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class JsonParseRxRTest {
  @Test
//  1111111111111111111111111111111111111
//  1111111111111111111111111111111111111  
  public void testReqMessagePopulatedListOfFiltersListDecoder() throws JsonProcessingException {
    log.info("testReqMessagePopulatedListOfFiltersListDecoder");

    String subscriptionId = "npub17x6pn22ukq3n5yw5x9prksdyyu6ww9jle2ckpqwdprh3ey8qhe6stnpujh";
    Integer kind = 1;
    String author = "f1b419a95cb0233a11d431423b41a42734e7165fcab16081cd08ef1c90e0be75";
    String uuidValue1 = "UUID-1";

    String addressableTag = String.join(":", String.valueOf(kind), author, uuidValue1);

    String reqJsonWithCustomTagQueryFilterToDecode =
        "[\"REQ\", " +
            "\"" + subscriptionId + "\", " +
            "{" +
//            "kinds\": [" + kind + "], " +
            "\"#a\": [\"" + addressableTag + "\"]" +
            "}]";

    ReqMessageRxR decodedReqMessage = new BaseMessageDecoderRxR<ReqMessageRxR>().decode(reqJsonWithCustomTagQueryFilterToDecode);

    AddressTagRxR addressTag1 = new AddressTagRxR(kind, new PublicKey(author), new IdentifierTag(uuidValue1));

    ReqMessageRxR expectedReqMessage = new ReqMessageRxR(subscriptionId,
        new FiltersRxR(
            new AddressTagFilterRxR<>(addressTag1)));

    String encoded = expectedReqMessage.encode();
    String decoded = decodedReqMessage.encode();
    assertEquals(encoded, decoded);
    assertEquals(expectedReqMessage, decodedReqMessage);
  }

  @Test
//  22222222222222222222222222222222222
//  22222222222222222222222222222222222  
  public void testReqMessageKindPubkeyAddressTagDeserializer() throws JsonProcessingException {
    log.info("testReqMessageKindPubkeyAddressTagDeserializer");

    Integer kind = 1;
    String subscriptionId = "npub1clk6vc9xhjp8q5cws262wuf2eh4zuvwupft03hy4ttqqnm7e0jrq3upup9";
    String author = "f1b419a95cb0233a11d431423b41a42734e7165fcab16081cd08ef1c90e0be75";
    String uuidKey = "#a";

    String joined1 = String.join(":", String.valueOf(kind), author) + ":";

    String reqJsonWithCustomTagQueryFilterToDecode = "[\"REQ\",\"" + subscriptionId + "\",{\"" + uuidKey + "\":[\"" + joined1 + "\"]}]";

    ReqMessageRxR decodedReqMessage = new BaseMessageDecoderRxR<ReqMessageRxR>().decode(reqJsonWithCustomTagQueryFilterToDecode);

    AddressTagRxR addressTag1 = new AddressTagRxR();
    addressTag1.setKind(kind);
    addressTag1.setPublicKey(new PublicKey(author));

    ReqMessageRxR expectedReqMessage = new ReqMessageRxR(subscriptionId, new FiltersRxR(new AddressTagFilterRxR<>(addressTag1)));

    assertEquals(expectedReqMessage.encode(), decodedReqMessage.encode());
    assertEquals(expectedReqMessage, decodedReqMessage);
  }

  @Test
//  33333333333333333333333333333333
//  33333333333333333333333333333333  
  public void testReqMessageKindPubkeyIdentifierTagRelayAddressTagDeserializerUnNestedVariant() throws JsonProcessingException {
    log.info("testReqMessageKindPubkeyIdentifierTagRelayAddressTagDeserializerUnNestedVariant");

    Integer kind = 1;
    String subscriptionId = "npub1clk6vc9xhjp8q5cws262wuf2eh4zuvwupft03hy4ttqqnm7e0jrq3upup9";
    String author = "f1b419a95cb0233a11d431423b41a42734e7165fcab16081cd08ef1c90e0be75";
    String uuidKey = "#a";
    String uuidValue1 = "UUID-1";
    String relay = "ws://localhost:5555";

    String join = String.join("\",\"", String.join(":", String.valueOf(kind), author, uuidValue1), relay);
    String reqJsonWithCustomTagQueryFilterToDecode = "[\"REQ\",\"" + subscriptionId + "\",{\"" + uuidKey + "\":[\"" + join + "\"]}]";

    ReqMessageRxR decodedReqMessage = new BaseMessageDecoderRxR<ReqMessageRxR>().decode(reqJsonWithCustomTagQueryFilterToDecode);

    AddressTagRxR addressTag = new AddressTagRxR();
    addressTag.setKind(kind);
    addressTag.setPublicKey(new PublicKey(author));
    addressTag.setIdentifierTag(new IdentifierTag(uuidValue1));
    addressTag.setRelay(new Relay(relay));

    ReqMessageRxR expectedReqMessage = new ReqMessageRxR(subscriptionId, new FiltersRxR(new AddressTagFilterRxR<>(addressTag)));

    assertEquals(expectedReqMessage.encode(), decodedReqMessage.encode());
    assertEquals(expectedReqMessage, decodedReqMessage);
  }

  @Test
  public void testReqMessageKindPubkeyIdentifierTagRelayAddressTagDeserializerNestedVariant() throws JsonProcessingException {
    log.info("testReqMessageKindPubkeyIdentifierTagRelayAddressTagDeserializerNestedVariant");

    Integer kind = 1;
    String subscriptionId = "npub1clk6vc9xhjp8q5cws262wuf2eh4zuvwupft03hy4ttqqnm7e0jrq3upup9";
    String author = "f1b419a95cb0233a11d431423b41a42734e7165fcab16081cd08ef1c90e0be75";
    String uuidKey = "#a";
    String uuidValue1 = "UUID-1";
    String relay = "ws://localhost:5555";

    String join = String.join("\",\"", String.join(":", String.valueOf(kind), author, uuidValue1), relay);
    String reqJsonWithCustomTagQueryFilterToDecode = "[\"REQ\",\"" + subscriptionId + "\",{\"" + uuidKey + "\":[[\"" + join + "\"]]}]";

    ReqMessageRxR decodedReqMessage = new BaseMessageDecoderRxR<ReqMessageRxR>().decode(reqJsonWithCustomTagQueryFilterToDecode);

    AddressTagRxR addressTag = new AddressTagRxR();
    addressTag.setKind(kind);
    addressTag.setPublicKey(new PublicKey(author));
    addressTag.setIdentifierTag(new IdentifierTag(uuidValue1));
    addressTag.setRelay(new Relay(relay));

    ReqMessageRxR expectedReqMessage = new ReqMessageRxR(subscriptionId, new FiltersRxR(new AddressTagFilterRxR<>(addressTag)));

    assertEquals(expectedReqMessage.encode(), decodedReqMessage.encode());
    assertEquals(expectedReqMessage, decodedReqMessage);
  }

  @Test
//  444444444444444444444444444444
//  444444444444444444444444444444    
  public void testMultipleAddressableTagFiltersDecoder() throws JsonProcessingException {
    log.info("testMultipleAddressableTagFiltersDecoder");

    String subscriptionId = "npub1clk6vc9xhjp8q5cws262wuf2eh4zuvwupft03hy4ttqqnm7e0jrq3upup9";
    String uuidKey = "#a";

    Integer kind1 = 1;
    String author1 = "f1b419a95cb0233a11d431423b41a42734e7165fcab16081cd08ef1c90e0be75";
    String uuidValue1 = "UUID-1";

    Integer kind2 = 1;
    String author2 = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
    String uuidValue2 = "UUID-2";

    String joined1 = String.join(":", String.valueOf(kind1), author1, uuidValue1);
    String joined2 = String.join(":", String.valueOf(kind2), author2, uuidValue2);

    String joined3 = String.join("\"],[\"", joined1, joined2);
    String reqJsonWithCustomTagQueryFilterToDecode = "[\"REQ\",\"" + subscriptionId + "\",{\"" + uuidKey + "\":[[\"" + joined3 + "\"]]}]";

    ReqMessageRxR decodedReqMessage = new BaseMessageDecoderRxR<ReqMessageRxR>().decode(reqJsonWithCustomTagQueryFilterToDecode);

    AddressTagRxR addressTag1 = new AddressTagRxR();
    addressTag1.setKind(kind1);
    addressTag1.setPublicKey(new PublicKey(author1));
    addressTag1.setIdentifierTag(new IdentifierTag(uuidValue1));

    AddressTagRxR addressTag2 = new AddressTagRxR();
    addressTag2.setKind(kind2);
    addressTag2.setPublicKey(new PublicKey(author2));
    addressTag2.setIdentifierTag(new IdentifierTag(uuidValue2));

    ReqMessageRxR expectedReqMessage = new ReqMessageRxR(subscriptionId,
        new FiltersRxR(
            new AddressTagFilterRxR<>(addressTag1),
            new AddressTagFilterRxR<>(addressTag2)));

    assertEquals(expectedReqMessage.encode(), decodedReqMessage.encode());
    assertEquals(expectedReqMessage, decodedReqMessage);
  }

  @Test
//  55555555555555555555555555555555
//  55555555555555555555555555555555    
  public void testMultipleAddressableTagFiltersWithRelaysDecoder() throws JsonProcessingException {
    log.info("testMultipleAddressableTagFiltersWithRelaysDecoder");

    String subscriptionId = "npub1clk6vc9xhjp8q5cws262wuf2eh4zuvwupft03hy4ttqqnm7e0jrq3upup9";
    String uuidKey = "#a";

    Integer kind1 = 1;
    String author1 = "f1b419a95cb0233a11d431423b41a42734e7165fcab16081cd08ef1c90e0be75";
    String uuidValue1 = "UUID-1";

    Integer kind2 = 1;
    String author2 = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
    String uuidValue2 = "UUID-2";

    String relay = "ws://localhost:5555";
    
    String joined1 = String.join("\",\"", String.join(":", String.valueOf(kind1), author1, uuidValue1), relay);
    String joined2 = String.join("\",\"", String.join(":", String.valueOf(kind2), author2, uuidValue2), relay);

    String joined3 = String.join("\"],[\"", joined1, joined2);

    String reqJsonWithCustomTagQueryFilterToDecode = "[\"REQ\",\"" + subscriptionId + "\",{\"" + uuidKey + "\":[[\"" + joined3 + "\"]]}]";

    ReqMessageRxR decodedReqMessage = new BaseMessageDecoderRxR<ReqMessageRxR>().decode(reqJsonWithCustomTagQueryFilterToDecode);

    AddressTagRxR addressTag1 = new AddressTagRxR();
    addressTag1.setKind(kind1);
    addressTag1.setPublicKey(new PublicKey(author1));
    addressTag1.setIdentifierTag(new IdentifierTag(uuidValue1));
    addressTag1.setRelay(new Relay(relay));

    AddressTagRxR addressTag2 = new AddressTagRxR();
    addressTag2.setKind(kind2);
    addressTag2.setPublicKey(new PublicKey(author2));
    addressTag2.setIdentifierTag(new IdentifierTag(uuidValue2));
    addressTag2.setRelay(new Relay(relay));

    ReqMessageRxR expectedReqMessage = new ReqMessageRxR(subscriptionId,
        new FiltersRxR(
            new AddressTagFilterRxR<>(addressTag1),
            new AddressTagFilterRxR<>(addressTag2)));

    assertEquals(expectedReqMessage.encode(), decodedReqMessage.encode());
    assertEquals(expectedReqMessage, decodedReqMessage);
  }

  @Test
//  66666666666666666666666666666666
//  66666666666666666666666666666666    
  public void testMultipleAddressableTagFiltersWithRelaysDecoderMixSizes() throws JsonProcessingException {
    log.info("testMultipleAddressableTagFiltersWithRelaysDecoderMixSizes");

    String subscriptionId = "npub1clk6vc9xhjp8q5cws262wuf2eh4zuvwupft03hy4ttqqnm7e0jrq3upup9";
    String uuidKey = "#a";

    Integer kind1 = 1;
    String author1 = "f1b419a95cb0233a11d431423b41a42734e7165fcab16081cd08ef1c90e0be75";
    String uuidValue1 = "UUID-1";

    Integer kind2 = 1;
    String author2 = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
    String uuidValue2 = "UUID-2";

    String relay = "ws://localhost:5555";

    String joined00 = String.join(":", String.valueOf(kind1), author1, uuidValue1);
    String joined01 = String.join(":", String.valueOf(kind1), author1)+":";

    String joined11 = String.join("\",\"", String.join(":", String.valueOf(kind1), author1)+":", relay);
    
    String joined21 = String.join("\",\"", String.join(":", String.valueOf(kind1), author1, uuidValue1), relay);
    String joined22 = String.join("\",\"", String.join(":", String.valueOf(kind2), author2, uuidValue2), relay);

    String joined3 = String.join("\"],[\"", joined00, joined01, joined11, joined21, joined22);

    String reqJsonWithCustomTagQueryFilterToDecode = "[\"REQ\",\"" + subscriptionId + "\",{\"" + uuidKey + "\":[[\"" + joined3 + "\"]]}]";

    ReqMessageRxR decodedReqMessage = new BaseMessageDecoderRxR<ReqMessageRxR>().decode(reqJsonWithCustomTagQueryFilterToDecode);

    AddressTagRxR addressTag00 = new AddressTagRxR();
    addressTag00.setKind(kind1);
    addressTag00.setPublicKey(new PublicKey(author1));
    addressTag00.setIdentifierTag(new IdentifierTag(uuidValue1));

    AddressTagRxR addressTag01 = new AddressTagRxR();
    addressTag01.setKind(kind1);
    addressTag01.setPublicKey(new PublicKey(author1));

    AddressTagRxR addressTag11 = new AddressTagRxR();
    addressTag11.setKind(kind1);
    addressTag11.setPublicKey(new PublicKey(author1));
    addressTag11.setRelay(new Relay(relay));
    
    AddressTagRxR addressTag21 = new AddressTagRxR();
    addressTag21.setKind(kind1);
    addressTag21.setPublicKey(new PublicKey(author1));
    addressTag21.setIdentifierTag(new IdentifierTag(uuidValue1));
    addressTag21.setRelay(new Relay(relay));

    AddressTagRxR addressTag22 = new AddressTagRxR();
    addressTag22.setKind(kind2);
    addressTag22.setPublicKey(new PublicKey(author2));
    addressTag22.setIdentifierTag(new IdentifierTag(uuidValue2));
    addressTag22.setRelay(new Relay(relay));

    ReqMessageRxR expectedReqMessage = new ReqMessageRxR(subscriptionId,
        new FiltersRxR(
            new AddressTagFilterRxR<>(addressTag00),
            new AddressTagFilterRxR<>(addressTag01),
            new AddressTagFilterRxR<>(addressTag11),
            new AddressTagFilterRxR<>(addressTag21),
            new AddressTagFilterRxR<>(addressTag22))
    );

    assertEquals(expectedReqMessage.encode(), decodedReqMessage.encode());
    assertEquals(expectedReqMessage, decodedReqMessage);
  }
}
