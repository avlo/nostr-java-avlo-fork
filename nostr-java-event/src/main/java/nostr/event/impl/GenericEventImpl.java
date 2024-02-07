package nostr.event.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import nostr.base.*;
import nostr.base.annotation.Key;
import nostr.crypto.bech32.Bech32;
import nostr.crypto.bech32.Bech32Prefix;
import nostr.event.BaseTag;
import nostr.event.json.deserializer.PublicKeyDeserializer;
import nostr.event.json.deserializer.SignatureDeserializer;
import nostr.util.NostrException;
import nostr.util.NostrUtil;

import java.beans.Transient;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.List;

/**
 * @author squirrel
 */
@Getter
@EqualsAndHashCode
public class GenericEventImpl implements GenericEvent, ISignable, IGenericElement {
  @Key
  @EqualsAndHashCode.Include
  private String id;

  @Key
  @JsonProperty("pubkey")
  @EqualsAndHashCode.Include
  @JsonDeserialize(using = PublicKeyDeserializer.class)
  private PublicKey pubKey;

  @Key
  @JsonProperty("created_at")
  @EqualsAndHashCode.Exclude
  private Long createdAt;

  @Key
  @EqualsAndHashCode.Exclude
  private Integer kind;

  @Key
  @EqualsAndHashCode.Exclude
  @JsonProperty("tags")
  private List<BaseTag> tags;

  @Key
  @EqualsAndHashCode.Exclude
  private String content;

  @Key
  @JsonProperty("sig")
  @EqualsAndHashCode.Exclude
  @JsonDeserialize(using = SignatureDeserializer.class)
  @Setter
  private Signature signature;

  @JsonIgnore
  @EqualsAndHashCode.Exclude
  private byte[] _serializedEvent;

  @JsonIgnore
  @EqualsAndHashCode.Exclude
  private Integer nip;

  @JsonIgnore
  @EqualsAndHashCode.Exclude
  private List<ElementAttribute> attributes;

//  public GenericEventImpl(@NonNull PublicKey pubKey, @NonNull Integer kind, @NonNull List<BaseTag> tags, String content) {
//    this.pubKey = pubKey;
//    this.kind = kind;
//    this.tags = tags;
//    this.content = content;
//    this.attributes = new ArrayList<>();
//    // Update parents
//    updateTagsParents(tags);
//  }


  @Override
  public String toBech32() {
    if (!isSigned()) {
      this.update();
    }

    try {
      return Bech32.toBech32(Bech32Prefix.NOTE, this.getId());
    } catch (NostrException ex) {
      throw new RuntimeException(ex);
    }
  }

  public void setTags(List<BaseTag> tags) {

    this.tags = tags;

    for (ITag o : tags) {
      o.setParent(this);
    }
  }

  public void addTag(BaseTag tag) {

    if (!tags.contains(tag)) {
      tag.setParent(this);
      tags.add(tag);
    }
  }

  public void update() {

    try {
      this.validate();

      this.createdAt = Instant.now().getEpochSecond();

      this._serializedEvent = this.serialize().getBytes(StandardCharsets.UTF_8);

      this.id = NostrUtil.bytesToHex(NostrUtil.sha256(_serializedEvent));
    } catch (NostrException | NoSuchAlgorithmException ex) {
      throw new RuntimeException(ex);
    }
  }

  @Transient
  public boolean isSigned() {
    return this.signature != null;
  }

  @Override
  public void addAttribute(ElementAttribute attribute) {
    this.attributes.add(attribute);
  }

  protected void validate() {

  }

  private String serialize() throws NostrException {
    var mapper = IEncoder.MAPPER;
    var arrayNode = JsonNodeFactory.instance.arrayNode();

    try {
      arrayNode.add(0);
      arrayNode.add(this.pubKey.toString());
      arrayNode.add(this.createdAt);
      arrayNode.add(this.kind);
      arrayNode.add(mapper.valueToTree(tags));
      arrayNode.add(this.content);

      return mapper.writeValueAsString(arrayNode);
    } catch (JsonProcessingException e) {
      throw new NostrException(e);
    }
  }

  protected final void updateTagsParents(List<? extends BaseTag> tagList) {
    if (tagList != null && !tagList.isEmpty()) {
      for (ITag t : tagList) {
        t.setParent(this);
      }
    }
  }
}
