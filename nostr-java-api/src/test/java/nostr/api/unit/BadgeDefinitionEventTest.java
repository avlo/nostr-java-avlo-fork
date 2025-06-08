package nostr.api.unit;

import java.util.List;
import nostr.base.PublicKey;
import nostr.event.filter.Filterable;
import nostr.event.impl.AbstractBadgeDefinitionEvent;
import nostr.event.impl.BadgeDefinitionDownVoteEvent;
import nostr.event.impl.BadgeDefinitionReputationEvent;
import nostr.event.impl.BadgeDefinitionUpvoteEvent;
import nostr.event.tag.IdentifierTag;
import nostr.id.Identity;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BadgeDefinitionEventTest {
    public static final Identity identity = Identity.generateRandomIdentity();
    public static final PublicKey senderPubkey = new PublicKey(identity.getPublicKey().toString());

    @Test
    void badgeDefinitionUpvoteEventTest() {
        BadgeDefinitionUpvoteEvent upvoteDefnEvent = new BadgeDefinitionUpvoteEvent(senderPubkey);
        List<IdentifierTag> typeSpecificTags = Filterable.getTypeSpecificTags(IdentifierTag.class, upvoteDefnEvent);
        assertEquals(1, typeSpecificTags.size());
        
        IdentifierTag first = typeSpecificTags.getFirst();
        assertEquals(IdentifierTag.class, first.getClass());
        assertEquals(AbstractBadgeDefinitionEvent.Type.UPVOTE.getName(), first.getUuid());

        String uuid = first.getUuid();
        String expected = AbstractBadgeDefinitionEvent.Type.UPVOTE.getName();
        AbstractBadgeDefinitionEvent.Type actual = AbstractBadgeDefinitionEvent.Type.valueOfString(uuid);
        assertEquals(expected, actual.getName());
    }

    @Test
    void badgeDefinitionDownVoteEventTest() {
        BadgeDefinitionDownVoteEvent definitionDownVoteEvent = new BadgeDefinitionDownVoteEvent(senderPubkey);
        List<IdentifierTag> typeSpecificTags = Filterable.getTypeSpecificTags(IdentifierTag.class, definitionDownVoteEvent);
        assertEquals(1, typeSpecificTags.size());

        IdentifierTag first = typeSpecificTags.getFirst();
        assertEquals(IdentifierTag.class, first.getClass());
        assertEquals(AbstractBadgeDefinitionEvent.Type.DOWNVOTE.getName(), first.getUuid());

        String uuid = first.getUuid();
        String expected = AbstractBadgeDefinitionEvent.Type.DOWNVOTE.getName();
        AbstractBadgeDefinitionEvent.Type actual = AbstractBadgeDefinitionEvent.Type.valueOfString(uuid);
        assertEquals(expected, actual.getName());
    }

    @Test
    void badgeDefinitionReputationEventTest() {
        BadgeDefinitionReputationEvent definitionReputationEvent = new BadgeDefinitionReputationEvent(senderPubkey);
        List<IdentifierTag> typeSpecificTags = Filterable.getTypeSpecificTags(IdentifierTag.class, definitionReputationEvent);
        assertEquals(1, typeSpecificTags.size());

        IdentifierTag first = typeSpecificTags.getFirst();
        assertEquals(IdentifierTag.class, first.getClass());
        assertEquals(AbstractBadgeDefinitionEvent.Type.REPUTATION.getName(), first.getUuid());

        String uuid = first.getUuid();
        String expected = AbstractBadgeDefinitionEvent.Type.REPUTATION.getName();
        AbstractBadgeDefinitionEvent.Type actual = AbstractBadgeDefinitionEvent.Type.valueOfString(uuid);
        assertEquals(expected, actual.getName());
    }
}
