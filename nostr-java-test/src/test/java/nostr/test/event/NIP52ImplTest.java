package nostr.test.event;

import nostr.api.NIP52;
import nostr.base.PublicKey;
import nostr.event.BaseTag;
import nostr.event.impl.CalendarContent;
import nostr.event.impl.CalendarTimeBasedEvent;
import nostr.event.tag.GeohashTag;
import nostr.event.tag.HashtagTag;
import nostr.event.tag.IdentifierTag;
import nostr.event.tag.PubKeyTag;
import nostr.event.tag.SubjectTag;
import nostr.id.Identity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NIP52ImplTest {
  public static final String TIME_BASED_EVENT_CONTENT = "CalendarTimeBasedEvent unit test content";
  public static final String TIME_BASED_TITLE = "CalendarTimeBasedEvent title";
  public static final String CALENDAR_TIME_BASED_EVENT_SUMMARY = "Calendar Time-Based Event listing summary";
  public static final String CALENDAR_TIME_BASED_EVENT_START_TZID = "1687765220";
  public static final Long START = 1716513986268L;
  public static CalendarContent timeBasedCalendarContent;
  public static Identity timeBasedSender;
  public static NIP52<CalendarTimeBasedEvent> calendarTimeBasedEventNIP52;
  public static final String CALENDAR_TIME_BASED_EVENT_LOCATION = "Calendar Time-Based Event location";

  // optional fields
  public static final String PTAG_1_HEX = "2bed79f81439ff794cf5ac5f7bff9121e257f399829e472c7a14d3e86fe76985";
  public static final PubKeyTag P_1_TAG = new PubKeyTag(new PublicKey(PTAG_1_HEX), null, "ISSUER");
  public static final String PTAG_2_HEX = "494001ac0c8af2a10f60f23538e5b35d3cdacb8e1cc956fe7a16dfa5cbfc4347";
  public static final PubKeyTag P_2_TAG = new PubKeyTag(new PublicKey(PTAG_2_HEX), null, "COUNTERPARTY");

  public static final String SUBJECT = "Calendar Time-Based Event Test Subject Tag";
  public static final SubjectTag SUBJECT_TAG = new SubjectTag(SUBJECT);
  public static final GeohashTag G_TAG = new GeohashTag("Calendar Time-Based Event Test Geohash Tag");
  public static final HashtagTag T_TAG = new HashtagTag("Calendar Time-Based Event Test Hashtag Tag");

  public static final IdentifierTag identifierTag = new IdentifierTag("UUID-CalendarTimeBasedEventTest");

  @BeforeAll
  static void setup() {
    timeBasedCalendarContent = new CalendarContent(identifierTag, TIME_BASED_TITLE, START);
    timeBasedCalendarContent.setParticipantPubKeys(List.of(P_1_TAG, P_2_TAG));
    timeBasedCalendarContent.setGeohashTag(G_TAG);
    timeBasedCalendarContent.setHashtagTags(List.of(T_TAG));
    timeBasedCalendarContent.setStartTzid(CALENDAR_TIME_BASED_EVENT_START_TZID);
    timeBasedCalendarContent.setEndTzid(START.toString());
    Long l = START + 100L;
    timeBasedCalendarContent.setEndTzid(l.toString());
    timeBasedCalendarContent.setSummary(CALENDAR_TIME_BASED_EVENT_SUMMARY);
    timeBasedCalendarContent.setLocation(CALENDAR_TIME_BASED_EVENT_LOCATION);
    timeBasedSender = Identity.generateRandomIdentity();
    calendarTimeBasedEventNIP52 = new NIP52<>(timeBasedSender);
  }

  @Test
  void testNIP52CreateTimeBasedCalendarCalendarEventWithAllOptionalParameters() {
    List<BaseTag> tags = new ArrayList<>();
    tags.add(SUBJECT_TAG);
    CalendarTimeBasedEvent calendarTimeBasedEvent = calendarTimeBasedEventNIP52.createCalendarTimeBasedEvent(tags, TIME_BASED_EVENT_CONTENT, timeBasedCalendarContent).getEvent();
    calendarTimeBasedEvent.update();

    assertNotNull(calendarTimeBasedEvent.getId());
    assertNull(calendarTimeBasedEvent.getCalendarContent().getId());
    assertTrue(calendarTimeBasedEvent.getTags().contains(SUBJECT_TAG));
    assertEquals(TIME_BASED_TITLE, calendarTimeBasedEvent.getCalendarContent().getTitle());
    assertEquals(CALENDAR_TIME_BASED_EVENT_SUMMARY, calendarTimeBasedEvent.getCalendarContent().getSummary());
    assertEquals(START, calendarTimeBasedEvent.getCalendarContent().getStart());
    List<PubKeyTag> participantPubKeys = calendarTimeBasedEvent.getCalendarContent().getParticipantPubKeys();
    assertEquals(2, participantPubKeys.size());
    assertTrue(participantPubKeys.contains(P_1_TAG));
    assertTrue(participantPubKeys.contains(P_2_TAG));
    assertEquals(G_TAG, calendarTimeBasedEvent.getCalendarContent().getGeohashTag());
    assertTrue(calendarTimeBasedEvent.getCalendarContent().getHashtagTags().contains(T_TAG));
    assertEquals(identifierTag, calendarTimeBasedEvent.getCalendarContent().getIdentifierTag());
    assertEquals(CALENDAR_TIME_BASED_EVENT_LOCATION, calendarTimeBasedEvent.getCalendarContent().getLocation());

    CalendarContent calendarContent = new CalendarContent(
        identifierTag,
        TIME_BASED_TITLE,
        START
    );
    calendarContent.setLocation(CALENDAR_TIME_BASED_EVENT_LOCATION);
    CalendarTimeBasedEvent instance2 = calendarTimeBasedEventNIP52.createCalendarTimeBasedEvent(tags, TIME_BASED_EVENT_CONTENT, timeBasedCalendarContent).getEvent();
    calendarTimeBasedEvent.update();

    assertEquals(calendarTimeBasedEvent, instance2);
    assertEquals(calendarTimeBasedEvent.getCalendarContent(), instance2.getCalendarContent());
  }

  @Test
  void testNIP99CreateClassifiedListingEventNullParameters() {
    System.out.println("testNIP99CreateClassifiedListingEventNullParameters");
    assertThrows(NullPointerException.class, () -> new CalendarContent(TIME_BASED_TITLE, null, START));
    assertThrows(NullPointerException.class, () -> new CalendarContent(TIME_BASED_TITLE, TIME_BASED_TITLE, null));
  }
}
