package nostr.event.impl;

import lombok.EqualsAndHashCode;
import nostr.base.annotation.Event;

/**
 * @author eric
 */
@EqualsAndHashCode(callSuper = false)
@Event(name = "", nip = 15)
public class CreateOrUpdateProductEvent extends EventDecorator {
  private final GenericEvent genericEvent;

  public CreateOrUpdateProductEvent(GenericEvent genericEvent) {
    super(genericEvent);
    this.genericEvent = genericEvent;
  }
}
