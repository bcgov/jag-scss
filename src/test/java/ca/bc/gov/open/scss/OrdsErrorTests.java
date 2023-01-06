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
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class OrdsErrorTests {
    @Autowired private MockMvc mockMvc;

    @Autowired private ObjectMapper objectMapper;

    @Mock private RestTemplate restTemplate;
    @Mock private HttpHeaders ordsHeader;

    @Test
    public void testPingOrdsFail() {
        HealthController healthController =
                new HealthController(restTemplate, objectMapper, ordsHeader);

        Assertions.assertThrows(ORDSException.class, () -> healthController.getPing(new GetPing()));
    }

    @Test
    public void testHealthOrdsFail() {
        HealthController healthController =
                new HealthController(restTemplate, objectMapper, ordsHeader);

        Assertions.assertThrows(
                ORDSException.class, () -> healthController.getHealth(new GetHealth()));
    }

    @Test
    public void testCourtGetCourtFileOrdsFail() {
        CourtController controller = new CourtController(restTemplate, objectMapper, ordsHeader);

        Assertions.assertThrows(
                ORDSException.class, () -> controller.getCourtFile(new GetCourtFile()));
    }

    @Test
    public void testCourtGetCourtBasicsOrdsFail() {
        CourtController controller = new CourtController(restTemplate, objectMapper, ordsHeader);

        Assertions.assertThrows(
                ORDSException.class, () -> controller.getCourtBasics(new GetCourtBasics()));
    }

    @Test
    public void testCourtGetCeisConnectInfoOrdsFail() {
        CourtController controller = new CourtController(restTemplate, objectMapper, ordsHeader);

        Assertions.assertThrows(
                ORDSException.class, () -> controller.getCeisConnectInfo(new GetCeisConnectInfo()));
    }

    @Test
    public void testCourtGetPartiesOrdsFail() {
        CourtController controller = new CourtController(restTemplate, objectMapper, ordsHeader);

        Assertions.assertThrows(ORDSException.class, () -> controller.getParties(new GetParties()));
    }

    @Test
    public void testCourtPartyNameSearchNullFilter() {
        CourtController controller = new CourtController(restTemplate, objectMapper, ordsHeader);

        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.GET),
                        Mockito.<HttpEntity<SearchResults>>any(),
                        Mockito.<Class<String>>any()))
                .thenThrow(RestClientException.class);

        Assertions.assertThrows(
                ORDSException.class, () -> controller.partyNameSearch(new PartyNameSearch()));
    }

    @Test
    public void testCourtPartyNameSearchOrdsFail() {
        CourtController controller = new CourtController(restTemplate, objectMapper, ordsHeader);

        var search = new PartyNameSearch();
        search.setFilter(new PartyNameSearchFilter());

        Assertions.assertThrows(ORDSException.class, () -> controller.partyNameSearch(search));
    }

    @Test
    public void testCourtSaveHearingResultsOrdsFail() {
        CourtController controller = new CourtController(restTemplate, objectMapper, ordsHeader);
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

        Assertions.assertThrows(ORDSException.class, () -> controller.saveHearingResults(req));
    }

    @Test
    public void testCourtSaveHearingResultsEmptyReqFail() {
        CourtController controller = new CourtController(restTemplate, objectMapper, ordsHeader);

        Assertions.assertThrows(
                ORDSException.class, () -> controller.saveHearingResults(new SaveHearingResults()));
    }

    @Test
    public void testFileFileNumberSearchOrdsFail() {
        FileController controller = new FileController(restTemplate, objectMapper, ordsHeader);

        Assertions.assertThrows(
                ORDSException.class, () -> controller.fileNumberSearch(new FileNumberSearch()));
    }

    @Test
    public void testFileLinkFileOrdsFail() {
        FileController controller = new FileController(restTemplate, objectMapper, ordsHeader);

        Assertions.assertThrows(ORDSException.class, () -> controller.linkFile(new LinkFile()));
    }

    @Test
    public void testFileUnlinkFileOrdsFail() {
        FileController controller = new FileController(restTemplate, objectMapper, ordsHeader);

        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.PUT),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<HashMap>>any()))
                .thenThrow(RestClientException.class);

        Assertions.assertThrows(ORDSException.class, () -> controller.unlinkFile(new UnlinkFile()));
    }

    @Test
    public void testFileFileNumberSearchPublicAccessOrdsFail() {
        FileController controller = new FileController(restTemplate, objectMapper, ordsHeader);

        Assertions.assertThrows(
                ORDSException.class,
                () -> controller.fileNumberSearchPublicAccess(new FileNumbeSearchPublicAccess()));
    }

    @Test
    public void testNotificationGetAllNotificationsOrdsFail() {
        NotificationController controller =
                new NotificationController(restTemplate, objectMapper, ordsHeader);

        Assertions.assertThrows(
                ORDSException.class,
                () -> controller.getAllNotifications(new GetAllNotifications()));
    }

    @Test
    public void testNotificationGetNotificationOrdsFail() {
        NotificationController controller =
                new NotificationController(restTemplate, objectMapper, ordsHeader);

        Assertions.assertThrows(
                ORDSException.class, () -> controller.getNotification(new GetNotifications()));
    }

    @Test
    public void testNotificationHasNotificationsOrdsFail() {
        NotificationController controller =
                new NotificationController(restTemplate, objectMapper, ordsHeader);

        Assertions.assertThrows(
                ORDSException.class, () -> controller.hasNotifications(new HasNotification()));
    }

    @Test
    public void testNotificationRemoveNotificationOrdsFail() {
        NotificationController controller =
                new NotificationController(restTemplate, objectMapper, ordsHeader);

        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.DELETE),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<HashMap>>any()))
                .thenThrow(RestClientException.class);

        Assertions.assertThrows(
                ORDSException.class, () -> controller.removeNotification(new RemoveNotification()));
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
