package nostr.event.impl;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import nostr.base.annotation.Event;
import nostr.event.AbstractEventContent;

import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Event(name = "ClassifiedListingEvent", nip = 99)
public class ClassifiedListingEvent extends EventDecorator {
  private final GenericEvent genericEvent;

  protected ClassifiedListingEvent(GenericEvent genericEvent) {
    super(genericEvent);
    this.genericEvent = genericEvent;
  }

  @Getter
  @Setter
  @EqualsAndHashCode(callSuper = false)
  public static class ClassifiedListing extends AbstractEventContent<NostrMarketplaceEvent> {
    private final Integer nip = 99;
    private String id;
    private boolean active;
    private List<String> hashTags;
    private String title;
    private String summary;
    private String location;
    private List<String> images;
    private String currency;
    private Float price;
  }
}
