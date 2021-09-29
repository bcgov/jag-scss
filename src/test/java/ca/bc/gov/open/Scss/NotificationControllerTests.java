package ca.bc.gov.open.Scss;

import static org.mockito.Mockito.when;

import ca.bc.gov.open.Scss.Controllers.NotificationController;
import ca.bc.gov.open.scss.wsdl.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
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
public class NotificationControllerTests {

    private NotificationController notificationController;

    @Autowired private ObjectMapper objectMapper;

    @Mock private RestTemplate restTemplate = new RestTemplate();

    @Test
    public void getAllNotificationsTest() {
        //  Init service under test
        notificationController = new NotificationController(restTemplate);
        //    Init request object
        var fs = new GetAllNotifications();

        //    Init response
        var nr = new GetAllNotificationsResponse();

        var not = new Notification();
        not.setNotificationId(BigDecimal.ONE);
        not.setLinkId(BigDecimal.ONE);
        not.setPhysicalFileId(BigDecimal.ONE);
        not.setCategory("A");
        not.setEventType("A");
        not.setStatus("A");
        Instant now = Instant.now();
        not.setEventDatetime(now);
        not.setStatusDatetime(now);

        nr.setNotifications(Collections.singletonList(not));
        ResponseEntity<GetAllNotificationsResponse> responseEntity =
                new ResponseEntity<>(nr, HttpStatus.OK);

        //     Set up to mock ords response
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.GET),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<GetAllNotificationsResponse>>any()))
                .thenReturn(responseEntity);

        //     Do request
        var out = notificationController.getAllNotifications(fs);

        //     Assert response is correct
        assert (out.equals(nr));
    }

    @Test
    public void getNotificationTest() {
        //  Init service under test
        notificationController = new NotificationController(restTemplate);
        //    Init request object
        var gn = new GetNotifications();

        //    Init response
        var nr = new GetNotificationsResponse();

        var not = new Notification();
        not.setNotificationId(BigDecimal.ONE);
        not.setLinkId(BigDecimal.ONE);
        not.setPhysicalFileId(BigDecimal.ONE);
        not.setCategory("A");
        not.setEventType("A");
        not.setStatus("A");
        Instant now = Instant.now();
        not.setEventDatetime(now);
        not.setStatusDatetime(now);

        nr.setNotifications(Collections.singletonList(not));
        ResponseEntity<GetNotificationsResponse> responseEntity =
                new ResponseEntity<>(nr, HttpStatus.OK);

        //     Set up to mock ords response
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.GET),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<GetNotificationsResponse>>any()))
                .thenReturn(responseEntity);

        //     Do request
        var out = notificationController.getNotification(gn);

        //     Assert response is correct
        assert (out.equals(nr));
    }

    @Test
    public void hasNotificationTest() {
        //  Init service under test
        notificationController = new NotificationController(restTemplate);
        //    Init request object
        var hn = new HasNotification();
        hn.setPhysicalFileId(BigDecimal.ONE);

        //    Init response
        var nr = new HasNotificationResponse();

        nr.setBoolean(true);

        ResponseEntity<HasNotificationResponse> responseEntity =
                new ResponseEntity<>(nr, HttpStatus.OK);

        //     Set up to mock ords response
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.GET),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<HasNotificationResponse>>any()))
                .thenReturn(responseEntity);

        //     Do request
        var out = notificationController.hasNotifications(hn);

        //     Assert response is correct
        assert (out.equals(nr));
    }

    @Test
    public void removeNotificationTest() {
        //  Init service under test
        notificationController = new NotificationController(restTemplate);
        //    Init request object
        var rn = new RemoveNotification();
        rn.setNotificationId(BigDecimal.ONE);

        //    Init response
        var nr = new RemoveNotificationResponse();

        ResponseEntity<RemoveNotificationResponse> responseEntity =
                new ResponseEntity<>(nr, HttpStatus.OK);

        //     Set up to mock ords response
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.DELETE),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<RemoveNotificationResponse>>any()))
                .thenReturn(responseEntity);

        //     Do request
        var out = notificationController.removeNotification(rn);

        //     Assert response is correct
        assert out != null;
    }

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

        assert inst.getStatusDatetime() == null;
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
        nr.setNotifications(Collections.singletonList(not));
        String out = "";
        var baos = new ByteArrayOutputStream();
        jaxbMarshaller.marshal(nr, baos);
        out = baos.toString();

        assert out.contains(nowStr.substring(0, nowStr.length() - 1)) && !out.contains(nowStr);
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
                        + "                           <CourtAppearanceDate>03-SEP-11</CourtAppearanceDate>\n"
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

        assert out != null;

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
                        + "                           <CourtAppearanceDate>03-SEP-11 05.05.05.002000 AM</CourtAppearanceDate>\n"
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

        assert out.getHearingResult()
                        .getHearingResult()
                        .getCaseDetails()
                        .getCaseAugmentation()
                        .getCaseHearing()
                        .getCourtEventAppearance()
                        .getCourtAppearanceDate()
                != null;

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

        assert out.getHearingResult()
                        .getHearingResult()
                        .getCaseDetails()
                        .getCaseAugmentation()
                        .getCaseHearing()
                        .getCourtEventAppearance()
                        .getCourtAppearanceDate()
                == null;
    }
}
