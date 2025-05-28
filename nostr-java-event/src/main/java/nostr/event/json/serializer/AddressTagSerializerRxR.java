//package nostr.event.json.serializer;
//
//import com.fasterxml.jackson.core.JsonGenerator;
//import com.fasterxml.jackson.databind.JsonSerializer;
//import com.fasterxml.jackson.databind.SerializerProvider;
//import java.io.IOException;
//import nostr.event.tag.AddressTagRxR;
//
//public class AddressTagSerializerRxR extends JsonSerializer<AddressTagRxR> {
//    @Override
//    public void serialize(AddressTagRxR value, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
//        jsonGenerator.writeStartArray();
//        jsonGenerator.writeString("a");
//        jsonGenerator.writeString(
//            value.getKind() + ":" +
//                value.getPublicKey().toString() + ":"
//        );
//
//        if (value.getIdentifierTag() != null) {
//            jsonGenerator.writeString(value.getIdentifierTag().getUuid());
//        }
//
//        if (value.getRelay() != null) {
//            jsonGenerator.writeString("\",\"" + value.getRelay().getUri());
//        }
//        jsonGenerator.writeEndArray();
//    }
//
//}
