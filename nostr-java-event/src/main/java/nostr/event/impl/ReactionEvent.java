package nostr.event.impl;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import nostr.base.ElementAttribute;
import nostr.base.annotation.Event;
import nostr.event.BaseTag;
import nostr.event.Reaction;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author squirrel
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Event(name = "Reactions", nip = 25)
public class ReactionEvent extends EventDecorator {
  private final GenericEvent genericEvent;
  private URL emoji;
  private Reaction reaction;
  public ReactionEvent(GenericEvent genericEvent) {
//        super(pubKey, Kind.REACTION, tags, reaction.getEmoji());
    super(genericEvent);
    this.genericEvent = genericEvent;
  }

//    public ReactionEvent(PublicKey pubKey, GenericEventImpl event, Reaction reaction) {
//        super(pubKey, Kind.REACTION);
//        this.setContent(reaction.getEmoji());
//        this.addTag(EventTag.builder().idEvent(event.getId()).build());
//    }
//
//    public ReactionEvent(PublicKey pubKey, GenericEventImpl event, String content, @NonNull URL emoji) {
//        super(pubKey, Kind.REACTION);
//        this.setContent(content);
//        this.addTag(EventTag.builder().idEvent(event.getId()).build());
//        addEmojiTag(content, emoji, getTags());
//    }
//
//    public ReactionEvent(PublicKey pubKey, List<BaseTag> tags, String content, @NonNull URL emoji) {
//        super(pubKey, Kind.REACTION, tags);
//        this.setContent(content);
//        addEmojiTag(content, emoji, tags);
//    }

  private void addEmojiTag(String content, URL emoji, List<BaseTag> tags) {
    List<ElementAttribute> attributes = new ArrayList<>();
    attributes.add(ElementAttribute.builder().name("shortcode").nip(30).value(content).build());
    attributes.add(ElementAttribute.builder().name("url").nip(30).value(emoji.toString()).build());
    tags.add(new GenericTag("emoji", 30, attributes));
  }
}
