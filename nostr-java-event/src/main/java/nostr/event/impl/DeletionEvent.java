
package nostr.event.impl;

import lombok.EqualsAndHashCode;
import nostr.base.annotation.Event;
import nostr.event.BaseTag;
import nostr.event.Kind;

import java.util.List;

/**
 * @author squirrel
 */
@EqualsAndHashCode(callSuper = false)
@Event(name = "Event Deletion", nip = 9)
public class DeletionEvent extends EventDecorator {

  public DeletionEvent(GenericEvent genericEvent, List<BaseTag> tags, String content) {
    super(genericEvent);
    setKind(Kind.DELETION);
    setTags(tags);
    setContent(content);
  }

  public DeletionEvent(GenericEvent genericEvent, List<BaseTag> tags) {
    this(genericEvent, tags, "Deletion request");
  }
}
