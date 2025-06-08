package nostr.event.impl;

import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.net.URI;
import lombok.NonNull;
import nostr.base.PublicKey;
import nostr.base.annotation.Event;

@Event(name = "BadgeAwardReputationEvent")
public class BadgeAwardReputationEvent extends AbstractBadgeAwardEvent {
    public BadgeAwardReputationEvent(
        @NonNull PublicKey badgeCreatorPubkey,
        @NonNull PublicKey badgeReceiverPubkey,
        @NonNull @DecimalMin(value = "0.0") @DecimalMin(value = "1.0") BigDecimal score,
        @NonNull URI uri) {
        super(badgeCreatorPubkey, badgeReceiverPubkey, Type.REPUTATION, score, uri);
    }

    public BigDecimal getScore() {
        return new BigDecimal(super.getContent());
    }
}
