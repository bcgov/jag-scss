package ca.bc.gov.open.scss;

import static org.mockito.Mockito.when;

import ca.bc.gov.open.scss.controllers.HealthController;
import ca.bc.gov.open.scss.wsdl.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class HealthControllerTests {

    @Mock private RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void getHealthTest() throws IOException {
        HealthController healthController = new HealthController(restTemplate, objectMapper);

        var resp = new GetHealthResponse();
        resp.setAppid("A");
        resp.setCompatibility("A");
        resp.setHost("A");
        resp.setMethod("A");
        resp.setVersion("A");
        resp.setInstance("A");
        resp.setStatus("A");

        ResponseEntity<GetHealthResponse> responseEntity =
                new ResponseEntity<>(resp, HttpStatus.OK);

        // Set up to mock ords response
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.GET),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<GetHealthResponse>>any()))
                .thenReturn(responseEntity);

        GetHealthResponse out = healthController.getHealth(new GetHealth());

        Assertions.assertNotNull(out);
    }

    @Test
    public void getPingTest() {
        // Only needed for log test otherwise required refactor
        HealthController healthController = new HealthController(restTemplate, objectMapper);

        var resp = new GetPingResponse();
        resp.setStatus("A");

        ResponseEntity<GetPingResponse> responseEntity = new ResponseEntity<>(resp, HttpStatus.OK);

        // Set up to mock ords response
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.GET),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<GetPingResponse>>any()))
                .thenReturn(responseEntity);

        GetPingResponse out = healthController.getPing(new GetPing());

        Assertions.assertNotNull(out);
    }
}
