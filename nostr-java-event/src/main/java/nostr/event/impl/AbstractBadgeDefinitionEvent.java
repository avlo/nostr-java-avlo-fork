
package nostr.event.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import nostr.base.PublicKey;
import nostr.event.BaseTag;
import nostr.event.Kind;
import nostr.event.NIP01Event;
import nostr.event.tag.IdentifierTag;

public abstract class AbstractBadgeDefinitionEvent extends NIP01Event {
    protected AbstractBadgeDefinitionEvent(
        @NonNull PublicKey pubKey,
        @NonNull AbstractBadgeDefinitionEvent.Type type) {
        super(pubKey, Kind.BADGE_DEFINITION_EVENT, List.of(new IdentifierTag(type.getName())));
    }

    protected AbstractBadgeDefinitionEvent(
        @NonNull PublicKey pubKey,
        @NonNull AbstractBadgeDefinitionEvent.Type type,
        @NonNull List<BaseTag> tags) {
        super(pubKey, Kind.BADGE_DEFINITION_EVENT,
            (List<BaseTag>) Stream.concat(
                Stream.of(Stream.of(new IdentifierTag(type.getName())).toList()),
                Stream.of(tags)).flatMap(Collection::stream).toList(),
            type.getName());
    }

    @AllArgsConstructor
    @Getter
    public enum Type {
        UPVOTE(1, "upvote"),
        DOWNVOTE(-1, "downvote"),
        REPUTATION(0, "reputation");
        

        @JsonValue
        private final int value;

        @JsonValue
        private final String name;

        @JsonCreator
        public static Type valueOf(int v) {
            return Optional.ofNullable(values()[v]).orElseThrow();
        }

        @JsonCreator
        public static Type valueOfString(String v) {
            return Stream.of(values()).filter(e -> e.name.equals(v)).findFirst().orElseThrow();
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
