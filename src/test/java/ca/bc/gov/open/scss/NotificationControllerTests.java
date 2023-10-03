package ca.bc.gov.open.scss;

import static org.mockito.Mockito.when;

import ca.bc.gov.open.scss.controllers.NotificationController;
import ca.bc.gov.open.scss.wsdl.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class NotificationControllerTests {

    @Mock private NotificationController notificationController;
    @Mock private ObjectMapper objectMapper;
    @Mock private RestTemplate restTemplate;
    private  ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        notificationController = Mockito.spy(new NotificationController(restTemplate, objectMapper));
    }

    @Test
    public void getAllNotificationsTest() throws IOException {
        // Init request object
        var req = new GetAllNotifications();

        // Init response
        var resp = new GetAllNotificationsResponse();
        Notification not = new Notification();
        not.setNotificationId(BigDecimal.ONE);
        not.setLinkId(BigDecimal.ONE);
        not.setPhysicalFileId(BigDecimal.ONE);
        not.setCategory("A");
        not.setEventType("A");
        not.setStatus("A");
        Instant now = Instant.now();
        not.setEventDatetime(now);
        not.setStatusDatetime(now);
        resp.getNotifications().add(not);

        ResponseEntity<GetAllNotificationsResponse> responseEntity =
                new ResponseEntity<>(resp, HttpStatus.OK);

        // Set up to mock ords response
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.GET),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<GetAllNotificationsResponse>>any()))
                .thenReturn(responseEntity);

        // Do request
        var out = notificationController.getAllNotifications(req);

        // Assert response is correct
        Assertions.assertEquals(resp, out);
    }

    @Test
    public void getNotificationTest() throws IOException {
        // Init request object
        var req = new GetNotifications();

        // Init response
        var resp = new GetNotificationsResponse();
        Notification not = new Notification();
        not.setNotificationId(BigDecimal.ONE);
        not.setLinkId(BigDecimal.ONE);
        not.setPhysicalFileId(BigDecimal.ONE);
        not.setCategory("A");
        not.setEventType("A");
        not.setStatus("A");
        Instant now = Instant.now();
        not.setEventDatetime(now);
        not.setStatusDatetime(now);
        resp.getNotifications().add(not);

        ResponseEntity<GetNotificationsResponse> responseEntity =
                new ResponseEntity<>(resp, HttpStatus.OK);

        // Set up to mock ords response
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.GET),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<GetNotificationsResponse>>any()))
                .thenReturn(responseEntity);

        // Do request
        var out = notificationController.getNotification(req);

        // Assert response is correct
        Assertions.assertEquals(resp, out);
    }

    @Test
    public void hasNotificationTest() throws IOException {
        // Init request object
        var req = new HasNotification();
        req.setPhysicalFileId(BigDecimal.ONE);

        // Init response
        var resp = new HasNotificationResponse();
        resp.setBoolean(true);

        ResponseEntity<HasNotificationResponse> responseEntity =
                new ResponseEntity<>(resp, HttpStatus.OK);

        // Set up to mock ords response
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.GET),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<HasNotificationResponse>>any()))
                .thenReturn(responseEntity);

        // Do request
        var out = notificationController.hasNotifications(req);

        // Assert response is correct
        Assertions.assertEquals(resp, out);
    }

    @Test
    public void removeNotificationTest() throws IOException {
        // Init request object
        var req = new RemoveNotification();
        req.setNotificationId(BigDecimal.ONE);

        // Init response
        var resp = new RemoveNotificationResponse();

        ResponseEntity<RemoveNotificationResponse> responseEntity =
                new ResponseEntity<>(resp, HttpStatus.OK);

        // Set up to mock ords response
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.DELETE),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<RemoveNotificationResponse>>any()))
                .thenReturn(responseEntity);

        // Do request
        var out = notificationController.removeNotification(req);

        // Assert response is correct
        Assertions.assertNotNull(out);
    }

    @Test
    public void JAXBMarshallInstant() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(GetAllNotificationsResponse.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        Instant now = Instant.now();
        var nowStr = now.toString();
        var nr = new GetAllNotificationsResponse();
        var not = new Notification();
        not.setNotificationId(BigDecimal.ONE);
        not.setLinkId(BigDecimal.ONE);
        not.setPhysicalFileId(BigDecimal.ONE);
        not.setCategory("A");
        not.setEventType("A");
        not.setStatus("A");
        not.setEventDatetime(now);
        not.setStatusDatetime(now);
        nr.getNotifications().add(not);
        String out;
        var baos = new ByteArrayOutputStream();
        jaxbMarshaller.marshal(nr, baos);
        out = baos.toString();

        Assertions.assertTrue(
                out.contains(nowStr.substring(0, nowStr.length() - 1)) && !out.contains(nowStr));
    }

    @Test
    public void SaveHearingParser() throws JAXBException {
        String in =
                "<scss:saveHearingResults xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:scss=\"http://brooks/SCSS.Source.CeisScss.ws.provider:CeisScss\">\n"
                        + "         <hearingResult>\n"
                        + "            <HearingResult>\n"
                        + "               <CaseDetails>\n"
                        + "                  <CaseTrackingID>1</CaseTrackingID>\n"
                        + "                  <CaseFiling>1</CaseFiling>\n"
                        + "                  <CaseAugmentation>\n"
                        + "                     <CaseHearing>\n"
                        + "                        <CourtEventAppearance>\n"
                        + "                           <CourtAppearanceCourt>1</CourtAppearanceCourt>\n"
                        + "                           <CourtAppearanceDate>2003-07-11</CourtAppearanceDate>\n"
                        + "                           <CourtAppearanceCategoryText>A</CourtAppearanceCategoryText>\n"
                        + "                           <CourtEventSequenceID></CourtEventSequenceID>\n"
                        + "                           <ActivityStatus>NOT PROCEDING</ActivityStatus>\n"
                        + "                           <CancellationStatus>Abandoned</CancellationStatus>\n"
                        + "                           <TimeMeasureDetails>\n"
                        + "                              <MeasureText>1</MeasureText>\n"
                        + "                              <MeasureUnitText>A</MeasureUnitText>\n"
                        + "                              <MeasureEstimatedIndicator>true</MeasureEstimatedIndicator>\n"
                        + "                           </TimeMeasureDetails>\n"
                        + "                        </CourtEventAppearance>\n"
                        + "                     </CaseHearing>\n"
                        + "                  </CaseAugmentation>\n"
                        + "               </CaseDetails>\n"
                        + "            </HearingResult>\n"
                        + "         </hearingResult>\n"
                        + "      </scss:saveHearingResults>";

        JAXBContext jaxbContext = JAXBContext.newInstance(SaveHearingResults.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        SaveHearingResults out =
                (SaveHearingResults)
                        jaxbUnmarshaller.unmarshal(new ByteArrayInputStream(in.getBytes()));

        Assertions.assertNotNull(out);

        in =
                "<scss:saveHearingResults xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:scss=\"http://brooks/SCSS.Source.CeisScss.ws.provider:CeisScss\">\n"
                        + "         <hearingResult>\n"
                        + "            <HearingResult>\n"
                        + "               <CaseDetails>\n"
                        + "                  <CaseTrackingID>1</CaseTrackingID>\n"
                        + "                  <CaseFiling>1</CaseFiling>\n"
                        + "                  <CaseAugmentation>\n"
                        + "                     <CaseHearing>\n"
                        + "                        <CourtEventAppearance>\n"
                        + "                           <CourtAppearanceCourt>1</CourtAppearanceCourt>\n"
                        + "                           <CourtAppearanceDate>2003-07-11 05.05.05.002000 AM</CourtAppearanceDate>\n"
                        + "                           <CourtAppearanceCategoryText>A</CourtAppearanceCategoryText>\n"
                        + "                           <CourtEventSequenceID></CourtEventSequenceID>\n"
                        + "                           <ActivityStatus>NOT PROCEDING</ActivityStatus>\n"
                        + "                           <CancellationStatus>Abandoned</CancellationStatus>\n"
                        + "                           <TimeMeasureDetails>\n"
                        + "                              <MeasureText>1</MeasureText>\n"
                        + "                              <MeasureUnitText>A</MeasureUnitText>\n"
                        + "                              <MeasureEstimatedIndicator>true</MeasureEstimatedIndicator>\n"
                        + "                           </TimeMeasureDetails>\n"
                        + "                        </CourtEventAppearance>\n"
                        + "                     </CaseHearing>\n"
                        + "                  </CaseAugmentation>\n"
                        + "               </CaseDetails>\n"
                        + "            </HearingResult>\n"
                        + "         </hearingResult>\n"
                        + "      </scss:saveHearingResults>";

        out =
                (SaveHearingResults)
                        jaxbUnmarshaller.unmarshal(new ByteArrayInputStream(in.getBytes()));

        Assertions.assertNotNull(
                out.getHearingResult()
                        .getHearingResult()
                        .getCaseDetails()
                        .getCaseAugmentation()
                        .getCaseHearing()
                        .getCourtEventAppearance()
                        .getCourtAppearanceDate());

        in =
                "<scss:saveHearingResults xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:scss=\"http://brooks/SCSS.Source.CeisScss.ws.provider:CeisScss\">\n"
                        + "         <hearingResult>\n"
                        + "            <HearingResult>\n"
                        + "               <CaseDetails>\n"
                        + "                  <CaseTrackingID>1</CaseTrackingID>\n"
                        + "                  <CaseFiling>1</CaseFiling>\n"
                        + "                  <CaseAugmentation>\n"
                        + "                     <CaseHearing>\n"
                        + "                        <CourtEventAppearance>\n"
                        + "                           <CourtAppearanceCourt>1</CourtAppearanceCourt>\n"
                        + "                           <CourtAppearanceDate>I am bad</CourtAppearanceDate>\n"
                        + "                           <CourtAppearanceCategoryText>A</CourtAppearanceCategoryText>\n"
                        + "                           <CourtEventSequenceID></CourtEventSequenceID>\n"
                        + "                           <ActivityStatus>NOT PROCEDING</ActivityStatus>\n"
                        + "                           <CancellationStatus>Abandoned</CancellationStatus>\n"
                        + "                           <TimeMeasureDetails>\n"
                        + "                              <MeasureText>1</MeasureText>\n"
                        + "                              <MeasureUnitText>A</MeasureUnitText>\n"
                        + "                              <MeasureEstimatedIndicator>true</MeasureEstimatedIndicator>\n"
                        + "                           </TimeMeasureDetails>\n"
                        + "                        </CourtEventAppearance>\n"
                        + "                     </CaseHearing>\n"
                        + "                  </CaseAugmentation>\n"
                        + "               </CaseDetails>\n"
                        + "            </HearingResult>\n"
                        + "         </hearingResult>\n"
                        + "      </scss:saveHearingResults>";

        out =
                (SaveHearingResults)
                        jaxbUnmarshaller.unmarshal(new ByteArrayInputStream(in.getBytes()));

        Assertions.assertNull(
                out.getHearingResult()
                        .getHearingResult()
                        .getCaseDetails()
                        .getCaseAugmentation()
                        .getCaseHearing()
                        .getCourtEventAppearance()
                        .getCourtAppearanceDate());
    }
}
