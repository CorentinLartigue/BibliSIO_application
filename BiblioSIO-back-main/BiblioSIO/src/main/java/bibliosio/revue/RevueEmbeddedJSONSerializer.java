package bibliosio.revue;

import bibliosio.revue.Revue;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class RevueEmbeddedJSONSerializer {
    public void serialize(Revue revue, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("titre", revue.getTitre());
        gen.writeStringField("url","/revues/" + revue.getId());
        gen.writeEndObject();
    }
}
