package nostr.event.impl;

import java.net.URI;
import lombok.NonNull;
import nostr.base.PublicKey;
import nostr.base.annotation.Event;

@Event(name = "BadgeAwardDownvoteEvent")
public class BadgeAwardDownvoteEvent extends AbstractBadgeAwardEvent {
    public BadgeAwardDownvoteEvent(
        @NonNull PublicKey badgeCreatorPubkey,
        @NonNull PublicKey badgeReceiverPubkey,
        @NonNull URI uri) {
        super(badgeCreatorPubkey, badgeReceiverPubkey, Type.DOWNVOTE, uri);
    }
}
