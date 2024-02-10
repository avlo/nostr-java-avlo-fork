package nostr.event.impl;

import lombok.EqualsAndHashCode;
import nostr.base.annotation.Event;
import nostr.event.Kind;

/**
 * @author squirrel
 */
@EqualsAndHashCode(callSuper = false)
@Event(name = "Replaceable Events", nip = 16)
public class ReplaceableEvent extends EventDecorator implements ValidatableEvent {
  private final int minKind;
  private final int maxKind;
  private final static String OOB_MESSAGE = "Invalid kind value. Must be between %d and %d (excl)";

  public ReplaceableEvent(GenericEvent genericEvent, Kind kind) throws AssertionError {
    this(genericEvent, kind, 10_000, 20_000);
  }

  public ReplaceableEvent(GenericEvent genericEvent, Kind kind, int minKind, int maxKind) throws AssertionError {
    super(genericEvent);
    setKind(kind);
    this.minKind = minKind;
    this.maxKind = maxKind;
  }

  @Override
  public void validate() {
    if (this.getKind().getValue() <= this.minKind || this.getKind().getValue() > this.maxKind) {
      throw new AssertionError(String.format(OOB_MESSAGE, this.minKind, this.maxKind), null);
    }
  }
}
