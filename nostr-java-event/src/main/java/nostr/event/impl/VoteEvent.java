
package nostr.event.impl;

import java.util.List;
import nostr.base.PublicKey;
import nostr.base.annotation.Event;
import nostr.event.BaseTag;
import nostr.event.Kind;
import nostr.event.NIP01Event;

@Event(name = "Vote")
public class VoteEvent extends NIP01Event {

    public VoteEvent(PublicKey pubKey, List<BaseTag> tags, String content) {
        super(pubKey, Kind.VOTE, tags, content);
    }   
}
