package nostr.event.impl;

import lombok.NonNull;
import nostr.base.annotation.Event;
import nostr.event.Kind;
import nostr.event.tag.EventTag;

/**
 * @author guilhermegps
 */
@Event(name = "Hide Message on Channel", nip = 28)
public class HideMessageEvent extends EventDecorator {

  public HideMessageEvent(GenericEvent genericEvent, @NonNull ChannelMessageEvent event, String content) {
    super(genericEvent);
    this.setKind(Kind.HIDE_MESSAGE);
    this.setContent(content);
    this.addTag(EventTag.builder().idEvent(event.getId()).build());
  }
}
