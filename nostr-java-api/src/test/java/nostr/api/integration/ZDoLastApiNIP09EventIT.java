package nostr.api.integration;

import java.util.List;
import java.util.Map;
import nostr.api.NIP01;
import nostr.api.NIP09;
import nostr.base.Relay;
import nostr.config.RelayConfig;
import nostr.event.BaseMessage;
import nostr.event.BaseTag;
import nostr.event.impl.GenericEvent;
import nostr.event.impl.ReplaceableEvent;
import nostr.event.impl.TextNoteEvent;
import nostr.event.message.OkMessage;
import nostr.event.tag.AddressTag;
import nostr.event.tag.EventTag;
import nostr.event.tag.IdentifierTag;
import nostr.id.Identity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringJUnitConfig(RelayConfig.class)
@ActiveProfiles("test")
public class ZDoLastApiNIP09EventIT {
    @Autowired
    private Map<String, String> relays;

    @Test
    public void deleteEventWithRef() {
        Identity identity = Identity.generateRandomIdentity();

        NIP01<ReplaceableEvent> nip011 = new NIP01<>(identity);
        BaseMessage replaceableMessage = nip011.createReplaceableEvent(10_001, "replaceable event").signAndSend(relays);

        assertNotNull(replaceableMessage);
        assertInstanceOf(OkMessage.class, replaceableMessage);

        GenericEvent replaceableEvent = nip011.getEvent();
        IdentifierTag identifierTag = new IdentifierTag(replaceableEvent.getId());

        NIP01<TextNoteEvent> nip01 = new NIP01<>(identity);
        nip01
            .createTextNoteEvent("Reference me!")
            .getEvent()
            .addTag(new AddressTag(10_001, identity.getPublicKey(), identifierTag, new Relay("ws://localhost:5555")));

        BaseMessage message = nip01.signAndSend(relays);

        assertNotNull(message);
        assertInstanceOf(OkMessage.class, message);

        GenericEvent event = nip01.getEvent();

        NIP09<?> nip09 = new NIP09<>(identity);
        GenericEvent deletedEvent = nip09.createDeletionEvent(event, "content").getEvent();

        assertEquals(4, deletedEvent.getTags().size());

        List<BaseTag> eventTags = deletedEvent.getTags()
            .stream()
            .filter(t -> "e".equals(t.getCode()))
            .toList();

        assertEquals(1, eventTags.size());

        EventTag eventTag = (EventTag) eventTags.getFirst();
        assertEquals(event.getId(), eventTag.getIdEvent());

        List<BaseTag> addressTags = deletedEvent.getTags()
            .stream()
            .filter(t -> "a".equals(t.getCode()))
            .toList();

        assertEquals(1, addressTags.size());

        AddressTag addressTag = (AddressTag) addressTags.getFirst();
        assertEquals(10_001, addressTag.getKind());
        assertEquals(replaceableEvent.getId(), addressTag.getIdentifierTag().getUuid());
        assertEquals(identity.getPublicKey(), addressTag.getPublicKey());

        List<BaseTag> kindTags = deletedEvent.getTags()
            .stream()
            .filter(t -> "k".equals(t.getCode()))
            .toList();

        assertEquals(2, kindTags.size());

        nip09.signAndSend(relays);
    }
}
