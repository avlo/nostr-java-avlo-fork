package nostr.event.impl;

import java.util.List;
import nostr.event.tag.PubKeyTag;
import nostr.event.Kind;
import nostr.base.PublicKey;
import lombok.Data;
import lombok.EqualsAndHashCode;
import nostr.base.ITag;
import nostr.base.annotation.Event;
import nostr.event.BaseTag;

/**
 *
 * @author squirrel
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Event(name = "Handling Mentions", nip = 8)
public final class MentionsEvent extends GenericEventImpl {

    public MentionsEvent(PublicKey pubKey, List<BaseTag> tags, String content) {
        super(pubKey, Kind.TEXT_NOTE, tags, content);
    }

    @Override
    public void update() {
        super.update();

        int index = 0;

        // TODO - Refactor with the EntityAttributeUtil class
        while (getTags().iterator().hasNext()) {
            ITag tag = getTags().iterator().next();
            String replacement = "#[" + index++ + "]";
            setContent(this.getContent().replace(((PubKeyTag) tag).getPublicKey().toString(), replacement));
        }
    }
}
