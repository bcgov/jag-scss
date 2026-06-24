package ca.bc.gov.open.scss.models.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InstantDeserializer extends JsonDeserializer<Instant> {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @Override
    public Instant deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        try {
            var sdf = new SimpleDateFormat("dd-MMM-yy hh.mm.ss.SSSSSS a", Locale.US);
            sdf.setTimeZone(TimeZone.getTimeZone("GMT-7"));
            Date d = sdf.parse(jsonParser.getText());
            return d.toInstant();
        } catch (ParseException e) {
            log.error("Could not parse date {}", jsonParser.getText());
            return null;
        }
    }
}
