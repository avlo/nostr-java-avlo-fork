/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nostr.api.factory.impl;

import lombok.NonNull;
import nostr.api.factory.EventFactory;
import nostr.base.PublicKey;
import nostr.event.impl.MentionsEvent;
import nostr.event.tag.PubKeyTag;
import nostr.id.Identity;

import java.util.List;

/**
 *
 * @author eric
 */
@Deprecated(since = "NIP-27")
public class NIP08Impl {

    public static class MentionsEventFactory extends EventFactory<MentionsEvent> {

        public MentionsEventFactory(@NonNull Identity sender, @NonNull List<PublicKey> publicKeys, @NonNull String content) {
            super(sender, content);
            publicKeys.forEach(pk -> getTags().add(PubKeyTag.builder().publicKey(pk).build()));
        }

        @Override
        public MentionsEvent create() {
            var event = new MentionsEvent(getSender(), getTags(), getContent());
            getTags().forEach(t -> event.addTag(t));
            return event;
        }

    }
}
