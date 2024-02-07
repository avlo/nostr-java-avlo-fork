package nostr.event.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import nostr.base.PublicKey;
import nostr.base.annotation.Event;
import nostr.event.AbstractEventContent;
import nostr.event.json.serializer.ItemSerializer;

/**
 *
 * @author eric
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Event(name = "", nip = 15)
public class CustomerOrderEvent extends CheckoutEvent {

    private CustomerOrderEvent() {
        super();
    }
    public CustomerOrderEvent(PublicKey sender, @NonNull Customer customer) {
        super(sender, customer.getContact().getPublicKey(), customer);
    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @ToString(callSuper = true)
    public static class Customer extends AbstractEventContent<CheckoutEvent> {

        @JsonProperty
        private final String id;

        @JsonProperty
        private MessageType type;

        @JsonProperty
        private String name;

        @JsonProperty
        private String address;

        @JsonProperty
        private String message;

        @JsonProperty
        private Contact contact;

        @JsonProperty
        private List<Item> items;

        @JsonProperty("shipping_id")
        private String shippingId;

        public Customer() {
            this.items = new ArrayList<>();
            this.id = UUID.randomUUID().toString();
        }

        @Data
        //@JsonSerialize(using = ContactSerializer.class)
        public static class Contact {

            @JsonProperty("nostr")
            private final PublicKey publicKey;

            @JsonProperty
            private String phone;

            @JsonProperty
            private String email;

            public Contact(@NonNull PublicKey publicKey) {
                this.publicKey = publicKey;
            }
                        
        }

        @Data
        @NoArgsConstructor
        @JsonSerialize(using = ItemSerializer.class)
        public static class Item {

            @JsonProperty
            private Product product;

            @JsonProperty
            private int quantity;
        }
    }
}
