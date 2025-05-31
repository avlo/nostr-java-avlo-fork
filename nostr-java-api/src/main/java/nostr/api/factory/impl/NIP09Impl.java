package nostr.api.factory.impl;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import nostr.api.factory.EventFactory;
import nostr.event.BaseTag;
import nostr.event.impl.DeletionEvent;
import nostr.id.Identity;

public class NIP09Impl {

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class DeletionEventFactory extends EventFactory<DeletionEvent> {

        public DeletionEventFactory(@NonNull Identity sender, @NonNull List<BaseTag> tags, @NonNull String content) {
            super(sender, tags, content);
        }

        @Override
        public DeletionEvent create() {
            return new DeletionEvent(getSender(), getTags(), getContent());
        }
    }

}
