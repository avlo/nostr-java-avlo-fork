package nostr.event.impl;

import lombok.Getter;
import lombok.Setter;
import nostr.base.PublicKey;
import nostr.base.annotation.Event;
import nostr.event.BaseTag;
import nostr.event.Kind;
import nostr.event.tag.PubKeyTag;

import java.util.List;

/**
 * @author squirrel
 */
@Getter
@Setter
@Event(name = "Encrypted Direct Message", nip = 4)
public class DirectMessageEvent extends EventDecorator {
  private final GenericEvent genericEvent;
  private PublicKey recipientPublicKey;

  public DirectMessageEvent(GenericEvent genericEvent) {
    super(genericEvent);
    this.genericEvent = genericEvent;
    this.genericEvent.setKind(Kind.ENCRYPTED_DIRECT_MESSAGE);
  }

//  public DirectMessageEvent(PublicKey sender, List<BaseTag> tags, String content) {
//    super(sender, Kind.ENCRYPTED_DIRECT_MESSAGE, tags, content);
//  }
//
//  public DirectMessageEvent(PublicKey sender, PublicKey recipient, String content) {
//    super(sender, Kind.ENCRYPTED_DIRECT_MESSAGE);
//    this.setContent(content);
//    this.addTag(PubKeyTag.builder().publicKey(recipient).build());
//  }
}
