package nostr.event.impl;

import lombok.EqualsAndHashCode;
import nostr.base.annotation.Event;
import nostr.event.tag.HashtagTag;
import nostr.event.tag.IdentifierTag;

import java.util.List;

/**
 * @author eric
 */
@EqualsAndHashCode(callSuper = false)
@Event(name = "", nip = 15)
public class CreateOrUpdateProductEvent extends EventDecorator {
  public CreateOrUpdateProductEvent(GenericEvent genericEvent, Product product, List<String> categories) {
    super(genericEvent);
    setContent(product.toString());
    addTag(new IdentifierTag(product.getId()));
    if (categories != null) {
      categories.forEach(c -> addTag(new HashtagTag(c)));
    }
  }
}
