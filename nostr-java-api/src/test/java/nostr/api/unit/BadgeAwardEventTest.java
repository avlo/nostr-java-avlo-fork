package nostr.api.unit;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import nostr.base.PublicKey;
import nostr.event.Kind;
import nostr.event.filter.Filterable;
import nostr.event.impl.AbstractBadgeAwardEvent;
import nostr.event.impl.BadgeAwardDownvoteEvent;
import nostr.event.impl.BadgeAwardReputationEvent;
import nostr.event.impl.BadgeAwardUpvoteEvent;
import nostr.event.tag.AddressTag;
import nostr.event.tag.PubKeyTag;
import nostr.id.Identity;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BadgeAwardEventTest {
    public static final PublicKey creator = new PublicKey(Identity.generateRandomIdentity().getPublicKey().toString());
    public static final PublicKey receiver = new PublicKey(Identity.generateRandomIdentity().getPublicKey().toString());
    public final URI uri;

    public BadgeAwardEventTest() throws URISyntaxException {
        this.uri = new URI("ws://localhost:5050");
    }

    @Test
    void badgeAwardUpvoteEventTest() {
        BadgeAwardUpvoteEvent awardUpvoteEvent = new BadgeAwardUpvoteEvent(creator, receiver, uri);
        List<AddressTag> typeSpecificTags = Filterable.getTypeSpecificTags(AddressTag.class, awardUpvoteEvent);
        assertEquals(1, typeSpecificTags.size());

        AddressTag addressTag = typeSpecificTags.getFirst();
        assertEquals(AddressTag.class, addressTag.getClass());
        assertEquals(creator, addressTag.getPublicKey());
        assertEquals(Kind.BADGE_AWARD_EVENT.getValue(), addressTag.getKind());
        assertEquals(AbstractBadgeAwardEvent.Type.UPVOTE.toString(), addressTag.getIdentifierTag().getUuid());

        List<PubKeyTag> pubKeyTags = Filterable.getTypeSpecificTags(PubKeyTag.class, awardUpvoteEvent);
        assertEquals(1, pubKeyTags.size());
        PubKeyTag pubkey = pubKeyTags.getFirst();
        assertEquals(receiver, pubkey.getPublicKey());
        assertEquals(uri.toString(), pubkey.getMainRelayUrl());
    }

    @Test
    void badgeAwardDownvoteEventTest() {
        BadgeAwardDownvoteEvent awardDownvoteEvent = new BadgeAwardDownvoteEvent(creator, receiver, uri);
        List<AddressTag> typeSpecificTags = Filterable.getTypeSpecificTags(AddressTag.class, awardDownvoteEvent);
        assertEquals(1, typeSpecificTags.size());

        AddressTag addressTag = typeSpecificTags.getFirst();
        assertEquals(AddressTag.class, addressTag.getClass());
        assertEquals(creator, addressTag.getPublicKey());
        assertEquals(Kind.BADGE_AWARD_EVENT.getValue(), addressTag.getKind());
        assertEquals(AbstractBadgeAwardEvent.Type.DOWNVOTE.toString(), addressTag.getIdentifierTag().getUuid());

        List<PubKeyTag> pubKeyTags = Filterable.getTypeSpecificTags(PubKeyTag.class, awardDownvoteEvent);
        assertEquals(1, pubKeyTags.size());
        PubKeyTag pubkey = pubKeyTags.getFirst();
        assertEquals(receiver, pubkey.getPublicKey());
        assertEquals(uri.toString(), pubkey.getMainRelayUrl());
    }

    @Test
    void badgeAwardReputationEventTest() {
        BigDecimal score = BigDecimal.valueOf(0.5);
        BadgeAwardReputationEvent awardReputationEvent = new BadgeAwardReputationEvent(creator, receiver, score, uri);
        assertEquals(score, awardReputationEvent.getScore());

        List<AddressTag> typeSpecificTags = Filterable.getTypeSpecificTags(AddressTag.class, awardReputationEvent);
        assertEquals(1, typeSpecificTags.size());

        AddressTag addressTag = typeSpecificTags.getFirst();
        assertEquals(AddressTag.class, addressTag.getClass());
        assertEquals(creator, addressTag.getPublicKey());
        assertEquals(Kind.BADGE_AWARD_EVENT.getValue(), addressTag.getKind());
        assertEquals(AbstractBadgeAwardEvent.Type.REPUTATION.toString(), addressTag.getIdentifierTag().getUuid());

        List<PubKeyTag> pubKeyTags = Filterable.getTypeSpecificTags(PubKeyTag.class, awardReputationEvent);
        assertEquals(1, pubKeyTags.size());
        PubKeyTag pubkey = pubKeyTags.getFirst();
        assertEquals(receiver, pubkey.getPublicKey());
        assertEquals(uri.toString(), pubkey.getMainRelayUrl());
    }


    @Test
    void testThrowsException() {
        assertThrows(AssertionError.class, () -> new BadgeAwardReputationEvent(creator, receiver, new BigDecimal("1.1"), uri));
        assertThrows(AssertionError.class, () -> new BadgeAwardReputationEvent(creator, receiver, new BigDecimal("-0.1"), uri));
        assertThrows(NumberFormatException.class, () -> new BadgeAwardReputationEvent(creator, receiver, new BigDecimal("NaN"), uri));
    }

    @Test
    void testDoesNotThrowAnyExceptions() {
        new BadgeAwardReputationEvent(creator, receiver, new BigDecimal("0.0"), uri);
        new BadgeAwardReputationEvent(creator, receiver, new BigDecimal("000.0000"), uri);
        new BadgeAwardReputationEvent(creator, receiver, new BigDecimal("000.111"), uri);
        new BadgeAwardReputationEvent(creator, receiver, new BigDecimal("0.512312"), uri);
        new BadgeAwardReputationEvent(creator, receiver, new BigDecimal("0.9999"), uri);
        new BadgeAwardReputationEvent(creator, receiver, new BigDecimal("1.00000"), uri);
    }
}
