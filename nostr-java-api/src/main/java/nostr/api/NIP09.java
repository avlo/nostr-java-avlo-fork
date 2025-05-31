package nostr.api;

import jakarta.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import lombok.NonNull;
import nostr.api.factory.impl.NIP09Impl;
import nostr.base.PublicKey;
import nostr.base.Relay;
import nostr.event.BaseTag;
import nostr.event.NIP09Event;
import nostr.event.impl.DeletionEvent;
import nostr.event.impl.GenericEvent;
import nostr.event.tag.AddressTag;
import nostr.event.tag.EventTag;
import nostr.event.tag.GenericTag;
import nostr.event.tag.IdentifierTag;
import nostr.id.Identity;

public class NIP09<T extends NIP09Event> extends EventNostr<T> {

    public NIP09(@NonNull Identity sender) {
        setSender(sender);
    }

    public NIP09<T> createDeletionEvent(@NotEmpty List<BaseTag> tags, @NonNull String content) {
        NIP09Impl.DeletionEventFactory deletionEventFactory = new NIP09Impl.DeletionEventFactory(getSender(), tags, content);
        DeletionEvent deletionEvent = deletionEventFactory.create();
        setEvent((T) deletionEvent);
        return this;
    }

    public NIP09<T> createDeletionEvent(GenericEvent eventToDelete, @NonNull String content) {
        List<BaseTag> tags = new ArrayList<>();

        // Handle GenericEvents
        tags.add(new EventTag(eventToDelete.getId()));

        // Handle AddressTags
        eventToDelete.getTags().stream()
            .filter(tag -> "a".equals(tag.getCode()))
            .forEach(tag -> {
                if (tag instanceof GenericTag) {
                    AddressTag addressTag = toAddressTag((GenericTag) tag);
                    tags.add(addressTag);
                    tags.add(NIP25.createKindTag(addressTag.getKind()));
                } else if (tag instanceof AddressTag) {
                    tags.add(tag);
                    tags.add(NIP25.createKindTag(((AddressTag) tag).getKind()));
                } else {
                    throw new IllegalArgumentException("Unsupported tag type: " + tag.getClass());
                }
            });

        // Add kind tags for all eventsToDelete
        tags.add(NIP25.createKindTag(eventToDelete.getKind()));

        this.setEvent((T) new NIP09Impl.DeletionEventFactory(getSender(), tags, content).create());
        return this;
    }

    private AddressTag toAddressTag(@NonNull GenericTag genericTag) {
        IdentifierTag identifierTag = new IdentifierTag();
        identifierTag.setUuid(genericTag.getAttributes().get(1).getValue().toString());

        AddressTag addressTag = new AddressTag();
        addressTag.setIdentifierTag(identifierTag);

        String value = genericTag.getAttributes().get(0).getValue().toString();
        String[] parts = value.split(":");
        addressTag.setKind(Integer.decode(parts[0]));
        addressTag.setPublicKey(new PublicKey(parts[1]));
        if (parts.length == 3) {
            addressTag.setRelay(new Relay(parts[2]));
        }

        addressTag.setParent(genericTag.getParent());

        return addressTag;
    }
}
