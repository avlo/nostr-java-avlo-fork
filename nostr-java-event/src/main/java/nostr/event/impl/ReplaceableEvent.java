package nostr.event.impl;

import lombok.EqualsAndHashCode;
import nostr.base.annotation.Event;
import nostr.event.BaseEvent;

/**
 * @author squirrel
 */
@EqualsAndHashCode(callSuper = false)
@Event(name = "Replaceable Events", nip = 16)
public class ReplaceableEvent implements GenericEvent {
  private final static int MAX = 20000;
  private final static int MIN = 10000;
  private final static String OOB_MESSAGE = "Invalid kind value. Must be between %d and %d (excl)";

  public ReplaceableEvent(GenericEvent genericEvent) throws AssertionError {
    this(new GenericEventImpl()genericEvent, MAX, MIN);
  }

  public ReplaceableEvent(GenericEvent genericEvent, int maxKind, int minKind) throws AssertionError {
//        Preconditions.
//        assertThatThrownBy(() -> Preconditions.checkArgume
    super(sender, kind, tags, content);
  }
}
