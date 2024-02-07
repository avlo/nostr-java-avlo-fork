package nostr.event.impl;

import lombok.EqualsAndHashCode;
import nostr.base.PublicKey;
import nostr.base.annotation.Event;
import nostr.event.BaseTag;

import java.util.List;

/**
 * @author eric
 */
@EqualsAndHashCode(callSuper = false)
@Event(name = "Parameterized Replaceable Events", nip = 33)
public class ParameterizedReplaceableEvent extends GenericEventImpl {
  public ParameterizedReplaceableEvent(PublicKey sender, Integer kind, List<BaseTag> tags, String content) {
    new ReplaceableEvent(new GenericEventImpl(sender, kind, tags, content), 30_000, 40_000));
  }
}
