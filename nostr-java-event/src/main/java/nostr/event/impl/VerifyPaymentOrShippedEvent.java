package nostr.event.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import nostr.base.annotation.Event;
import nostr.event.AbstractEventContent;
import nostr.event.impl.CustomerOrderEvent.Customer;

import java.util.UUID;

/**
 * @author eric
 */
@EqualsAndHashCode(callSuper = false)
@Event(name = "", nip = 15)
public class VerifyPaymentOrShippedEvent extends EventDecorator {
  private final Customer customer;
  private final PaymentShipmentStatus status;

  public VerifyPaymentOrShippedEvent(GenericEvent genericEvent, Customer customer, PaymentShipmentStatus status) {
    super(genericEvent);
    this.customer = customer;
    this.status = status;
  }

  @Getter
  @Setter
  @EqualsAndHashCode(callSuper = false)
  @ToString(callSuper = true)
  public static class PaymentShipmentStatus extends AbstractEventContent<VerifyPaymentOrShippedEvent> {

    @JsonProperty
    private final String id;

    @JsonProperty
    private CheckoutEvent.MessageType type;

    @JsonProperty
    private String message;

    @JsonProperty
    private boolean paid;

    @JsonProperty
    private boolean shipped;

    public PaymentShipmentStatus() {
      this.id = UUID.randomUUID().toString();
    }
  }
}
