/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nostr.api.factory.impl;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import nostr.api.factory.EventFactory;
import nostr.event.BaseTag;
import nostr.event.Kind;
import nostr.event.impl.*;
import nostr.id.Identity;

/**
 *
 * @author eric
 */
public class NIP16 {

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class ReplaceableEventFactory extends EventFactory<ReplaceableEvent> {

        private final Integer kind;

        public ReplaceableEventFactory(@NonNull Integer kind, @NonNull String content) {
            super(content);
            this.kind = kind;
        }

        public ReplaceableEventFactory(@NonNull Identity sender, @NonNull Integer kind, @NonNull String content) {
            super(sender, content);
            this.kind = kind;
        }

        public ReplaceableEventFactory(@NonNull List<BaseTag> tags, @NonNull Integer kind, @NonNull String content) {
            super(tags, content);
            this.kind = kind;
        }

        public ReplaceableEventFactory(@NonNull Identity sender, @NonNull List<BaseTag> tags, @NonNull Integer kind, @NonNull String content) {
            super(sender, tags, content);
            this.kind = kind;
        }

        @Override
        public ReplaceableEvent create() {
            var event = new ReplaceableEvent(
                new GenericEventImpl());
            event.setPubKey(getSender());
            event.setKind(Kind.valueOf(kind));
            event.setTags(getTags());
            event.setContent(getContent());
            return event;
        }
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class EphemeralEventFactory extends EventFactory<EphemeralEvent> {

        private final Integer kind;

        public EphemeralEventFactory(@NonNull Integer kind, @NonNull String content) {
            super(content);
            this.kind = kind;
        }

        public EphemeralEventFactory(@NonNull Identity sender, @NonNull Integer kind, @NonNull String content) {
            super(sender, content);
            this.kind = kind;
        }

        @Override
        public EphemeralEvent create() {
            return new EphemeralEvent(getSender(), kind, getTags(), getContent());
        }

    }

}