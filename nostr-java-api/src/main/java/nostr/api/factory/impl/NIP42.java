/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nostr.api.factory.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import nostr.api.factory.EventFactory;
import nostr.api.factory.MessageFactory;
import nostr.api.factory.TagFactory;
import nostr.base.Command;
import nostr.base.ElementAttribute;
import nostr.base.Relay;
import nostr.event.BaseTag;
import nostr.event.impl.ClientAuthenticationEvent;
import nostr.event.impl.GenericEventImpl;
import nostr.event.impl.GenericMessage;
import nostr.event.message.ClientAuthenticationMessage;
import nostr.id.Identity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author eric
 */
public class NIP42 {

  @Data
  @EqualsAndHashCode(callSuper = false)
  public static class ClientAuthenticationEventFactory extends EventFactory<ClientAuthenticationEvent> {

    private final String challenge;
    private final Relay relay;

    public ClientAuthenticationEventFactory(@NonNull String challenge, @NonNull Relay relay) {
      super(null);
      this.challenge = challenge;
      this.relay = relay;
    }

    public ClientAuthenticationEventFactory(@NonNull Identity sender, @NonNull String challenge, @NonNull Relay relay) {
      super(sender, null);
      this.challenge = challenge;
      this.relay = relay;
    }

    public ClientAuthenticationEventFactory(List<BaseTag> tags, @NonNull String challenge, @NonNull Relay relay) {
      super(tags, null);
      this.challenge = challenge;
      this.relay = relay;
    }

    public ClientAuthenticationEventFactory(@NonNull Identity sender, @NonNull List<BaseTag> tags, @NonNull String challenge, @NonNull Relay relay) {
      super(sender, tags, null);
      this.challenge = challenge;
      this.relay = relay;
    }

    @Override
    public ClientAuthenticationEvent create() {
      var event = new ClientAuthenticationEvent(new GenericEventImpl(getSender()), challenge, relay);
      return event;
    }
  }

  @Data
  @EqualsAndHashCode(callSuper = false)
  public static class RelaysTagFactory extends TagFactory {

    public RelaysTagFactory(List<Relay> relays) {
      super("relays", 42, relays.stream().map(Relay::getHostname).collect(Collectors.joining(",")));
    }

    public RelaysTagFactory(Relay relay) {
      super("relays", 42, relay.getHostname());
    }
  }

  @Data
  @EqualsAndHashCode(callSuper = false)
  public static class ChallengeTagFactory extends TagFactory {

    public ChallengeTagFactory(String challenge) {
      super("challenge", 42, challenge);
    }
  }

  @Data
  @EqualsAndHashCode(callSuper = false)
  @AllArgsConstructor
  public static class RelayAuthenticationMessageFactory extends MessageFactory<GenericMessage> {

    @NonNull
    private final String challenge;

    @Override
    public GenericMessage create() {
      final List<ElementAttribute> attributes = new ArrayList<>();
      final var attr = new ElementAttribute("challenge", challenge, 42);
      attributes.add(attr);
      return new GenericMessage(Command.AUTH.name(), attributes, 42);
    }
  }

  @Data
  @EqualsAndHashCode(callSuper = false)
  @AllArgsConstructor
  public static class ClientAuthenticationMessageFactory extends MessageFactory<ClientAuthenticationMessage> {

    @NonNull
    private final ClientAuthenticationEvent event;

    @Override
    public ClientAuthenticationMessage create() {
      return new ClientAuthenticationMessage(event);
    }
  }

}
