package nostr.event.impl;

import com.fasterxml.jackson.annotation.JsonValue;
import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import nostr.base.PublicKey;
import nostr.event.Kind;
import nostr.event.NIP01Event;
import nostr.event.tag.AddressTag;
import nostr.event.tag.IdentifierTag;
import nostr.event.tag.PubKeyTag;

public abstract class AbstractBadgeAwardEvent extends NIP01Event {
    protected AbstractBadgeAwardEvent(
        @NonNull PublicKey badgeCreatorPubkey,
        @NonNull PublicKey badgeReceiverPubkey,
        @NonNull Type type,
        @NonNull URI uri) {
        super(badgeCreatorPubkey, Kind.BADGE_AWARD_EVENT,
            List.of(
                new AddressTag(
                    Kind.BADGE_AWARD_EVENT.getValue(),
                    badgeCreatorPubkey,
                    new IdentifierTag(
                        type.getName())),
                new PubKeyTag(
                    badgeReceiverPubkey,
                    uri.toString())),
            type.getName());
    }

    protected AbstractBadgeAwardEvent(
        @NonNull PublicKey badgeCreatorPubkey,
        @NonNull PublicKey badgeReceiverPubkey,
        @NonNull Type type,
        @NonNull
//        @DecimalMin(value = "0.0") @DecimalMax(value = "1.0")  <--- did not work, had to use record below instead 
        BigDecimal score,
        @NonNull URI uri) {
        super(badgeCreatorPubkey, Kind.BADGE_AWARD_EVENT,
            List.of(
                new AddressTag(
                    Kind.BADGE_AWARD_EVENT.getValue(),
                    badgeCreatorPubkey,
                    new IdentifierTag(
                        type.getName())),
                new PubKeyTag(
                    badgeReceiverPubkey,
                    uri.toString())),
            new ValidScore(score).score().toString());
    }

    @AllArgsConstructor
    @Getter
    public enum Type {
        UPVOTE("upvote"),
        DOWNVOTE("downvote"),
        REPUTATION("reputation");

        @JsonValue
        private final String name;

        @Override
        public String toString() {
            return name;
        }
    }

    public record ValidScore(BigDecimal score) {
        public ValidScore(BigDecimal score) {
            String errorMessage = String.format("score must be [0.0 - 1.0], was [%s]", score.toString());
            assert BigDecimal.ZERO.compareTo(score) <= 0 : errorMessage;
            assert BigDecimal.ONE.compareTo(score) >= 0 : errorMessage;
            this.score = score;
        }
    }
}
