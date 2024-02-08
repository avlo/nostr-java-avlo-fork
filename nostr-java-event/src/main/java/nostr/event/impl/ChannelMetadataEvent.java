package nostr.event.impl;

import lombok.Getter;
import lombok.Setter;
import nostr.base.ChannelProfile;
import nostr.base.annotation.Event;
import nostr.event.Kind;

/**
 * @author guilhermegps
 */
@Getter
@Setter
@Event(name = "Channel Metadata", nip = 28)
public class ChannelMetadataEvent extends EventDecorator {
  private final GenericEvent genericEvent;
  private ChannelProfile channelProfile;

  public ChannelMetadataEvent(GenericEvent genericEvent) {
    super(genericEvent);
    this.genericEvent = genericEvent;
    this.genericEvent.setKind(Kind.CHANNEL_METADATA);
  }

//    public ChannelMetadataEvent(@NonNull PublicKey pubKey, @NonNull ChannelCreateEvent event, ChannelProfile profile) {
//        super(pubKey, Kind.CHANNEL_METADATA, new ArrayList<>(), escapeJsonString(profile.toString()));
//        this.addTag(EventTag.builder().idEvent(event.getId()).build());
//    }
//
//    public ChannelMetadataEvent(@NonNull PublicKey pubKey, @NonNull EventTag channelCreateEventTag, ChannelProfile profile) {
//        super(pubKey, Kind.CHANNEL_METADATA, new ArrayList<>(), escapeJsonString(profile.toString()));
//        this.addTag(channelCreateEventTag);
}
