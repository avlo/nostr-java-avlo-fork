package nostr.event.impl;

import nostr.base.PublicKey;
import nostr.base.Signature;
import nostr.event.BaseEvent;
import nostr.event.BaseTag;

import java.util.List;

public interface GenericEvent extends BaseEvent {
  PublicKey getPubKey();
  Long getCreatedAt();
  Integer getKind();
  List<BaseTag> getTags();
  String getContent();
  Signature getSignature();
  Integer getNip();
}
