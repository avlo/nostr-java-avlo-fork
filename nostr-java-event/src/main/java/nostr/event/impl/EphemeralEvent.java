package nostr.event.impl;

import lombok.EqualsAndHashCode;
import nostr.base.PublicKey;
import nostr.base.annotation.Event;
import nostr.event.BaseTag;
import nostr.event.Kind;
import nostr.event.tag.PubKeyTag;

import java.util.ArrayList;
import java.util.List;

/**
 * @author squirrel
 */
@EqualsAndHashCode(callSuper = false)
@Event(name = "Ephemeral Events", nip = 16)
public class EphemeralEvent extends EventDecorator {
  private final GenericEvent genericEvent;

  public EphemeralEvent(GenericEvent genericEvent, Integer kind, List<BaseTag> tags, String content) {
    super(genericEvent);
    this.genericEvent = genericEvent;
    setKind(Kind.valueOf(kind));
    setTags(tags);
    setContent(content);
  }

  public EphemeralEvent(GenericEvent genericEvent, Integer kind, List<BaseTag> tags) {
    this(genericEvent, kind, tags, "...");
  }

  public EphemeralEvent(GenericEvent genericEvent, Integer kind, PublicKey recipient) {
    this(genericEvent, kind, new ArrayList<>());
    this.addTag(PubKeyTag.builder().publicKey(recipient).build());
  }

  public void update() {
    // TODO: below worth revisit
    ((GenericEventImpl) genericEvent).update();
  }

  // TODO - Validate the kind.
}
