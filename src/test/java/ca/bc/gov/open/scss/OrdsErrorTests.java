package ca.bc.gov.open.scss;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ca.bc.gov.open.scss.controllers.CourtController;
import ca.bc.gov.open.scss.controllers.FileController;
import ca.bc.gov.open.scss.controllers.HealthController;
import ca.bc.gov.open.scss.controllers.NotificationController;
import ca.bc.gov.open.scss.exceptions.ORDSException;
import ca.bc.gov.open.scss.wsdl.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import java.util.HashMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrdsErrorTests {
    @Mock private ObjectMapper objectMapper;
    @Mock private RestTemplate restTemplate;
    @Mock private HealthController healthController;
    @Mock private CourtController courtController;
    @Mock private FileController fileController;
    @Mock private NotificationController notificationController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        healthController = Mockito.spy(new HealthController(restTemplate, objectMapper));
        courtController = Mockito.spy(new CourtController(restTemplate, objectMapper));
        fileController = Mockito.spy(new FileController(restTemplate, objectMapper));
        notificationController = Mockito.spy(new NotificationController(restTemplate, objectMapper));
    }

    @Test
    public void testPingOrdsFail() {
        Assertions.assertThrows(ORDSException.class, () -> healthController.getPing(new GetPing()));
    }

    @Test
    public void testHealthOrdsFail() {
        Assertions.assertThrows(
                ORDSException.class, () -> healthController.getHealth(new GetHealth()));
    }

    @Test
    public void testCourtGetCourtFileOrdsFail() {
        Assertions.assertThrows(
                ORDSException.class, () -> courtController.getCourtFile(new GetCourtFile()));
    }

    @Test
    public void testCourtGetCourtBasicsOrdsFail() {
          Assertions.assertThrows(
                ORDSException.class, () -> courtController.getCourtBasics(new GetCourtBasics()));
    }

    @Test
    public void testCourtGetCeisConnectInfoOrdsFail() {
        Assertions.assertThrows(
                ORDSException.class, () -> courtController.getCeisConnectInfo(new GetCeisConnectInfo()));
    }

    @Test
    public void testCourtGetPartiesOrdsFail() {
        Assertions.assertThrows(ORDSException.class, () -> courtController.getParties(new GetParties()));
    }

    @Test
    public void testCourtPartyNameSearchNullFilter() {
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.GET),
                        Mockito.<HttpEntity<SearchResults>>any(),
                        Mockito.<Class<String>>any()))
                .thenThrow(RestClientException.class);

        Assertions.assertThrows(
                ORDSException.class, () -> courtController.partyNameSearch(new PartyNameSearch()));
    }

    @Test
    public void testCourtPartyNameSearchOrdsFail() {
        var search = new PartyNameSearch();
        search.setFilter(new PartyNameSearchFilter());

        Assertions.assertThrows(ORDSException.class, () -> courtController.partyNameSearch(search));
    }

    @Test
    public void testCourtSaveHearingResultsOrdsFail() {
        var req = new SaveHearingResults();
        HearingResult hr = new HearingResult();
        HearingResult2 hr2 = new HearingResult2();
        CaseDetails cd = new CaseDetails();
        CaseAugmentation ca = new CaseAugmentation();
        CaseHearing ch = new CaseHearing();
        CourtEventAppearance cea = new CourtEventAppearance();
        cea.setCourtAppearanceDate(Instant.now());
        cea.setActivityStatus("A");
        cea.setCancellationStatus("A");
        cea.setCourtAppearanceCourt("A");
        ch.setCourtEventAppearance(cea);
        ca.setCaseHearing(ch);
        cd.setCaseAugmentation(ca);
        hr2.setCaseDetails(cd);
        hr.setHearingResult(hr2);
        req.setHearingResult(hr);

        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.POST),
                        Mockito.<HttpEntity<CaseDetails>>any(),
                        Mockito.<Class<String>>any()))
                .thenThrow(RestClientException.class);

        Assertions.assertThrows(ORDSException.class, () -> courtController.saveHearingResults(req));
    }

    @Test
    public void testCourtSaveHearingResultsEmptyReqFail() {
        Assertions.assertThrows(
                ORDSException.class, () -> courtController.saveHearingResults(new SaveHearingResults()));
    }

    @Test
    public void testFileFileNumberSearchOrdsFail() {
        Assertions.assertThrows(
                ORDSException.class, () -> fileController.fileNumberSearch(new FileNumberSearch()));
    }

    @Test
    public void testFileLinkFileOrdsFail() {
        Assertions.assertThrows(ORDSException.class, () -> fileController.linkFile(new LinkFile()));
    }

    @Test
    public void testFileUnlinkFileOrdsFail() {
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.PUT),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<HashMap>>any()))
                .thenThrow(RestClientException.class);

        Assertions.assertThrows(ORDSException.class, () -> fileController.unlinkFile(new UnlinkFile()));
    }

    @Test
    public void testFileFileNumberSearchPublicAccessOrdsFail() {
        Assertions.assertThrows(
                ORDSException.class,
                () -> fileController.fileNumberSearchPublicAccess(new FileNumbeSearchPublicAccess()));
    }

    @Test
    public void testNotificationGetAllNotificationsOrdsFail() {
        Assertions.assertThrows(
                ORDSException.class,
                () -> notificationController.getAllNotifications(new GetAllNotifications()));
    }

    @Test
    public void testNotificationGetNotificationOrdsFail() {
        Assertions.assertThrows(
                ORDSException.class, () -> notificationController.getNotification(new GetNotifications()));
    }

    @Test
    public void testNotificationHasNotificationsOrdsFail() {
        Assertions.assertThrows(
                ORDSException.class, () -> notificationController.hasNotifications(new HasNotification()));
    }

    @Test
    public void testNotificationRemoveNotificationOrdsFail() {
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.DELETE),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<HashMap>>any()))
                .thenThrow(RestClientException.class);

        Assertions.assertThrows(
                ORDSException.class, () -> notificationController.removeNotification(new RemoveNotification()));
    }
}
