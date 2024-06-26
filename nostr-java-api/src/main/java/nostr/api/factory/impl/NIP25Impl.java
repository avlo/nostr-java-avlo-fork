/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nostr.api.factory.impl;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import nostr.api.factory.EventFactory;
import nostr.event.BaseTag;
import nostr.event.Reaction;
import nostr.event.impl.GenericEvent;
import nostr.event.impl.ReactionEvent;
import nostr.id.Identity;

import java.util.List;

/**
 *
 * @author eric
 */
// TESTME
public class NIP25Impl {

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class ReactionEventFactory extends EventFactory<ReactionEvent> {

        public final GenericEvent event;

        public ReactionEventFactory(@NonNull Identity sender, @NonNull GenericEvent event, Reaction reaction) {
            super(sender, reaction.getEmoji());
            this.event = event;
        }

        public ReactionEventFactory(@NonNull Identity sender, @NonNull List<BaseTag> tags, @NonNull GenericEvent event, String reaction) {
            super(sender, tags, reaction);
            this.event = event;
        }

        public ReactionEventFactory(@NonNull Identity sender, @NonNull GenericEvent event, String content) {
            super(sender, content);
            this.event = event;
        }

        public ReactionEventFactory(@NonNull Identity sender, @NonNull List<BaseTag> tags, String content) {
            super(sender, tags, content);
            this.event = null;
        }

        @Override
        public ReactionEvent create() {
            return new ReactionEvent(getSender(), event, getTags(), getContent());
        }
    }

}
