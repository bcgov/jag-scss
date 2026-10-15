package ca.bc.gov.open.scss.models.serializers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;

public class InstantDeserializer extends ValueDeserializer<Instant> {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @Override
    public Instant deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) {
        try {
            var sdf = new SimpleDateFormat("dd-MMM-yy hh.mm.ss.SSSSSS a", Locale.US);
            sdf.setTimeZone(TimeZone.getTimeZone("GMT-7"));
            Date d = sdf.parse(jsonParser.getString());
            return d.toInstant();
        } catch (ParseException e) {
            log.error("Could not parse date {}", jsonParser.getString());
            return null;
        }
    }
}
