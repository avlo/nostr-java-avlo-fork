
package nostr.event.list;

import lombok.Builder;
import lombok.NonNull;
import nostr.event.impl.GenericEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author squirrel
 */
@Builder
// TODO - public class EventList extends BaseList<? extends GenericEvent>
public class EventList extends BaseList<GenericEvent> {

  public EventList() {
    this(new ArrayList<>());
  }

  private EventList(@NonNull List<GenericEvent> list) {
    super(list);
  }
}
