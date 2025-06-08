package nostr.event.impl;

import lombok.NonNull;
import nostr.base.PublicKey;
import nostr.base.annotation.Event;

@Event(name = "BadgeDefinitionDownVoteEvent")
public class BadgeDefinitionDownVoteEvent extends AbstractBadgeDefinitionEvent {
    public BadgeDefinitionDownVoteEvent(@NonNull PublicKey pubKey) {
        super(pubKey, Type.DOWNVOTE);
    }

//    public BadgeDefinitionDownVoteEvent(@NonNull PublicKey pubKey, @NonNull List<BaseTag> tags) {
//        super(pubKey, VoteValue.DOWNVOTE, tags);
//    }
}
