
package nostr.event.impl;

import nostr.base.ElementAttribute;
import nostr.base.annotation.Event;
import nostr.event.BaseTag;

import java.util.List;
import java.util.Map;

/**
 * @author squirrel
 */
@Event(name = "OpenTimestamps Attestations for Events")
public class OtsEvent extends TextNoteEvent {

  public OtsEvent(GenericEvent genericEvent, List<BaseTag> tags, String content, String ots) {
    super(genericEvent, tags, content);
    var attribute = ElementAttribute.builder().nip(3).name("param0").value(Map.of("ots", ots)).build();
    this.addAttribute(attribute);
  }

}
