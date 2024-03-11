/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nostr.api;

import java.util.List;

import lombok.NonNull;
import nostr.api.factory.impl.NIP25Impl.ReactionEventFactory;
import nostr.event.BaseTag;
import nostr.event.NIP25Event;
import nostr.event.Reaction;
import nostr.event.impl.GenericEvent;
import nostr.id.Identity;

/**
 *
 * @author eric
 */
public class NIP25<T extends NIP25Event> extends EventNostr<T> {
	
	public NIP25(@NonNull Identity sender) {
		setSender(sender);
	}

    /**
     * Create a Reaction event
     * @param event the related event to react to
     * @param reaction
     * @return 
     */
    public NIP25<T> createReactionEvent(@NonNull GenericEvent event, @NonNull Reaction reaction) {
        var e = new ReactionEventFactory(getSender(), event, reaction).create();
		this.setEvent((T) e);

		return this;
    }
    
    /**
     * Create a NIP25 Reaction event to react to a specific event
     * @param event the related event to react to
     * @param content MAY be an emoji
     * @return 
     */
    public NIP25<T> createReactionEvent(@NonNull GenericEvent event, @NonNull String content) {
    	var e = new ReactionEventFactory(getSender(), event, content).create();
		this.setEvent((T) e);

		return this;
    }

    /**
     * Create a NIP25 Reaction event to react to several event and/or pubkeys
     * @param tags the list of events or pubkeys to react to
     * @param content MAY be an emoji
     * @return 
     */
    public NIP25<T> createReactionEvent(@NonNull List<BaseTag> tags, @NonNull String content) {
    	var e = new ReactionEventFactory(getSender(), tags, content).create();
		this.setEvent((T) e);

		return this;
    }

    /**
     * 
     * @param event
     */
    public void like(@NonNull GenericEvent event) {
        react(event, Reaction.LIKE.getEmoji());
    }

    /**
     * 
     * @param event
     */
    public void dislike(@NonNull GenericEvent event) {
        react(event, Reaction.DISLIKE.getEmoji());
    }
    
    /**
     * 
     * @param event
     * @param reaction
     * @param url
     */
    public void react(@NonNull GenericEvent event, @NonNull String reaction) {
        var e = new ReactionEventFactory(getSender(), event, reaction).create();

		this.setEvent((T) e);
		this.sign().send();
    }
}
