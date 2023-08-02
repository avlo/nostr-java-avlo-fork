/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nostr.api;

import nostr.api.factory.TagFactory;
import java.net.URL;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import nostr.event.tag.GeohashTag;
import nostr.event.tag.HashtagTag;
import nostr.event.tag.ReferenceTag;

/**
 *
 * @author eric
 */
public class NIP12 extends Nostr {

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class HashtagTagFactory extends TagFactory<HashtagTag> {

        private final String hashtag;

        public HashtagTagFactory(@NonNull String hashtag) {
            this.hashtag = hashtag;
        }

        @Override
        public HashtagTag create() {
            return new HashtagTag(hashtag);
        }
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class ReferenceTagFactory extends TagFactory<ReferenceTag> {

        private final URL url;

        public ReferenceTagFactory(@NonNull URL url) {
            this.url = url;
        }

        @Override
        public ReferenceTag create() {
            return new ReferenceTag(url);
        }
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class GeohashTagFactory extends TagFactory<GeohashTag> {

        private final String location;

        public GeohashTagFactory(@NonNull String location) {
            this.location = location;
        }

        @Override
        public GeohashTag create() {
            return new GeohashTag(location);
        }
    }
}
