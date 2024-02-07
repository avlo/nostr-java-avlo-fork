package nostr.event.impl;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import nostr.base.IEvent;
import nostr.base.PublicKey;
import nostr.base.annotation.Event;
import nostr.event.AbstractEventContent;
import nostr.event.BaseTag;
import nostr.event.IContent;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Event(name = "ClassifiedListingEvent", nip = 99)
public class ClassifiedListingEvent extends ParameterizedReplaceableEvent {
    protected ClassifiedListingEvent() {
        super();
    }
    public ClassifiedListingEvent(PublicKey sender, Integer kind, List<BaseTag> tags, IContent content) {
        super(sender, kind, tags, content.toString());
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
