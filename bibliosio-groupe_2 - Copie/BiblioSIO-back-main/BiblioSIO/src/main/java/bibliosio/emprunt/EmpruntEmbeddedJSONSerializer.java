package bibliosio.emprunt;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class EmpruntEmbeddedJSONSerializer extends JsonSerializer<Emprunt> {
    @Override
    public void serialize(Emprunt emprunt, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("url","/emprunts/" + emprunt.getId());
        gen.writeEndObject();
    }
}
