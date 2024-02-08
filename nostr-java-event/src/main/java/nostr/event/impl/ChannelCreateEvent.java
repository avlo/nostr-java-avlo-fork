package nostr.event.impl;

import lombok.EqualsAndHashCode;
import nostr.base.annotation.Event;

/**
 * @author guilhermegps
 */
@EqualsAndHashCode(callSuper = false)
@Event(name = "Create Channel", nip = 28)
public class ChannelCreateEvent extends EventDecorator {
  private final GenericEvent genericEvent;

  public ChannelCreateEvent(GenericEvent genericEvent) {
    super(genericEvent);
    this.genericEvent = genericEvent;
  }
//        super(pubKey, Kind.CHANNEL_CREATE, new ArrayList<>(), escapeJsonString(profile.toString()));
}
