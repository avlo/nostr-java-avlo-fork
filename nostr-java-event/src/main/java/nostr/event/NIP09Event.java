package nostr.event;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import nostr.base.PublicKey;
import nostr.event.impl.GenericEvent;

@NoArgsConstructor
public abstract class NIP09Event extends GenericEvent {
    public NIP09Event(@NonNull PublicKey pubKey, @NonNull Kind kind, @NotEmpty List<BaseTag> tags, @NonNull String content) {
        super(pubKey, kind, tags, content);
    }
}
