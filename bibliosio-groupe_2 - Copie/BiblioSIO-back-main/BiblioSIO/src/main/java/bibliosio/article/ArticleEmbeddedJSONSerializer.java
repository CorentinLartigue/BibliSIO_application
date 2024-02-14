package bibliosio.article;

import bibliosio.emprunteur.Emprunteur;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class ArticleEmbeddedJSONSerializer {
    public void serialize(Article article, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("titre", article.getTitre());
        gen.writeStringField("url","/articles/" + article.getId());
        gen.writeEndObject();
    }
}
