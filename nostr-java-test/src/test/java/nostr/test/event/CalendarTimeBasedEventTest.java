package nostr.test.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nostr.api.NIP52;
import nostr.base.PublicKey;
import nostr.base.Signature;
import nostr.event.BaseTag;
import nostr.event.NIP52Event;
import nostr.event.impl.CalendarContent;
import nostr.event.impl.GenericEvent;
import nostr.event.impl.GenericTag;
import nostr.event.json.codec.BaseEventEncoder;
import nostr.event.tag.GeohashTag;
import nostr.event.tag.HashtagTag;
import nostr.event.tag.IdentifierTag;
import nostr.event.tag.PubKeyTag;
import nostr.event.tag.ReferenceTag;
import nostr.event.tag.SubjectTag;
import nostr.id.Identity;
import nostr.test.util.ComparatorWithoutOrder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CalendarTimeBasedEventTest {
  // required fields
  public static final Identity identity = Identity.generateRandomIdentity();
  public static final PublicKey senderPubkey = new PublicKey(identity.getPublicKey().toString());

  public static final String CALENDAR_TIME_BASED_EVENT_TITLE = "Calendar Time-Based Event title";
  public static final String CALENDAR_TIME_BASED_EVENT_CONTENT = "calendar Time-Based Event content";
  public static final IdentifierTag identifierTag = new IdentifierTag("UUID-CalendarTimeBasedEventTest");
  public static final Long START = 1716513986268L;

  // optional fields
  public static final String str = "http://some.url";
  public static final String PTAG_1_HEX = "2bed79f81439ff794cf5ac5f7bff9121e257f399829e472c7a14d3e86fe76985";
  public static final PubKeyTag P_1_TAG = new PubKeyTag(new PublicKey(PTAG_1_HEX), str, "ISSUER");
  public static final String PTAG_2_HEX = "494001ac0c8af2a10f60f23538e5b35d3cdacb8e1cc956fe7a16dfa5cbfc4347";
  public static final PubKeyTag P_2_TAG = new PubKeyTag(new PublicKey(PTAG_2_HEX), str, "COUNTERPARTY");

  public static final String SUBJECT = "Calendar Time-Based Event Test Subject Tag";
  public static final SubjectTag SUBJECT_TAG = new SubjectTag(SUBJECT);
  public static final GeohashTag G_TAG = new GeohashTag("Calendar Time-Based Event Test Geohash Tag");
  public static final HashtagTag T_TAG = new HashtagTag("Calendar Time-Based Event Test Hashtag Tag");

  public static final String CALENDAR_TIME_BASED_EVENT_SUMMARY = "Calendar Time-Based Event summary";
  public static final String CALENDAR_TIME_BASED_EVENT_START_TZID = "1687765220";
  public static final String CALENDAR_TIME_BASED_EVENT_END_TZID = "1687765220";
  public static final String CALENDAR_TIME_BASED_EVENT_LOCATION = "Calendar Time-Based Event location";

  // keys
  public static final String START_TZID_CODE = "start_tzid";
  public static final String END_CODE = "end";
  public static final String LOCATION_CODE = "location";

  private NIP52Event instance;
  String expectedEncodedJson;
  Signature signature;

  @BeforeAll
  void setup() throws URISyntaxException {
    // a random set of base tags
    List<BaseTag> tags = new ArrayList<>();
    tags.add(P_1_TAG);
    tags.add(P_2_TAG);
    tags.add(GenericTag.create(LOCATION_CODE, 52, CALENDAR_TIME_BASED_EVENT_LOCATION));
    tags.add(SUBJECT_TAG);
    tags.add(G_TAG);
    tags.add(T_TAG);
    tags.add(GenericTag.create(START_TZID_CODE, 52, CALENDAR_TIME_BASED_EVENT_START_TZID));
    Long l = START + 100L;
    tags.add(GenericTag.create(END_CODE, 52, l.toString()));

    CalendarContent calendarContent = CalendarContent.builder(identifierTag, CALENDAR_TIME_BASED_EVENT_TITLE, START).build();
    // a random set of calendar tags
    calendarContent.setEndTzid(CALENDAR_TIME_BASED_EVENT_END_TZID);
    calendarContent.setSummary(CALENDAR_TIME_BASED_EVENT_SUMMARY);
    URI uri = new URI(str);
    calendarContent.setReferenceTags(List.of(new ReferenceTag(uri)));

    instance = new NIP52<>(identity).createCalendarTimeBasedEvent(tags, CALENDAR_TIME_BASED_EVENT_CONTENT, calendarContent).getEvent();
    signature = identity.sign(instance);
    instance.setSignature(signature);

    expectedEncodedJson = "{\"id\":\"" + instance.getId() + "\",\"kind\":31923,\"content\":\"calendar Time-Based Event content\",\"pubkey\":\"" + senderPubkey + "\",\"created_at\":" + instance.getCreatedAt() + ",\"tags\":[[\"p\",\"2bed79f81439ff794cf5ac5f7bff9121e257f399829e472c7a14d3e86fe76985\",\"http://some.url\",\"ISSUER\"],[\"p\",\"494001ac0c8af2a10f60f23538e5b35d3cdacb8e1cc956fe7a16dfa5cbfc4347\",\"http://some.url\",\"COUNTERPARTY\"],[\"location\",\"Calendar Time-Based Event location\"],[\"subject\",\"Calendar Time-Based Event Test Subject Tag\"],[\"g\",\"Calendar Time-Based Event Test Geohash Tag\"],[\"t\",\"Calendar Time-Based Event Test Hashtag Tag\"],[\"start_tzid\",\"1687765220\"],[\"end\",\"1716513986368\"],[\"d\",\"UUID-CalendarTimeBasedEventTest\"],[\"title\",\"Calendar Time-Based Event title\"],[\"start\",\"1716513986268\"],[\"end_tzid\",\"1687765220\"],[\"summary\",\"Calendar Time-Based Event summary\"],[\"r\",\"http://some.url\"]],\"sig\":\"" + signature.toString() + "\"}";
  }

  @Test
  void testCalendarTimeBasedEventEncoding() throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();

    assertTrue(
        ComparatorWithoutOrder.isEquivalentJson(
            mapper.readTree(new BaseEventEncoder<>(instance).encode()),
            mapper.readTree(expectedEncodedJson)));
  }

  @Test
  void testCalendarTimeBasedEventDecoding() throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();

    assertTrue(
        ComparatorWithoutOrder.isEquivalentJson(
            mapper.readTree(
                new BaseEventEncoder<>(
                    new ObjectMapper()
                        .readValue(
                            expectedEncodedJson,
                            GenericEvent.class))
                    .encode()),
            mapper.readTree(new BaseEventEncoder<>(instance).encode())));
  }
}