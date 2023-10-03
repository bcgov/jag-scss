package ca.bc.gov.open.scss;

import static org.mockito.Mockito.when;

import ca.bc.gov.open.scss.controllers.FileController;
import ca.bc.gov.open.scss.wsdl.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FileControllerTests {

    @Mock FileController fileController;
    @Mock private ObjectMapper objectMapper;
    @Mock private RestTemplate restTemplate = new RestTemplate();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        fileController = Mockito.spy(new FileController(restTemplate, objectMapper));
    }

    @Test
    public void fileNumberSearchTest() throws IOException {
        // Init request object
        var req = new FileNumberSearch();
        req.setFilter(new FileNumberSearchFilter());
        req.getFilter().setCourtClassCode("C");
        req.getFilter().setCourtFileNumber("V");
        req.getFilter().setCourtLevelCode("S");
        req.getFilter().setLocationId(new BigDecimal(1));

        // Init response
        var resp = new FileNumberSearchResponse();
        CourtFile cf = new CourtFile();
        cf.setCourtClassCode("A");
        cf.setCourtLevelCode("A");
        cf.setCourtFileNumber("A");
        cf.setLocationId(BigDecimal.ONE);
        cf.setPhysicalFileId(BigDecimal.ONE);
        cf.setStyleOfCause("A");
        resp.getCourtFiles().add(cf);

        ResponseEntity<FileNumberSearchResponse> responseEntity =
                new ResponseEntity<>(resp, HttpStatus.OK);

        // Set up to mock ords response
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.GET),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<FileNumberSearchResponse>>any()))
                .thenReturn(responseEntity);

        // Do request
        var out = fileController.fileNumberSearch(req);

        // Assert response is correct
        Assertions.assertEquals(resp, out);
    }

    @Test
    public void fileNumberSearchNullFilterTest() throws IOException {
        // Init request object
        var req = new FileNumberSearch();

        // Init response
        var resp = new FileNumberSearchResponse();
        CourtFile cf = new CourtFile();
        cf.setCourtClassCode("A");
        cf.setCourtLevelCode("A");
        cf.setCourtFileNumber("A");
        cf.setLocationId(BigDecimal.ONE);
        cf.setPhysicalFileId(BigDecimal.ONE);
        cf.setStyleOfCause("A");
        resp.getCourtFiles().add(cf);

        ResponseEntity<FileNumberSearchResponse> responseEntity =
                new ResponseEntity<>(resp, HttpStatus.OK);

        // Set up to mock ords response
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.GET),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<FileNumberSearchResponse>>any()))
                .thenReturn(responseEntity);

        // Do request
        var out = fileController.fileNumberSearch(req);

        // Assert response is correct
        Assertions.assertEquals(resp, out);
    }

    @Test
    public void linkFileTest() throws IOException {
        // Init request object
        var req = new LinkFile();
        req.setPhysicalFileId(BigDecimal.ONE);
        req.setCaseActionNumber("A");

        // Init response
        var resp = new LinkFileResponse();
        resp.setLinkId(BigDecimal.ONE);

        ResponseEntity<LinkFileResponse> responseEntity = new ResponseEntity<>(resp, HttpStatus.OK);

        // Set up to mock ords response
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.POST),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<LinkFileResponse>>any()))
                .thenReturn(responseEntity);

        // Do request
        var out = fileController.linkFile(req);

        // Assert response is correct
        Assertions.assertEquals(resp, out);
    }

    @Test
    public void unlinkFileTest() throws IOException {
        // Init request object
        var req = new UnlinkFile();
        req.setPhysicalFileId(BigDecimal.ONE);
        req.setCaseActionNumber("A");

        // Init response
        var resp = new UnlinkFileResponse();

        ResponseEntity<UnlinkFileResponse> responseEntity =
                new ResponseEntity<>(resp, HttpStatus.OK);

        // Set up to mock ords response
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.PUT),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<UnlinkFileResponse>>any()))
                .thenReturn(responseEntity);

        // Do request
        var out = fileController.unlinkFile(req);

        // Assert response is correct
        Assertions.assertNotNull(out);
    }

    @Test
    public void fileNumberSearchPublicAccessTest() throws IOException {
        // Init request object
        var req = new FileNumbeSearchPublicAccess();
        var fil = new FileNumberSearchFilter();
        fil.setCourtFileNumber("A");
        fil.setLocationId(BigDecimal.ONE);
        fil.setLocationId(BigDecimal.ONE);
        fil.setCourtClassCode("A");
        req.setFilter(fil);

        // Init response
        var resp = new FileNumbeSearchPublicAccessResponse();
        CourtFile cf = new CourtFile();
        cf.setCourtClassCode("A");
        cf.setCourtLevelCode("A");
        cf.setCourtFileNumber("A");
        cf.setLocationId(BigDecimal.ONE);
        cf.setPhysicalFileId(BigDecimal.ONE);
        cf.setStyleOfCause("A");
        resp.getCourtFiles().add(cf);

        ResponseEntity<FileNumbeSearchPublicAccessResponse> responseEntity =
                new ResponseEntity<>(resp, HttpStatus.OK);

        // Set up to mock ords response
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.GET),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<FileNumbeSearchPublicAccessResponse>>any()))
                .thenReturn(responseEntity);

        // Do request
        var out = fileController.fileNumberSearchPublicAccess(req);

        // Assert response is correct
        Assertions.assertEquals(resp, out);
    }

    @Test
    public void fileNumberSearchPublicAccessNullFilterTest() throws IOException {
        // Init request object
        var req = new FileNumbeSearchPublicAccess();

        // Init response
        var resp = new FileNumbeSearchPublicAccessResponse();
        var cf = new CourtFile();
        cf.setCourtClassCode("A");
        cf.setCourtLevelCode("A");
        cf.setCourtFileNumber("A");
        cf.setLocationId(BigDecimal.ONE);
        cf.setPhysicalFileId(BigDecimal.ONE);
        cf.setStyleOfCause("A");
        resp.getCourtFiles().add(cf);

        ResponseEntity<FileNumbeSearchPublicAccessResponse> responseEntity =
                new ResponseEntity<>(resp, HttpStatus.OK);

        // Set up to mock ords response
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.GET),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<FileNumbeSearchPublicAccessResponse>>any()))
                .thenReturn(responseEntity);

        // Do request
        var out = fileController.fileNumberSearchPublicAccess(req);

        // Assert response is correct
        Assertions.assertEquals(resp, out);
    }

    @Test
    public void fileNumberSearchPublicAccessSealedTest() throws IOException {
        // Init request object
        var req = new FileNumbeSearchPublicAccess();

        // Init response
        var resp = new FileNumbeSearchPublicAccessResponse();

        ResponseEntity<FileNumbeSearchPublicAccessResponse> responseEntity =
                new ResponseEntity<>(resp, HttpStatus.OK);

        // Set up to mock ords response
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.GET),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<FileNumbeSearchPublicAccessResponse>>any()))
                .thenReturn(responseEntity);

        // Do request
        var out = fileController.fileNumberSearchPublicAccess(req);

        // Assert response is correct
        Assertions.assertEquals(resp, out);
    }
}
