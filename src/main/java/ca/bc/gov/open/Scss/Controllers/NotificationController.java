package ca.bc.gov.open.Scss.Controllers;

import ca.bc.gov.open.Scss.Configuration.SoapConfig;
import ca.bc.gov.open.Scss.Exceptions.ORDSException;
import ca.bc.gov.open.scss.wsdl.*;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
@Slf4j
public class NotificationController {

    @Value("${scss.host}")
    private String host = "http://127.0.0.1/";

    private final RestTemplate restTemplate;

    @Autowired
    public NotificationController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "getAllNotifications")
    @ResponsePayload
    public GetAllNotificationsResponse getAllNotifications(
            @RequestPayload GetAllNotifications search) throws ORDSException {
        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(host + "GetAllNotifications");

        try {
            HttpEntity<GetAllNotificationsResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.GET,
                            new HttpEntity<>(new HttpHeaders()),
                            GetAllNotificationsResponse.class);

            return resp.getBody();
        } catch (Exception ex) {
            log.error("Error retrieving data from ords in method GetAllNotifications");
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "getNotifications")
    @ResponsePayload
    public GetNotificationsResponse getNotification(@RequestPayload GetNotifications search)
            throws ORDSException {
        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(host + "GetNotifications")
                        .queryParam("physicalFileId", search.getPhysicalFileId());

        try {
            HttpEntity<GetNotificationsResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.GET,
                            new HttpEntity<>(new HttpHeaders()),
                            GetNotificationsResponse.class);

            return resp.getBody();
        } catch (Exception ex) {
            log.error("Error retrieving data from ords in method GetNotifications");
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "hasNotification")
    @ResponsePayload
    public HasNotificationResponse hasNotifications(@RequestPayload HasNotification search)
            throws ORDSException {
        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(host + "HasNotification")
                        .queryParam("physicalFileId", search.getPhysicalFileId());

        try {
            HttpEntity<HasNotificationResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.GET,
                            new HttpEntity<>(new HttpHeaders()),
                            HasNotificationResponse.class);

            return resp.getBody();
        } catch (Exception ex) {
            log.error("Error retrieving data from ords in method HasNotification");
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "removeNotification")
    @ResponsePayload
    public RemoveNotificationResponse removeNotification(
            @RequestPayload RemoveNotification search) {

        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(host + "RemoveNotification")
                        .queryParam("NotificationId", search.getNotificationId());

        try {
            HttpEntity<HashMap> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.DELETE,
                            new HttpEntity<>(new HttpHeaders()),
                            HashMap.class);
        } catch (Exception ex) {
            log.error("Error retrieving data from ords in method RemoveNotification");
            throw new ORDSException();
        }
        return new RemoveNotificationResponse();
    }
}
