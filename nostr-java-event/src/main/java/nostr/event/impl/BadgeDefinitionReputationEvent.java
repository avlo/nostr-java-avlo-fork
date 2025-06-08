package nostr.event.impl;

import lombok.NonNull;
import nostr.base.PublicKey;
import nostr.base.annotation.Event;

@Event(name = "BadgeDefinitionReputationEvent")
public class BadgeDefinitionReputationEvent extends AbstractBadgeDefinitionEvent {
    public BadgeDefinitionReputationEvent(@NonNull PublicKey pubKey) {
        super(pubKey, Type.REPUTATION);
    }

//    public BadgeDefinitionUpvoteEvent(@NonNull PublicKey pubKey, @NonNull List<BaseTag> tags) {
//        super(pubKey, VoteValue.UPVOTE, tags);
//    }
}
