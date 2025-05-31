
package nostr.event.impl;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import nostr.base.PublicKey;
import nostr.base.annotation.Event;
import nostr.event.BaseTag;
import nostr.event.Kind;
import nostr.event.NIP09Event;

@Data
@EqualsAndHashCode(callSuper = false)
@Event(name = "Event Deletion", nip = 9)
public class DeletionEvent extends NIP09Event {

    public DeletionEvent(@NonNull PublicKey pubKey, @NotEmpty List<BaseTag> tags, @NonNull String content) {
        super(pubKey, Kind.DELETION, tags, content);
    }
}
