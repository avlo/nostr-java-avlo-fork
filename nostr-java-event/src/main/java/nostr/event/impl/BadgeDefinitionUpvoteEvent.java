package nostr.event.impl;

import lombok.NonNull;
import nostr.base.PublicKey;
import nostr.base.annotation.Event;

@Event(name = "BadgeDefinitionUpvoteEvent")
public class BadgeDefinitionUpvoteEvent extends AbstractBadgeDefinitionEvent {
    public BadgeDefinitionUpvoteEvent(@NonNull PublicKey pubKey) {
        super(pubKey, Type.UPVOTE);
    }

//    public BadgeDefinitionUpvoteEvent(@NonNull PublicKey pubKey, @NonNull List<BaseTag> tags) {
//        super(pubKey, VoteValue.UPVOTE, tags);
//    }
}
