package ca.bc.gov.open.Scss.Controllers;

import ca.bc.gov.open.Scss.Configuration.SoapConfig;
import ca.bc.gov.open.scss.wsdl.*;
import java.util.HashMap;
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
            @RequestPayload GetAllNotifications search) {
        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(host + "GetAllNotifications");

        HttpEntity<GetAllNotificationsResponse> resp =
                restTemplate.exchange(
                        builder.toUriString(),
                        HttpMethod.GET,
                        new HttpEntity<>(new HttpHeaders()),
                        GetAllNotificationsResponse.class);

        return resp.getBody();
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "getNotifications")
    @ResponsePayload
    public GetNotificationsResponse getNotification(@RequestPayload GetNotifications search) {
        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(host + "GetNotifications")
                        .queryParam("physicalFileId", search.getPhysicalFileId());

        HttpEntity<GetNotificationsResponse> resp =
                restTemplate.exchange(
                        builder.toUriString(),
                        HttpMethod.GET,
                        new HttpEntity<>(new HttpHeaders()),
                        GetNotificationsResponse.class);

        return resp.getBody();
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "hasNotification")
    @ResponsePayload
    public HasNotificationResponse hasNotifications(@RequestPayload HasNotification search) {
        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(host + "HasNotification")
                        .queryParam("physicalFileId", search.getPhysicalFileId());

        HttpEntity<HasNotificationResponse> resp =
                restTemplate.exchange(
                        builder.toUriString(),
                        HttpMethod.GET,
                        new HttpEntity<>(new HttpHeaders()),
                        HasNotificationResponse.class);

        return resp.getBody();
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "removeNotification")
    @ResponsePayload
    public RemoveNotificationResponse removeNotification(
            @RequestPayload RemoveNotification search) {

        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(host + "RemoveNotification")
                        .queryParam("NotificationId", search.getNotificationId());

        HttpEntity<HashMap> resp =
                restTemplate.exchange(
                        builder.toUriString(),
                        HttpMethod.DELETE,
                        new HttpEntity<>(new HttpHeaders()),
                        HashMap.class);

        return new RemoveNotificationResponse();
    }
}
