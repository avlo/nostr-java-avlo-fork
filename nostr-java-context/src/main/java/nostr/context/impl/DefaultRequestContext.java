package nostr.context.impl;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import nostr.base.Relay;
import nostr.context.RequestContext;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class DefaultRequestContext implements RequestContext {
    private byte[] privateKey;
    private String subscriptionId;
    private Map<String, String> relays;
    private Map<Relay, String> challenges = new HashMap<>();

    @Override
    public void validate() {
    }

    public String getChallenge(@NonNull Relay relay) {
        return challenges.get(relay);
    }

    public String getChallenge(String relay) {
        return getChallenge(new Relay(relay));
    }

    public void setChallenge(@NonNull Relay relay, @NonNull String challenge) {
        challenges.put(relay, challenge);
    }

    public void setChallenge(String relay, String challenge) {
        setChallenge(new Relay(relay), challenge);
    }
}