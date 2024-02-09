
package nostr.event.impl;

import nostr.base.annotation.Event;
import nostr.event.BaseTag;
import nostr.event.Kind;

import java.util.List;

/**
 * @author squirrel
 */
@Event(name = "Text Note")
public class TextNoteEvent extends EventDecorator {
  private final GenericEvent genericEvent;
  public TextNoteEvent(GenericEvent genericEvent, List<BaseTag> tags, String content) {
    super(genericEvent);
    this.genericEvent = genericEvent;
    setKind(Kind.TEXT_NOTE);
    setTags(tags);
    setContent(content);
  }

  public void update() {
    // TODO: below worth revisit
    ((GenericEventImpl)genericEvent).update();
  }
}
