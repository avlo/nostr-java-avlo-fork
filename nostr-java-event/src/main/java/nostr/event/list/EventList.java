
package nostr.event.list;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.NonNull;
import nostr.event.impl.GenericEventImpl;

/**
 *
 * @author squirrel
 */
@Builder
// TODO - public class EventList extends BaseList<? extends GenericEvent>
public class EventList extends BaseList<GenericEventImpl> {

    public EventList() {
        this(new ArrayList<>());
    }

    private EventList(@NonNull List<GenericEventImpl> list) {
        super(list);
    }
}
