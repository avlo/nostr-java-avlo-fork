package nostr.event.impl;

import nostr.base.PublicKey;
import nostr.base.annotation.Event;
import nostr.event.BaseTag;
import nostr.event.Kind;
import nostr.event.tag.PubKeyTag;

import java.util.List;

/**
 * @author squirrel
 */
@Event(name = "Encrypted Direct Message", nip = 4)
public class DirectMessageEvent extends EventDecorator {
  public DirectMessageEvent(GenericEvent genericEvent) {
    super(genericEvent);
    setKind(Kind.ENCRYPTED_DIRECT_MESSAGE);
  }

  public DirectMessageEvent(GenericEvent genericEvent, List<BaseTag> tags, String content) {
    super(genericEvent);
    setKind(Kind.ENCRYPTED_DIRECT_MESSAGE);
    setTags(tags);
    setContent(content);
  }

  public DirectMessageEvent(GenericEvent genericEvent, PublicKey recipient, String content) {
    super(genericEvent);
    setKind(Kind.ENCRYPTED_DIRECT_MESSAGE);
    setContent(content);
    addTag(PubKeyTag.builder().publicKey(recipient).build());
  }
}
