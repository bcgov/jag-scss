package ca.bc.gov.open.scss;

import static org.mockito.Mockito.when;

import ca.bc.gov.open.scss.Controllers.CourtController;
import ca.bc.gov.open.scss.Controllers.FileController;
import ca.bc.gov.open.scss.Controllers.HealthController;
import ca.bc.gov.open.scss.Controllers.NotificationController;
import ca.bc.gov.open.scss.wsdl.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class OrdsErrorTests {

    @Mock private RestTemplate restTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testHealthPingOrdsFail() {
        HealthController healthController = new HealthController(restTemplate, objectMapper);

        // Set up to mock ords response
        setUpRestTemplate();

        try {
            healthController.getPing(new GetPing());
        } catch (Exception ex) {
            // Exception caught as expected
            assert true;
            return;
        }
        // Test should never reach here
        assert false;
    }

    @Test
    public void testHealthHealthOrdsFail() {
        HealthController healthController = new HealthController(restTemplate, objectMapper);

        // Set up to mock ords response
        setUpRestTemplate();

        try {
            healthController.getHealth(new GetHealth());
        } catch (Exception ex) {
            // Exception caught as expected
            assert true;
            return;
        }
        // Test should never reach here
        assert false;
    }

    @Test
    public void testCourtgetCourtFileOrdsFail() {
        CourtController controller = new CourtController(restTemplate, objectMapper);

        // Set up to mock ords response
        setUpRestTemplate();

        try {
            controller.getCourtFile(new GetCourtFile());
        } catch (Exception ex) {
            // Exception caught as expected
            assert true;
            return;
        }
        // Test should never reach here
        assert false;
    }

    @Test
    public void testCourtgetCourtBasicsOrdsFail() {
        CourtController controller = new CourtController(restTemplate, objectMapper);

        // Set up to mock ords response
        setUpRestTemplate();

        try {
            controller.getCourtBasics(new GetCourtBasics());
        } catch (Exception ex) {
            // Exception caught as expected
            assert true;
            return;
        }
        // Test should never reach here
        assert false;
    }

    @Test
    public void testCourtgetCeisConnectInfoOrdsFail() {
        CourtController controller = new CourtController(restTemplate, objectMapper);

        // Set up to mock ords response
        setUpRestTemplate();

        try {
            controller.getCeisConnectInfo(new GetCeisConnectInfo());
        } catch (Exception ex) {
            // Exception caught as expected
            assert true;
            return;
        }
        // Test should never reach here
        assert false;
    }

    @Test
    public void testCourtgetPartiesOrdsFail() {
        CourtController controller = new CourtController(restTemplate, objectMapper);

        // Set up to mock ords response
        setUpRestTemplate();

        try {
            controller.getParties(new GetParties());
        } catch (Exception ex) {
            // Exception caught as expected
            assert true;
            return;
        }
        // Test should never reach here
        assert false;
    }

    @Test
    public void testCourtpartyNameSearchNullFilterl() {
        CourtController controller = new CourtController(restTemplate, objectMapper);

        // Set up to mock ords response
        setUpRestTemplate();

        try {
            controller.partyNameSearch(new PartyNameSearch());
        } catch (Exception ex) {
            // Exception caught as expected
            assert true;
            return;
        }
        // Test should never reach here
        assert false;
    }

    @Test
    public void testCourtpartyNameSearchOrdsFail() {
        CourtController controller = new CourtController(restTemplate, objectMapper);

        // Set up to mock ords response
        setUpRestTemplate();

        var search = new PartyNameSearch();
        search.setFilter(new PartyNameSearchFilter());

        try {
            controller.partyNameSearch(search);
        } catch (Exception ex) {
            // Exception caught as expected
            assert true;
            return;
        }
        // Test should never reach here
        assert false;
    }

    @Test
    public void testCourtsaveHearingResultsOrdsFail() {
        CourtController controller = new CourtController(restTemplate, objectMapper);

        // Set up to mock ords response
        setUpRestTemplate();

        try {
            controller.saveHearingResults(new SaveHearingResults());
        } catch (Exception ex) {
            // Exception caught as expected
            assert true;
            return;
        }
        // Test should never reach here
        assert false;
    }

    @Test
    public void testFilefileNumberSearchOrdsFail() {
        FileController controller = new FileController(restTemplate, objectMapper);

        // Set up to mock ords response
        setUpRestTemplate();

        try {
            controller.fileNumberSearch(new FileNumberSearch());
        } catch (Exception ex) {
            // Exception caught as expected
            assert true;
            return;
        }
        // Test should never reach here
        assert false;
    }

    @Test
    public void testFilelinkFileOrdsFail() {
        FileController controller = new FileController(restTemplate, objectMapper);

        // Set up to mock ords response
        setUpRestTemplate();

        try {
            controller.linkFile(new LinkFile());
        } catch (Exception ex) {
            // Exception caught as expected
            assert true;
            return;
        }
        // Test should never reach here
        assert false;
    }

    @Test
    public void testFileunlinkFileOrdsFail() {
        FileController controller = new FileController(restTemplate, objectMapper);

        // Set up to mock ords response
        setUpRestTemplate();

        try {
            controller.unlinkFile(new UnlinkFile());
        } catch (Exception ex) {
            // Exception caught as expected
            assert true;
            return;
        }
        // Test should never reach here
        assert false;
    }

    @Test
    public void testFilefileNumberSearchPublicAccessOrdsFail() {
        FileController controller = new FileController(restTemplate, objectMapper);

        // Set up to mock ords response
        setUpRestTemplate();

        try {
            controller.fileNumberSearchPublicAccess(new FileNumbeSearchPublicAccess());
        } catch (Exception ex) {
            // Exception caught as expected
            assert true;
            return;
        }
        // Test should never reach here
        assert false;
    }

    @Test
    public void testNotificationgetAllNotificationsOrdsFail() {
        NotificationController controller = new NotificationController(restTemplate, objectMapper);

        // Set up to mock ords response
        setUpRestTemplate();

        try {
            controller.getAllNotifications(new GetAllNotifications());
        } catch (Exception ex) {
            // Exception caught as expected
            assert true;
            return;
        }
        // Test should never reach here
        assert false;
    }

    @Test
    public void testNotificationgetNotificationOrdsFail() {
        NotificationController controller = new NotificationController(restTemplate, objectMapper);

        // Set up to mock ords response
        setUpRestTemplate();

        try {
            controller.getNotification(new GetNotifications());
        } catch (Exception ex) {
            // Exception caught as expected
            assert true;
            return;
        }
        // Test should never reach here
        assert false;
    }

    @Test
    public void testNotificationhasNotificationsOrdsFail() {
        NotificationController controller = new NotificationController(restTemplate, objectMapper);

        // Set up to mock ords response
        setUpRestTemplate();

        try {
            controller.hasNotifications(new HasNotification());
        } catch (Exception ex) {
            // Exception caught as expected
            assert true;
            return;
        }
        // Test should never reach here
        assert false;
    }

    @Test
    public void testNotificationremoveNotificationOrdsFail() {
        NotificationController controller = new NotificationController(restTemplate, objectMapper);

        // Set up to mock ords response
        setUpRestTemplate();

        try {
            controller.removeNotification(new RemoveNotification());
        } catch (Exception ex) {
            // Exception caught as expected
            assert true;
            return;
        }
        // Test should never reach here
        assert false;
    }

    private void setUpRestTemplate() {
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.GET),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<GetCourtFileResponse>>any()))
                .thenThrow(new RestClientException("BAD"));

        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.POST),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<GetCourtFileResponse>>any()))
                .thenThrow(new RestClientException("BAD"));

        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.PUT),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<GetCourtFileResponse>>any()))
                .thenThrow(new RestClientException("BAD"));

        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.DELETE),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<GetCourtFileResponse>>any()))
                .thenThrow(new RestClientException("BAD"));
    }
}
