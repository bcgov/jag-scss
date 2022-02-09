package ca.bc.gov.open.scss;

import static org.mockito.Mockito.when;

import ca.bc.gov.open.scss.controllers.CourtController;
import ca.bc.gov.open.scss.models.serializers.InstantDeserializer;
import ca.bc.gov.open.scss.models.serializers.InstantSerializer;
import ca.bc.gov.open.scss.wsdl.*;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Locale;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CourtControllerTests {

    private CourtController courtController;

    @Autowired private ObjectMapper objectMapper;

    @Mock private RestTemplate restTemplate;

    @Test
    public void getCourtFileTest() throws IOException {
        // Init service under test
        courtController = new CourtController(restTemplate, objectMapper);

        // Init request object
        var req = new GetCourtFile();
        req.setPhysicalFileId(BigDecimal.ONE);

        // Init response
        var resp = new GetCourtFileResponse();
        CourtFile cf = new CourtFile();
        cf.setCourtClassCode("A");
        cf.setCourtLevelCode("A");
        cf.setCourtFileNumber("A");
        cf.setLocationId(BigDecimal.ONE);
        cf.setPhysicalFileId(BigDecimal.ONE);
        cf.setStyleOfCause("A");
        resp.setCourtFile(cf);

        ResponseEntity<GetCourtFileResponse> responseEntity =
                new ResponseEntity<>(resp, HttpStatus.OK);

        // Set up to mock ords response
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.GET),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<GetCourtFileResponse>>any()))
                .thenReturn(responseEntity);

        // Do request
        var out = courtController.getCourtFile(req);

        // Assert response is correct
        Assertions.assertEquals(resp, out);
        // Assertions.assertNotNull(out);
    }

    @Test
    public void getCourtBasicsTest() throws IOException {
        // Init service under test
        courtController = new CourtController(restTemplate, objectMapper);

        // Init request object
        var req = new GetCourtBasics();
        req.setPhysicalFileId(BigDecimal.ONE);

        // Init response
        CaseBasics cb = new CaseBasics();
        cb.setCourtClassCode("A");
        cb.setCourtLevelCode("A");
        cb.setPhysicalFileId(BigDecimal.ONE);
        Issue is = new Issue();
        is.setIssueDescription("A");
        is.setIssueTypeCode("A");
        cb.setIssues(Collections.singletonList(is));
        cb.setFileAccessLevelCode("A");
        cb.setLocationId(BigDecimal.ONE);

        ResponseEntity<CaseBasics> responseEntity = new ResponseEntity<>(cb, HttpStatus.OK);

        // Set up to mock ords response
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.GET),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<CaseBasics>>any()))
                .thenReturn(responseEntity);

        // Do request
        var out = courtController.getCourtBasics(req);

        // Assert response is correct
        Assertions.assertEquals(cb, out.getCaseBasics());
    }

    @Test
    public void getCeisConnectInfoTest() throws IOException {
        // Init service under test
        courtController = new CourtController(restTemplate, objectMapper);

        // Init request object
        var req = new GetCeisConnectInfo();

        var resp = new GetCeisConnectInfoResponse();
        resp.setConnectionInfo("A");

        ResponseEntity<GetCeisConnectInfoResponse> responseEntity =
                new ResponseEntity<>(resp, HttpStatus.OK);

        // Set up to mock ords response
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.GET),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<GetCeisConnectInfoResponse>>any()))
                .thenReturn(responseEntity);

        // Do request
        var out = courtController.getCeisConnectInfo(req);

        // Assert response is correct
        Assertions.assertEquals(resp, out);
    }

    @Test
    public void getPartiesTest() throws IOException {
        // Init service under test
        courtController = new CourtController(restTemplate, objectMapper);

        // Init request object
        var req = new GetParties();
        req.setPhysicalFileId(BigDecimal.ONE);

        // Init response
        var resp = new GetPartiesResponse();
        CaseParty cp = new CaseParty();
        cp.setActive(true);
        cp.setFirstName("A");
        cp.setAddressFirstLine("A");
        cp.setCity("A");
        cp.setAddressSecondLine("A");
        cp.setCounselName("A");
        cp.setCounselPhoneNumber("A");
        cp.setOrganizationName("A");
        cp.setPartyId(BigDecimal.ONE);
        cp.setExtensionNumber("A");
        cp.setPartyRoleCode("A");
        cp.setPartyTypeCode("A");
        cp.setPostalCode("A");
        cp.setSelfRepresented(true);
        cp.setSurname("A");
        cp.setProvince("A");
        cp.setPhoneNumber("A");
        resp.setParties(Collections.singletonList(cp));

        ResponseEntity<GetPartiesResponse> responseEntity =
                new ResponseEntity<>(resp, HttpStatus.OK);

        // Set up to mock ords response
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.GET),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<GetPartiesResponse>>any()))
                .thenReturn(responseEntity);

        // Do request
        var out = courtController.getParties(req);

        // Assert response is correct
        Assertions.assertEquals(resp, out);
    }

    @Test
    public void partyNameSearchTest() throws IOException {
        // Init service under test
        courtController = new CourtController(restTemplate, objectMapper);

        // Init request object
        var req = new PartyNameSearch();
        PartyNameSearchFilter pnf = new PartyNameSearchFilter();
        pnf.setCourtClass("A");
        pnf.setAgencyId(BigDecimal.ONE);
        pnf.setSearchType("A");
        pnf.setFirstName("A");
        pnf.setCourtLevel("A");
        pnf.setPage(BigDecimal.ONE);
        pnf.setName("A");
        pnf.setRoleType("A");
        req.setFilter(pnf);

        // Init response
        SearchResults res = new SearchResults();
        res.setPage(BigDecimal.ONE);
        res.setRecordsPerPage(BigDecimal.ONE);
        res.setTotalRecords(BigDecimal.ONE);
        res.setResults(Collections.singletonList(new CourtFile()));

        ResponseEntity<SearchResults> responseEntity = new ResponseEntity<>(res, HttpStatus.OK);

        // Set up to mock ords response
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.GET),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<SearchResults>>any()))
                .thenReturn(responseEntity);

        // Do request
        var out = courtController.partyNameSearch(req);

        // Assert response is correct
        Assertions.assertEquals(res, out.getSearchResults());
    }

    @Test
    public void partyNameSearchNullFilterTest() throws IOException {
        // Init service under test
        courtController = new CourtController(restTemplate, objectMapper);

        // Init request object
        var req = new PartyNameSearch();

        // Init response
        SearchResults res = new SearchResults();
        res.setPage(BigDecimal.ONE);
        res.setResults(Collections.singletonList(new CourtFile()));
        res.setRecordsPerPage(BigDecimal.ONE);
        res.setTotalRecords(BigDecimal.ONE);

        ResponseEntity<SearchResults> responseEntity = new ResponseEntity<>(res, HttpStatus.OK);

        // Set up to mock ords response
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.GET),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<SearchResults>>any()))
                .thenReturn(responseEntity);

        // Do request
        var out = courtController.partyNameSearch(req);

        // Assert response is correct
        Assertions.assertNotNull(out);
    }

    @Test
    public void saveHearingResultTest() throws IOException {
        // Init service under test
        courtController = new CourtController(restTemplate, objectMapper);

        var req = new SaveHearingResults();
        var res = new HearingResult();
        var res2 = new HearingResult2();
        var cd = new CaseDetails();
        cd.setCaseFiling("A");
        cd.setCaseTrackingID(BigDecimal.ONE);

        var ca = new CaseAugmentation();
        var ch = new CaseHearing();
        var ea = new CourtEventAppearance();
        ea.setActivityStatus("A");
        ea.setCourtAppearanceCourt("A");
        ea.setActivityStatus("A");
        ea.setCancellationStatus("A");
        ea.setCourtAppearanceDate(Instant.now());
        ea.setCourtAppearanceCategoryText("A");
        ea.setCourtEventSequenceID("A");

        var tm = new TimeMeasureDetails();
        tm.setMeasureText(BigDecimal.ONE);
        tm.setMeasureEstimatedIndicator(true);
        tm.setMeasureUnitText("A");

        // Wrap into the request
        ea.setTimeMeasureDetails(tm);
        ch.setCourtEventAppearance(ea);
        ca.setCaseHearing(ch);
        cd.setCaseAugmentation(ca);
        res2.setCaseDetails(cd);
        res.setHearingResult(res2);
        req.setHearingResult(res);

        ResponseEntity<String> responseEntity = new ResponseEntity<>("", HttpStatus.OK);

        // Set up to mock ords response
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.POST),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<String>>any()))
                .thenReturn(responseEntity);

        // Do request
        var out = courtController.saveHearingResults(req);

        // Assert response is correct
        Assertions.assertNotNull(out);
    }

    @Test
    public void saveHearingResultTestBadDate() throws IOException {
        // Init service under test
        courtController = new CourtController(restTemplate, objectMapper);

        var req = new SaveHearingResults();
        var res = new HearingResult();
        var res2 = new HearingResult2();
        var cd = new CaseDetails();
        cd.setCaseFiling("A");
        cd.setCaseTrackingID(BigDecimal.ONE);

        var ca = new CaseAugmentation();
        var ch = new CaseHearing();
        var ea = new CourtEventAppearance();
        ea.setActivityStatus("A");
        ea.setCourtAppearanceCourt("A");
        ea.setActivityStatus("A");
        ea.setCancellationStatus("A");
        ea.setCourtAppearanceDate(Instant.now());
        ea.setCourtAppearanceCategoryText("A");
        ea.setCourtEventSequenceID("A");

        var tm = new TimeMeasureDetails();
        tm.setMeasureText(BigDecimal.ONE);
        tm.setMeasureEstimatedIndicator(true);
        tm.setMeasureUnitText("A");

        // Wrap into the request
        ea.setTimeMeasureDetails(tm);
        ch.setCourtEventAppearance(ea);
        ca.setCaseHearing(ch);
        cd.setCaseAugmentation(ca);
        res2.setCaseDetails(cd);
        res.setHearingResult(res2);
        req.setHearingResult(res);

        ResponseEntity<String> responseEntity = new ResponseEntity<>("", HttpStatus.OK);

        // Set up to mock ords response
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.POST),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<String>>any()))
                .thenReturn(responseEntity);

        // Do request
        var out = courtController.saveHearingResults(req);

        // Assert response is correct
        Assertions.assertNotNull(out);
    }

    @Test
    public void testInstantSerializer() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Instant.class, new InstantDeserializer());
        module.addSerializer(Instant.class, new InstantSerializer());
        objectMapper.registerModule(module);

        var time = Instant.now();
        String out = objectMapper.writeValueAsString(time);

        String expected =
                DateTimeFormatter.ofPattern("dd-MMM-yyyy")
                        .withZone(ZoneId.of("GMT-7"))
                        .withLocale(Locale.US)
                        .format(time);

        out = out.replace("\"", "");
        Assertions.assertEquals(expected, out);
    }
}
