package nostr.event.impl;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import nostr.base.annotation.Event;
import nostr.event.BaseTag;
import nostr.event.Kind;

import java.util.List;

/**
 * @author eric
 */
@EqualsAndHashCode(callSuper = false)
@Event(name = "Contact List and Petnames", nip = 2)
public class ContactListEvent extends EventDecorator {

  public ContactListEvent(GenericEvent genericEvent, @NonNull List<BaseTag> tags) {
    super(genericEvent);
    this.setKind(Kind.CONTACT_LIST);
    this.setTags(tags);
  }
}
