package nostr.event.impl;

import java.net.URI;
import lombok.NonNull;
import nostr.base.PublicKey;
import nostr.base.annotation.Event;

@Event(name = "BadgeAwardUpvoteEvent")
public class BadgeAwardUpvoteEvent extends AbstractBadgeAwardEvent {
    public BadgeAwardUpvoteEvent(
        @NonNull PublicKey badgeCreatorPubkey,
        @NonNull PublicKey badgeReceiverPubkey,
        @NonNull URI uri) {
        super(badgeCreatorPubkey, badgeReceiverPubkey, Type.UPVOTE, uri);
    }
}
