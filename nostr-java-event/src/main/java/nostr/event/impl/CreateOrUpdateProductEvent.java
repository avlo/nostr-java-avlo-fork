package nostr.event.impl;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import nostr.base.PublicKey;
import nostr.base.annotation.Event;
import nostr.event.BaseTag;
import nostr.event.IContent;

import java.util.List;

/**
 * @author eric
 */
@EqualsAndHashCode(callSuper = false)
@Event(name = "", nip = 15)
public class CreateOrUpdateProductEvent extends GenericEventImpl {
  NostrMarketplaceEvent nostrMarketplaceEvent;
  public CreateOrUpdateProductEvent(PublicKey sender, List<BaseTag> tags, @NonNull IContent product) {
    nostrMarketplaceEvent = new NostrMarketplaceEvent(
            new ParameterizedReplaceableEvent(
                new ReplaceableEvent(
                    new GenericEventImpl(sender, 30018, tags, product.toString()))));
  }
}
