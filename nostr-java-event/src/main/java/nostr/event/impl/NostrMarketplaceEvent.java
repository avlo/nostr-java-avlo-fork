package nostr.event.impl;

import lombok.*;
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
public class NostrMarketplaceEvent extends GenericEventImpl {
  public NostrMarketplaceEvent(PublicKey sender, Integer kind, List<BaseTag> tags, IContent content) {
    super(new ParameterizedReplaceableEvent(sender, kind, tags, content.toString()));
  }
}
