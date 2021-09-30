package ca.bc.gov.open.Scss.Controllers;

import ca.bc.gov.open.Scss.Configuration.SoapConfig;
import ca.bc.gov.open.Scss.Exceptions.ORDSException;
import ca.bc.gov.open.Scss.Models.OrdsErrorLog;
import ca.bc.gov.open.scss.wsdl.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final ObjectMapper objectMapper;

    @Autowired
    public NotificationController(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "getAllNotifications")
    @ResponsePayload
    public GetAllNotificationsResponse getAllNotifications(
            @RequestPayload GetAllNotifications search)
            throws ORDSException, JsonProcessingException {
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
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error retrieving data from ords",
                                    "GetAllNotifications",
                                    search)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "getNotifications")
    @ResponsePayload
    public GetNotificationsResponse getNotification(@RequestPayload GetNotifications search)
            throws ORDSException, JsonProcessingException {
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
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error retrieving data from ords",
                                    "GetNotifications",
                                    search)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "hasNotification")
    @ResponsePayload
    public HasNotificationResponse hasNotifications(@RequestPayload HasNotification search)
            throws ORDSException, JsonProcessingException {
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
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error retrieving data from ords", "HasNotification", search)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "removeNotification")
    @ResponsePayload
    public RemoveNotificationResponse removeNotification(@RequestPayload RemoveNotification search)
            throws JsonProcessingException {

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
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error retrieving data from ords",
                                    "RemoveNotification",
                                    search)));
            throw new ORDSException();
        }
        return new RemoveNotificationResponse();
    }
}
