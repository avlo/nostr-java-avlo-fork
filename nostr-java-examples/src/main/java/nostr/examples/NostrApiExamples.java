package nostr.examples;

import lombok.extern.java.Log;
import nostr.api.*;
import nostr.base.ChannelProfile;
import nostr.base.PublicKey;
import nostr.base.UserProfile;
import nostr.event.BaseTag;
import nostr.event.Kind;
import nostr.event.Reaction;
import nostr.event.impl.*;
import nostr.event.list.KindList;
import nostr.event.tag.EventTag;
import nostr.event.tag.PubKeyTag;
import nostr.id.Identity;
import nostr.util.NostrException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.LogManager;

/**
 * @author eric
 */
@Log
public class NostrApiExamples {

  private static final Identity RECEIVER = Identity.generateRandomIdentity();
  private static final Identity SENDER = Identity.generateRandomIdentity();

  private static final UserProfile PROFILE = new UserProfile(SENDER.getPublicKey(), "erict875", "erict875@nostr-java.io", "It's me!", null);

  static {
    final LogManager logManager = LogManager.getLogManager();
    try (final InputStream is = NostrApiExamples.class
        .getResourceAsStream("/logging.properties")) {
      logManager.readConfiguration(is);
    } catch (IOException ex) {
      System.exit(-1000);
    }

    try {
      PROFILE.setPicture(new URI("https://images.unsplash.com/photo-1462888210965-cdf193fb74de").toURL());
    } catch (MalformedURLException | URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

  public static void main(String[] args) throws Exception {
    try {
      log.log(Level.FINE, "================= The Beginning");
      logAccountsData();

      ExecutorService executor = Executors.newFixedThreadPool(10);

      executor.submit(() -> {
        sendTextNoteEvent();
      });

      executor.submit(() -> {
        sendEncryptedDirectMessage();
      });

      executor.submit(() -> {
        mentionsEvent();
      });

      executor.submit(() -> {
        deletionEvent();
      });

      executor.submit(() -> {
        metaDataEvent();
      });

      executor.submit(() -> {
        ephemerealEvent();
      });

      executor.submit(() -> {
        reactionEvent();
      });

      executor.submit(() -> {
        replaceableEvent();
      });

      executor.submit(() -> {
        internetIdMetadata();
      });

      executor.submit(() -> {
        filters();
      });

      executor.submit(() -> {
        createChannel();
      });
      executor.submit(() -> {
        updateChannelMetadata();
      });

      executor.submit(() -> {
        sendChannelMessage();
      });

      executor.submit(() -> {
        hideMessage();
      });

      executor.submit(() -> {
        muteUser();
      });

      stop(executor);

      if (executor.isTerminated()) {
        log.log(Level.FINE, "================== The End");
      }

    } catch (IllegalArgumentException ex) {
      log.log(Level.SEVERE, null, ex);
      throw new NostrException(ex);
    }
  }

  private static void sendTextNoteEvent() {
    logHeader("sendTextNoteEvent");

    PubKeyTag rcptTag = PubKeyTag.builder().publicKey(RECEIVER.getPublicKey()).build();
    List<BaseTag> tags = new ArrayList<>();
    tags.add(rcptTag);

    var event = NIP01.createTextNoteEvent(tags, "Hello world, I'm here on nostr-java API!");
    Nostr.sign(event);
    Nostr.send(event);
  }

  private static void sendEncryptedDirectMessage() {
    logHeader("sendEncryptedDirectMessage");

    var event2 = NIP04.createDirectMessageEvent(RECEIVER.getPublicKey(), "Hello Nakamoto!");
    NIP04.encrypt(SENDER, event2);
    Nostr.sign(event2);
    Nostr.send(event2);
  }

  private static void mentionsEvent() {
    logHeader("mentionsEvent");

    List<PublicKey> publicKeys = new ArrayList<>();
    publicKeys.add(RECEIVER.getPublicKey());

    var event = NIP08.createMentionsEvent(publicKeys, "Hello #[1]");
    Nostr.sign(event);
    Nostr.send(event);
  }

  private static void deletionEvent() {
    logHeader("deletionEvent");

    var event = NIP01.createTextNoteEvent("Hello Astral, Please delete me!");
    Nostr.sign(event);
    Nostr.send(event);

    List<BaseTag> tags = new ArrayList<>();
    tags.add(EventTag.builder().idEvent(event.getId()).build());
    var delEvent = NIP09.createDeletionEvent(tags);

    Nostr.sign(delEvent);
    Nostr.send(delEvent);
  }

  private static void metaDataEvent() {
    logHeader("metaDataEvent");

    var event = NIP01.createMetadataEvent(PROFILE);
    Nostr.sign(event);
    Nostr.send(event);
  }

  private static void ephemerealEvent() {
    logHeader("ephemerealEvent");

    var event = NIP16.createEphemeralEvent(Integer.SIZE, "An ephemereal event");
    Nostr.sign(event);
    Nostr.send(event);

  }

  private static void reactionEvent() {
    logHeader("reactionEvent");

    var event = NIP01.createTextNoteEvent("Hello Astral, Please like me!");
    Nostr.sign(event);
    Nostr.send(event);

    var reactionEvent = NIP25.createReactionEvent(event, Reaction.LIKE);
    Nostr.sign(reactionEvent);
    Nostr.send(reactionEvent);
  }

  private static void replaceableEvent() {
    logHeader("replaceableEvent");

    var event = NIP01.createTextNoteEvent("Hello Astral, Please replace me!");
    Nostr.sign(event);
    Nostr.send(event);

    List<BaseTag> tags = new ArrayList<>();
    tags.add(EventTag.builder().idEvent(event.getId()).build());
    var replaceableEvent = NIP16.createReplaceableEvent(tags, 15_000, "New content");
    Nostr.sign(replaceableEvent);
    Nostr.send(replaceableEvent);
  }

  private static void internetIdMetadata() {
    logHeader("internetIdMetadata");

    var event = NIP05.createInternetIdentifierMetadataEvent(PROFILE);
    Nostr.sign(event);
    Nostr.send(event);
  }

  public static void filters() {
    logHeader("filters");

    KindList kindList = new KindList();
    kindList.add(Kind.EPHEMEREAL_EVENT.getValue());
    kindList.add(Kind.TEXT_NOTE.getValue());

    Filters filters = NIP01.createFilters(null, null, kindList, null, null, null, null, null, null);
    String subId = "subId" + System.currentTimeMillis();
    Nostr.send(filters, subId);
  }

  private static GenericEvent createChannel() {
    try {
      logHeader("createChannel");

      var channel = new ChannelProfile("JNostr Channel", "This is a channel to test NIP28 in nostr-java", "https://cdn.pixabay.com/photo/2020/05/19/13/48/cartoon-5190942_960_720.jpg");
      var event = NIP28.createChannelCreateEvent(channel);

      Nostr.sign(event);
      Nostr.send(event);

      return event;
    } catch (MalformedURLException | URISyntaxException ex) {
      throw new RuntimeException(ex);
    }
  }

  private static void updateChannelMetadata() {
    try {
      logHeader("updateChannelMetadata");

      var channelCreateEvent = createChannel();

      BaseTag tag = EventTag.builder()
          .idEvent(channelCreateEvent.getId())
          .recommendedRelayUrl("localhost:8080")
          .build();

      var channel = new ChannelProfile("test change name", "This is a channel to test NIP28 in nostr-java | changed", "https://cdn.pixabay.com/photo/2020/05/19/13/48/cartoon-5190942_960_720.jpg");
      var event = NIP28.createChannelCreateEvent(channel);
      event.addTag(tag);

      Nostr.sign(event);
      Nostr.send(event);
    } catch (MalformedURLException | URISyntaxException ex) {
      throw new RuntimeException(ex);
    }

  }

  private static GenericEvent sendChannelMessage() {
    logHeader("sendChannelMessage");

    var channelCreateEvent = createChannel();
    var event = NIP28.createChannelMessageEvent((ChannelCreateEvent) channelCreateEvent, "Hello everybody!");
    Nostr.sign(event);
    Nostr.send(event);

    return event;
  }

  private static GenericEventImpl hideMessage() {
    logHeader("hideMessage");

    var channelMessageEvent = sendChannelMessage();

    GenericEventImpl event = NIP28.createHideMessageEvent((ChannelMessageEvent) channelMessageEvent, "Dick pic");

    Nostr.sign(event);
    Nostr.send(event);

    return event;
  }

  private static GenericEventImpl muteUser() {
    logHeader("muteUser");

    GenericEventImpl event = NIP28.createMuteUserEvent(RECEIVER.getPublicKey(), "Posting dick pics");

    Nostr.sign(event);
    Nostr.send(event);

    return event;
  }

  private static void logAccountsData() {
    String msg = "################################ ACCOUNTS BEGINNING ################################" +
        '\n' + "*** RECEIVER ***" + '\n' +
        '\n' + "* PrivateKey: " + RECEIVER.getPrivateKey().getBech32() +
        '\n' + "* PrivateKey HEX: " + RECEIVER.getPrivateKey().toString() +
        '\n' + "* PublicKey: " + RECEIVER.getPublicKey().getBech32() +
        '\n' + "* PublicKey HEX: " + RECEIVER.getPublicKey().toString() +
        '\n' + '\n' + "*** SENDER ***" + '\n' +
        '\n' + "* PrivateKey: " + SENDER.getPrivateKey().getBech32() +
        '\n' + "* PrivateKey HEX: " + SENDER.getPrivateKey().toString() +
        '\n' + "* PublicKey: " + SENDER.getPublicKey().getBech32() +
        '\n' + "* PublicKey HEX: " + SENDER.getPublicKey().toString() +
        '\n' + '\n' + "################################ ACCOUNTS END ################################";

    log.log(Level.INFO, msg);
  }

  private static void logHeader(String header) {
    for (int i = 0; i < 30; i++) {
      System.out.print("#");
    }
    System.out.println();
    System.out.println("\t" + header);
    for (int i = 0; i < 30; i++) {
      System.out.print("#");
    }
    System.out.println();
  }

  private static void stop(ExecutorService executor) {
    try {
      executor.shutdown();
      executor.awaitTermination(60, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      log.log(Level.SEVERE, "termination interrupted");
    } finally {
      if (!executor.isTerminated()) {
        log.log(Level.SEVERE, "killing non-finished tasks");
      }
      executor.shutdownNow();
    }
  }
}
