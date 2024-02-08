package nostr.event.impl;

import nostr.base.IGenericElement;
import nostr.base.ISignable;
import nostr.base.PublicKey;
import nostr.base.Signature;
import nostr.event.BaseEvent;
import nostr.event.BaseTag;
import nostr.event.Kind;

import java.util.List;

public interface GenericEvent extends BaseEvent, ISignable, IGenericElement {
  PublicKey getPubKey();
  void setPubKey(PublicKey getPubKey);

  Long getCreatedAt();
  void setCreatedAt(Long createdAt);

  Kind getKind();
  void setKind(Kind kind);

  List<BaseTag> getTags();
  void setTags(List<BaseTag> tags);
  void addTag(BaseTag tag);

  String getContent();
  void setContent(String content);

  Signature getSignature();
  void setSignature(Signature signature);

  Integer getNip();
  void setNip(Integer nip);
}
