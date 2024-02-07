
package nostr.event.impl;

import java.util.List;
import nostr.base.PublicKey;
import nostr.event.Kind;
import nostr.base.annotation.Event;
import nostr.event.BaseTag;

/**
 *
 * @author squirrel
 */
@Event(name = "Text Note")
public class TextNoteEvent extends GenericEventImpl {

    public TextNoteEvent(PublicKey pubKey, List<BaseTag> tags, String content) {
        super(pubKey, Kind.TEXT_NOTE, tags, content);
    }   
}
