package ca.bc.gov.open.scss.models.serializers;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

public class InstantSerializer extends ValueSerializer<Instant> {
    @Override
    public void serialize(Instant value, JsonGenerator gen, SerializationContext serializers) {
        String out =
                DateTimeFormatter.ofPattern("dd-MMM-yyyy")
                        .withZone(ZoneId.of("GMT-7"))
                        .withLocale(Locale.US)
                        .format(value);
        gen.writeString(out);
    }
}
