/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nostr.api.factory.impl;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import nostr.api.factory.EventFactory;
import nostr.base.ChannelProfile;
import nostr.base.ContentReason;
import nostr.base.PublicKey;
import nostr.base.Relay;
import nostr.event.impl.*;
import nostr.id.Identity;

import static nostr.util.NostrUtil.escapeJsonString;

/**
 * @author eric
 */
public class NIP28 {

  @Data
  @EqualsAndHashCode(callSuper = false)
  public static class ChannelCreateEventFactory extends EventFactory<ChannelCreateEvent> {

    private final ChannelProfile profile;

    public ChannelCreateEventFactory(@NonNull ChannelProfile profile) {
      super(null);
      this.profile = profile;
    }

    @Override
    public ChannelCreateEvent create() {
      return new ChannelCreateEvent(new GenericEventImpl(getSender()), profile);
    }
  }

  @Data
  @EqualsAndHashCode(callSuper = false)
  public static class ChannelMessageEventFactory extends EventFactory<ChannelMessageEvent> {

    private final ChannelCreateEvent channelCreateEventRoot;
    private ChannelMessageEvent channelMessageEvent;
    private Relay recommendedRelayRoot;
    private Relay recommendedRelayReply;

    public ChannelMessageEventFactory(@NonNull ChannelCreateEvent channelCreateEventRoot, @NonNull String content) {
      super(content);
      this.channelCreateEventRoot = channelCreateEventRoot;
    }

    @Override
    public ChannelMessageEvent create() {
      return new ChannelMessageEvent(new GenericEventImpl(getSender()), channelCreateEventRoot, channelMessageEvent, getContent(), recommendedRelayRoot, recommendedRelayReply);
    }
  }

  @Data
  @EqualsAndHashCode(callSuper = false)
  public static class ChannelMetadataEventFactory extends EventFactory<ChannelMetadataEvent> {

    private final ChannelProfile profile;
    private final ChannelCreateEvent channelCreateEvent;

    public ChannelMetadataEventFactory(@NonNull ChannelCreateEvent channelCreateEvent, @NonNull ChannelProfile profile) {
      super(null);
      this.channelCreateEvent = channelCreateEvent;
      this.profile = profile;
    }

    public ChannelMetadataEventFactory(@NonNull Identity sender, @NonNull ChannelCreateEvent channelCreateEvent, @NonNull ChannelProfile profile) {
      super(sender, null);
      this.channelCreateEvent = channelCreateEvent;
      this.profile = profile;
    }

    @Override
    public ChannelMetadataEvent create() {
      var channelMetadataEvent = new ChannelMetadataEvent(channelCreateEvent);
      channelMetadataEvent.setContent(escapeJsonString(profile.toString()));
      return channelMetadataEvent;
    }
  }

  @Data
  @EqualsAndHashCode(callSuper = false)
  public static class HideMessageEventFactory extends EventFactory<HideMessageEvent> {

    private final ChannelMessageEvent channelMessageEvent;
    private String reason;

    public HideMessageEventFactory(@NonNull ChannelMessageEvent channelMessageEvent, @NonNull String reason) {
      super(null);
      this.channelMessageEvent = channelMessageEvent;
      this.reason = reason;
    }

    public HideMessageEventFactory(@NonNull Identity sender, @NonNull ChannelMessageEvent channelMessageEvent, @NonNull String reason) {
      super(sender, null);
      this.channelMessageEvent = channelMessageEvent;
      this.reason = reason;
    }

    @Override
    public String getContent() {
      if (reason != null) {
        ContentReason contentReason = new ContentReason(reason);
        return escapeJsonString(contentReason.toString());
      }

      return null;
    }

    @Override
    public HideMessageEvent create() {
      return new HideMessageEvent(new GenericEventImpl(getSender()), channelMessageEvent, getContent());
    }
  }

  @Data
  @EqualsAndHashCode(callSuper = false)
  public static class MuteUserEventFactory extends EventFactory<MuteUserEvent> {

    private final PublicKey mutedUser;
    private String reason;

    public MuteUserEventFactory(@NonNull PublicKey mutedUser, @NonNull String reason) {
      super(null);
      this.mutedUser = mutedUser;
      this.reason = reason;
    }

    public MuteUserEventFactory(@NonNull Identity sender, @NonNull PublicKey mutedUser, @NonNull String reason) {
      super(sender, null);
      this.mutedUser = mutedUser;
      this.reason = reason;
    }

    @Override
    public String getContent() {
      if (reason != null) {
        ContentReason contentReason = new ContentReason(reason);
        return escapeJsonString(contentReason.toString());
      }

      return null;
    }

    @Override
    public MuteUserEvent create() {
      return new MuteUserEvent(new GenericEventImpl(getSender()), mutedUser, getContent());
    }
  }
}
