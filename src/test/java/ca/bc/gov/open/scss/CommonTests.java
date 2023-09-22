package ca.bc.gov.open.scss;

import ca.bc.gov.open.scss.wsdl.Notification;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class CommonTests {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @Test
    public void InstantDeserializerTest() throws JsonProcessingException, ParseException {
        String instantString = "{\"statusDatetime\": \"05-NOV-12 08.05.59.00000 AM\"}";

        Notification inst = objectMapper.readValue(instantString, Notification.class);

        String strReqDelTime = "05-NOV-12 08.05.59.00000 AM";
        var sdf = new SimpleDateFormat("dd-MMM-yy hh.mm.ss.SSSSSS a", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-7"));
        Date d = sdf.parse(strReqDelTime);
        Instant reqInstant = d.toInstant();

        assert inst.getStatusDatetime().compareTo(reqInstant) == 0;

        instantString = "{\"statusDatetime\": \"I am bad\"}";

        inst = objectMapper.readValue(instantString, Notification.class);

        Assertions.assertNull(inst.getStatusDatetime());
    }

    @Test
    public void securityTestFail_Then401() throws Exception {
        var response =
                mockMvc.perform(post("/ws").contentType(MediaType.TEXT_XML))
                        .andExpect(status().is4xxClientError())
                        .andReturn();
        Assertions.assertEquals(
                HttpStatus.UNAUTHORIZED.value(), response.getResponse().getStatus());
    }
}
