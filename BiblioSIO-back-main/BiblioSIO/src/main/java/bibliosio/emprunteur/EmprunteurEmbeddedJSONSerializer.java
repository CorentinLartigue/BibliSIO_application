package bibliosio.emprunteur;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class EmprunteurEmbeddedJSONSerializer  extends JsonSerializer<Emprunteur> {
    @Override
    public void serialize(Emprunteur emprunteur, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("nom", emprunteur.getNom());
        gen.writeStringField("url","/emprunteurs/" + emprunteur.getId());
        gen.writeEndObject();
    }
}
