package nostr.event;

import java.util.List;

import lombok.NoArgsConstructor;
import nostr.base.PublicKey;
import nostr.event.impl.GenericEvent;

/**
 * @author guilhermegps
 */
@NoArgsConstructor
public abstract class NIP09Event extends GenericEvent {

	public NIP09Event(PublicKey pubKey, Kind kind, List<BaseTag> tags, String content) {
		super(pubKey, kind, tags, content);
	}

}
