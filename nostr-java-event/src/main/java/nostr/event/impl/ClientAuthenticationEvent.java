package nostr.event.impl;

import lombok.Getter;
import lombok.NonNull;
import nostr.base.ElementAttribute;
import nostr.base.Relay;
import nostr.base.annotation.Event;
import nostr.event.BaseTag;
import nostr.event.Kind;

import java.util.ArrayList;
import java.util.List;

/**
 * @author squirrel
 */
@Getter
@Event(name = "Authentication of clients to relays", nip = 42)
public class ClientAuthenticationEvent extends EventDecorator {
  private final GenericEvent genericEvent;
  private final String challenge;
  private final Relay relay;

  public ClientAuthenticationEvent(GenericEvent genericEvent, String challenge, @NonNull Relay relay) {
    super(genericEvent);
    this.genericEvent = genericEvent;
    this.genericEvent.setKind(Kind.CLIENT_AUTH);
    this.challenge = challenge;
    this.relay = relay;

    List<ElementAttribute> chAttributes = new ArrayList<>();
    var attribute = ElementAttribute.builder().nip(42).name("challenge").value(challenge).build();
    chAttributes.add(attribute);

    this.setTags(new ArrayList<>());
    BaseTag chTag = new GenericTag("auth", 42, chAttributes);

    this.genericEvent.addTag(chTag);

    final List<ElementAttribute> relayAttributes = new ArrayList<>();
    final ElementAttribute relayAttribute = getRelayAttribute(relay);
    relayAttributes.add(relayAttribute);
    final BaseTag relayTag = new GenericTag("relay", 42, relayAttributes);
    this.genericEvent.addTag(relayTag);

    this.genericEvent.setNip(42);
  }

  private static ElementAttribute getRelayAttribute(Relay relay) {
    return ElementAttribute.builder().nip(42).name("uri").value(relay.getHostname()).build();
  }

}
