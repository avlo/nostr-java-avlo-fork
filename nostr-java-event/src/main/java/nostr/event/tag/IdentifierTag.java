package nostr.event.tag;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import nostr.base.annotation.Key;
import nostr.base.annotation.Tag;
import nostr.event.BaseTag;
import nostr.event.json.serializer.IdentifierTagSerializer;

@Builder
@Data
@Tag(code = "d", nip = 1)
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize(using = IdentifierTagSerializer.class)
public class IdentifierTag extends BaseTag {

    @Key
    @JsonProperty
    private String uuid;

    public static <T extends BaseTag> T deserialize(@NonNull JsonNode node) {
        IdentifierTag tag = new IdentifierTag();
        setRequiredField(node.get(1), (n, t) -> tag.setUuid(n.asText()), tag);
        return (T) tag;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        IdentifierTag that = (IdentifierTag) o;
        return Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
