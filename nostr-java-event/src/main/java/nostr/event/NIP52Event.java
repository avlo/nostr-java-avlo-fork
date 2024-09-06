package nostr.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.NonNull;
import lombok.SneakyThrows;
import nostr.base.PublicKey;
import nostr.event.impl.CalendarContent;
import nostr.event.impl.GenericEvent;
import nostr.event.impl.GenericTag;

import java.util.List;
import java.util.Optional;

public abstract class NIP52Event extends GenericEvent {
  @JsonIgnore
  private final CalendarContent calendarContent;

  public NIP52Event(@NonNull PublicKey pubKey, @NonNull Kind kind, @NonNull List<BaseTag> baseTags, @NonNull String content, @NonNull CalendarContent calendarContent) {
    super(pubKey, kind, baseTags, content);
    this.calendarContent = calendarContent;
    appendTags();
  }

  private void appendTags() {
    addStandardTag(calendarContent.getIdentifierTag());
    addGenericTag("id", calendarContent.getId());
    addGenericTag("title", calendarContent.getTitle());
    addGenericTag("start", calendarContent.getStart());
    addGenericTag("end", calendarContent.getEnd());
    addGenericTag("start_tzid", calendarContent.getStartTzid());
    addGenericTag("end_tzid", calendarContent.getEndTzid());
    addGenericTag("summary", calendarContent.getSummary());
    addGenericTag("image", calendarContent.getImage());
    addGenericTag("location", calendarContent.getLocation());
    addStandardTag(calendarContent.getGeohashTag());
    addStandardTag(calendarContent.getParticipantPubKeys());
    addStringListTag("l", calendarContent.getLabels());
    addStandardTag(calendarContent.getHashtagTags());
    addStandardTag(calendarContent.getReferenceTags());
  }

  // TODO: possibly refactor below into parent/hierarchy, take a look at Product/Classifieds first.
  private <T extends BaseTag> void addStandardTag(List<T> tag) {
    Optional.ofNullable(tag).ifPresent(tagList -> tagList.forEach(this::addStandardTag));
  }

  private void addStandardTag(BaseTag tag) {
    Optional.ofNullable(tag).ifPresent(this::addTag);
  }

  private void addGenericTag(String key, Object value) {
    Optional.ofNullable(value).ifPresent(s -> addTag(GenericTag.create(key, 52, s.toString())));
  }

  private void addStringListTag(String label, List<String> tag) {
    Optional.ofNullable(tag).ifPresent(tagList -> addGenericTag(label, tagList));
  }

  //  @JsonValue
//  public String json() throws JsonProcessingException {
//    JsonNode calendarNode = new ObjectMapper().valueToTree(calendarContent);
//    ArrayNode tags = new ObjectMapper().valueToTree(getTags());
//    calendarNode.fields().forEachRemaining(cal -> {
//      ArrayNode newArray = new ObjectMapper().createArrayNode();
//      newArray.add(cal.getKey());
//      newArray.add(cal.getValue());
//      tags.add(newArray);
//    });
//    return new ObjectMapper().writeValueAsString(tags);
//  }

//  @SneakyThrows
//  @Override
//  public String toString() {
//    ArrayNode root = new ObjectMapper().valueToTree(this);
//    JsonNode rootNode = new ObjectMapper().valueToTree(this);
//
//    JsonNode calendarNode = new ObjectMapper().valueToTree(calendarContent);
//    ArrayNode tags = new ObjectMapper().valueToTree(getTags());
//    calendarNode.fields().forEachRemaining(cal -> {
//      ArrayNode newArray = new ObjectMapper().createArrayNode();
//      newArray.add(cal.getKey());
//      newArray.add(cal.getValue());
//      tags.add(newArray);
//    });
//    return new ObjectMapper().writeValueAsString(tags);
//  }
}
