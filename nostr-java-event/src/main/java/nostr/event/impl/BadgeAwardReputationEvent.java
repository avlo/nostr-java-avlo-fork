package nostr.event.impl;

import java.net.URI;
import lombok.NonNull;
import nostr.base.PublicKey;
import nostr.base.annotation.Event;

@Event(name = "BadgeAwardReputationEvent")
public class BadgeAwardReputationEvent extends AbstractBadgeAwardEvent {
    public BadgeAwardReputationEvent(
        @NonNull PublicKey badgeCreatorPubkey,
        @NonNull PublicKey badgeReceiverPubkey,
        @NonNull URI uri) {
        super(badgeCreatorPubkey, badgeReceiverPubkey, Type.REPUTATION, uri);
    }
}
