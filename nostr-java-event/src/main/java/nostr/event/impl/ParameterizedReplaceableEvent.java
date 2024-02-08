package nostr.event.impl;

import lombok.EqualsAndHashCode;
import nostr.base.annotation.Event;

/**
 * @author eric
 */
@EqualsAndHashCode(callSuper = false)
@Event(name = "Parameterized Replaceable Events", nip = 33)
public class ParameterizedReplaceableEvent extends EventDecorator {
  private final GenericEvent genericEvent;

  public ParameterizedReplaceableEvent(GenericEvent genericEvent) {
    super(genericEvent);
    this.genericEvent = genericEvent;
  }
}
