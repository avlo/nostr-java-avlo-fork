package nostr.event.message;

import lombok.ToString;
import nostr.base.PublicKey;
import nostr.event.BaseTag;
import nostr.event.Kind;
import nostr.event.impl.GenericEvent;

import java.util.List;

/**
 * @author squirrel
 */
@ToString
public class ContactListMessage extends EventMessage {

  public ContactListMessage(GenericEvent genericEvent, List<BaseTag> contactList, PublicKey publicKey) {
    super(genericEvent);
    genericEvent.setKind(Kind.CONTACT_LIST);
    genericEvent.setTags(contactList);
  }
}
