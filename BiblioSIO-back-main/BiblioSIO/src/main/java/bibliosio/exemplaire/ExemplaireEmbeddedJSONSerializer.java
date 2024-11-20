package bibliosio.exemplaire;

import bibliosio.emprunteur.Emprunteur;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class ExemplaireEmbeddedJSONSerializer {
    public void serialize(Exemplaire exemplaire, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("titre", exemplaire.getTitre());
        gen.writeStringField("url","/exemplaires/" + exemplaire.getId());
        gen.writeEndObject();
    }
}
