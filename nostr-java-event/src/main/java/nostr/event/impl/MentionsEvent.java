package nostr.event.impl;

import lombok.EqualsAndHashCode;
import nostr.base.ITag;
import nostr.base.annotation.Event;
import nostr.event.Kind;
import nostr.event.tag.PubKeyTag;

/**
 * @author squirrel
 */
@EqualsAndHashCode(callSuper = false)
@Event(name = "Handling Mentions", nip = 8)
public final class MentionsEvent extends EventDecorator implements UpdatableEvent {
  private final GenericEvent genericEvent;

  public MentionsEvent(GenericEvent genericEvent) {
    super(genericEvent);
    this.genericEvent = genericEvent;
    this.genericEvent.setPubKey(getPubKey());
    this.genericEvent.setKind(Kind.TEXT_NOTE);
  }

  @Override
  public void update() {
    int index = 0;
    // TODO - Refactor with the EntityAttributeUtil class
    while (getTags().iterator().hasNext()) {
      ITag tag = getTags().iterator().next();
      String replacement = "#[" + index++ + "]";
      setContent(this.getContent().replace(((PubKeyTag) tag).getPublicKey().toString(), replacement));
    }
  }
}
