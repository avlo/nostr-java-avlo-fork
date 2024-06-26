/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nostr.api.factory.impl;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import nostr.api.factory.EventFactory;
import nostr.event.impl.CreateOrUpdateProductEvent;
import nostr.event.impl.CreateOrUpdateStallEvent;
import nostr.event.impl.CustomerOrderEvent;
import nostr.event.impl.MerchantRequestPaymentEvent;
import nostr.event.impl.NostrMarketplaceEvent;
import nostr.event.impl.VerifyPaymentOrShippedEvent;
import nostr.event.tag.HashtagTag;
import nostr.event.tag.IdentifierTag;
import nostr.id.Identity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author eric
 */
public class NIP15Impl {

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class VerifyPaymentOrShippedEventFactory extends EventFactory<VerifyPaymentOrShippedEvent> {

        private final CustomerOrderEvent.Customer customer;
        private final VerifyPaymentOrShippedEvent.PaymentShipmentStatus status;

        @Override
        public VerifyPaymentOrShippedEvent create() {
            return new VerifyPaymentOrShippedEvent(getSender(), customer, status);
        }

    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class MerchantRequestPaymentEventFactory extends EventFactory<MerchantRequestPaymentEvent> {

        private final MerchantRequestPaymentEvent.Payment payment;
        private final CustomerOrderEvent.Customer customer;

        public MerchantRequestPaymentEventFactory(@NonNull Identity sender, CustomerOrderEvent.Customer customer, @NonNull MerchantRequestPaymentEvent.Payment payment) {
            super(sender, payment.toString());
            this.payment = payment;
            this.customer = customer;
        }

        @Override
        public MerchantRequestPaymentEvent create() {
            return new MerchantRequestPaymentEvent(getSender(), this.customer, payment);
        }
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class CustomerOrderEventFactory extends EventFactory<CustomerOrderEvent> {

        private final CustomerOrderEvent.Customer customer;

        public CustomerOrderEventFactory(Identity identity, @NonNull CustomerOrderEvent.Customer customer) {
            super(identity, customer.toString());
            this.customer = customer;
        }

        @Override
        public CustomerOrderEvent create() {
            return new CustomerOrderEvent(getSender(), customer);
        }

    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class CreateOrUpdateStallEventFactory extends EventFactory<CreateOrUpdateStallEvent> {

        private final CreateOrUpdateStallEvent.Stall stall;

        public CreateOrUpdateStallEventFactory(Identity identity, @NonNull CreateOrUpdateStallEvent.Stall stall) {
            super(identity, stall.toString());
            this.stall = stall;
        }

        @Override
        public CreateOrUpdateStallEvent create() {
            return new CreateOrUpdateStallEvent(getSender(), new ArrayList<>(), stall);
        }

    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class CreateOrUpdateProductEventFactory extends EventFactory<CreateOrUpdateProductEvent> {

        private final NostrMarketplaceEvent.Product product;
        private final List<String> categories;

        public CreateOrUpdateProductEventFactory(Identity identity, @NonNull NostrMarketplaceEvent.Product product, List<String> categories) {
            super(identity, product.toString());
            this.product = product;
            this.categories = categories;
        }

        @Override
        public CreateOrUpdateProductEvent create() {
            var event = new CreateOrUpdateProductEvent(getSender(), new ArrayList<>(), product);
            event.addTag(new IdentifierTag(product.getId()));
            if (categories != null) {
                categories.forEach(c -> event.addTag(new HashtagTag(c)));
            }

            return event;
        }

    }
}
