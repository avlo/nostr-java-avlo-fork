
package nostr.event.tag;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import nostr.base.annotation.Key;
import nostr.base.annotation.Tag;
import nostr.event.BaseTag;

/**
 *
 * @author squirrel
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Tag(code = "subject", nip = 14)
@JsonPropertyOrder({"subject"})
public final class SubjectTag extends BaseTag {

    @Key
    @JsonProperty("subject")
    private String subject;

    public static <T extends BaseTag> T deserialize(@NonNull JsonNode node) {
        SubjectTag tag = new SubjectTag();

        final JsonNode nodeSubject = node.get(1);
        if (nodeSubject != null) {
            tag.setSubject(nodeSubject.asText());
        }
        return (T) tag;
    }
}
