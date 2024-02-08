package nostr.event.impl;

import lombok.EqualsAndHashCode;
import nostr.base.annotation.Event;

/**
 * @author squirrel
 */
@EqualsAndHashCode(callSuper = false)
@Event(name = "Replaceable Events", nip = 16)
public class ReplaceableEvent extends EventDecorator {
  private final GenericEvent genericEvent;
  private final static int MAX = 20000;
  private final static int MIN = 10000;
  private final static String OOB_MESSAGE = "Invalid kind value. Must be between %d and %d (excl)";

  public ReplaceableEvent(GenericEvent genericEvent) throws AssertionError {
    super(genericEvent);
    this.genericEvent = genericEvent;
  }
}
