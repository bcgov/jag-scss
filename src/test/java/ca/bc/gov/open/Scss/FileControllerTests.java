package ca.bc.gov.open.Scss;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ca.bc.gov.open.Scss.Controllers.FileController;
import ca.bc.gov.open.scss.wsdl.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class FileControllerTests {

    @Autowired private FileController fileController;

    @Autowired private MockMvc mockMvc;

    @Mock private RestTemplate restTemplate = new RestTemplate();

    @Test
    public void fileNumberSearchTest() throws IOException {
        //  Init service under test
        fileController = new FileController(restTemplate, null);

        //    Init request object
        var fs = new FileNumberSearch();
        fs.setFilter(new FileNumberSearchFilter());
        fs.getFilter().setCourtClassCode("C");
        fs.getFilter().setCourtFileNumber("V");
        fs.getFilter().setCourtLevelCode("S");
        fs.getFilter().setLocationId(new BigDecimal(1));

        //    Init response
        CourtFile cf = new CourtFile();
        cf.setCourtClassCode("A");
        cf.setCourtLevelCode("A");
        cf.setCourtFileNumber("A");
        cf.setLocationId(BigDecimal.ONE);
        cf.setPhysicalFileId(BigDecimal.ONE);
        cf.setStyleOfCause("A");

        var fns = new FileNumberSearchResponse();
        fns.setCourtFiles(Collections.singletonList(cf));
        ResponseEntity<FileNumberSearchResponse> responseEntity =
                new ResponseEntity<>(fns, HttpStatus.OK);

        //     Set up to mock ords response
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.GET),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<FileNumberSearchResponse>>any()))
                .thenReturn(responseEntity);

        //     Do request
        var out = fileController.fileNumberSearch(fs);

        //     Assert response is correct
        assert (out.equals(fns));
    }

    @Test
    public void fileNumberSearchNullFilterTest() throws IOException {
        //  Init service under test
        fileController = new FileController(restTemplate, null);

        //    Init request object
        var fs = new FileNumberSearch();

        //    Init response
        CourtFile cf = new CourtFile();
        cf.setCourtClassCode("A");
        cf.setCourtLevelCode("A");
        cf.setCourtFileNumber("A");
        cf.setLocationId(BigDecimal.ONE);
        cf.setPhysicalFileId(BigDecimal.ONE);
        cf.setStyleOfCause("A");

        var fns = new FileNumberSearchResponse();
        fns.setCourtFiles(Collections.singletonList(cf));
        ResponseEntity<FileNumberSearchResponse> responseEntity =
                new ResponseEntity<>(fns, HttpStatus.OK);

        //     Set up to mock ords response
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.GET),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<FileNumberSearchResponse>>any()))
                .thenReturn(responseEntity);

        //     Do request
        var out = fileController.fileNumberSearch(fs);

        //     Assert response is correct
        assert (out.equals(fns));
    }

    @Test
    public void linkFileTest() throws IOException {
        //  Init service under test
        fileController = new FileController(restTemplate, null);

        //    Init request object
        var lf = new LinkFile();
        lf.setPhysicalFileId(BigDecimal.ONE);
        lf.setCaseActionNumber("A");
        //    Init response
        var lfr = new LinkFileResponse();
        lfr.setLinkId(BigDecimal.ONE);

        ResponseEntity<LinkFileResponse> responseEntity = new ResponseEntity<>(lfr, HttpStatus.OK);

        //     Set up to mock ords response
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.POST),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<LinkFileResponse>>any()))
                .thenReturn(responseEntity);

        //     Do request
        var out = fileController.linkFile(lf);

        //     Assert response is correct
        assert (out.equals(lfr));
    }

    @Test
    public void unlinkFileTest() throws IOException {
        //  Init service under test
        fileController = new FileController(restTemplate, null);

        //    Init request object
        var lf = new UnlinkFile();
        lf.setPhysicalFileId(BigDecimal.ONE);
        lf.setCaseActionNumber("A");
        //    Init response
        var lfr = new UnlinkFileResponse();

        ResponseEntity<UnlinkFileResponse> responseEntity =
                new ResponseEntity<>(lfr, HttpStatus.OK);

        //     Set up to mock ords response
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.PUT),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<UnlinkFileResponse>>any()))
                .thenReturn(responseEntity);

        //     Do request
        var out = fileController.unlinkFile(lf);

        //     Assert response is correct
        assert out != null;
    }

    @Test
    public void fileNumberSearchPublicAccessTest() throws IOException {
        //  Init service under test
        fileController = new FileController(restTemplate, null);

        //    Init request object
        var fs = new FileNumbeSearchPublicAccess();
        var fil = new FileNumberSearchFilter();
        fil.setCourtFileNumber("A");
        fil.setLocationId(BigDecimal.ONE);
        fil.setLocationId(BigDecimal.ONE);
        fil.setCourtClassCode("A");
        fs.setFilter(fil);

        //    Init response
        var cf = new CourtFile();
        cf.setCourtClassCode("A");
        cf.setCourtLevelCode("A");
        cf.setCourtFileNumber("A");
        cf.setLocationId(BigDecimal.ONE);
        cf.setPhysicalFileId(BigDecimal.ONE);
        cf.setStyleOfCause("A");

        var fns = new FileNumbeSearchPublicAccessResponse();
        fns.setCourtFiles(Collections.singletonList(cf));

        ResponseEntity<FileNumbeSearchPublicAccessResponse> responseEntity =
                new ResponseEntity<>(fns, HttpStatus.OK);

        //     Set up to mock ords response
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.GET),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<FileNumbeSearchPublicAccessResponse>>any()))
                .thenReturn(responseEntity);

        //     Do request
        var out = fileController.fileNumberSearchPublicAccess(fs);

        //     Assert response is correct
        assert (out.equals(fns));
    }

    @Test
    public void fileNumberSearchPublicAccessNullFilterTest() throws IOException {
        //  Init service under test
        fileController = new FileController(restTemplate, null);

        //    Init request object
        var fs = new FileNumbeSearchPublicAccess();

        //    Init response
        var cf = new CourtFile();
        cf.setCourtClassCode("A");
        cf.setCourtLevelCode("A");
        cf.setCourtFileNumber("A");
        cf.setLocationId(BigDecimal.ONE);
        cf.setPhysicalFileId(BigDecimal.ONE);
        cf.setStyleOfCause("A");

        var fns = new FileNumbeSearchPublicAccessResponse();
        fns.setCourtFiles(Collections.singletonList(cf));

        ResponseEntity<FileNumbeSearchPublicAccessResponse> responseEntity =
                new ResponseEntity<>(fns, HttpStatus.OK);

        //     Set up to mock ords response
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.GET),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<FileNumbeSearchPublicAccessResponse>>any()))
                .thenReturn(responseEntity);

        //     Do request
        var out = fileController.fileNumberSearchPublicAccess(fs);

        //     Assert response is correct
        assert (out.equals(fns));
    }

    @Test
    public void fileNumberSearchPublicAccessSealedTest() throws IOException {
        //  Init service under test
        fileController = new FileController(restTemplate, null);

        //    Init request object
        var fs = new FileNumbeSearchPublicAccess();

        var fns = new FileNumbeSearchPublicAccessResponse();

        ResponseEntity<FileNumbeSearchPublicAccessResponse> responseEntity =
                new ResponseEntity<>(fns, HttpStatus.OK);

        //     Set up to mock ords response
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.GET),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<FileNumbeSearchPublicAccessResponse>>any()))
                .thenReturn(responseEntity);

        //     Do request
        var out = fileController.fileNumberSearchPublicAccess(fs);

        //     Assert response is correct
        assert (out.equals(fns));
    }

    @Test
    public void securityTestFail_Then403() throws Exception {
        var response =
                mockMvc.perform(post("/ws").contentType(MediaType.TEXT_XML))
                        .andExpect(status().is4xxClientError())
                        .andReturn();
        assert response.getResponse().getStatus() == 401;
    }
}
