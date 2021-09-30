package ca.bc.gov.open.Scss.Controllers;

import ca.bc.gov.open.Scss.Configuration.SoapConfig;
import ca.bc.gov.open.Scss.Exceptions.ORDSException;
import ca.bc.gov.open.Scss.Models.OrdsErrorLog;
import ca.bc.gov.open.scss.wsdl.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class HealthController {

    @Value("${scss.host}")
    private String host = "https://127.0.0.1/";

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    @Autowired
    public HealthController(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "getHealth")
    @ResponsePayload
    public GetHealthResponse getHealth(@RequestPayload GetHealth empty)
            throws JsonProcessingException {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(host + "health");

        try {
            HttpEntity<GetHealthResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.GET,
                            new HttpEntity<>(new HttpHeaders()),
                            GetHealthResponse.class);

            return resp.getBody();
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog("Error retrieving data from ords", "getPing", empty)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "getPing")
    @ResponsePayload
    public GetPingResponse getPing(@RequestPayload GetPing empty) throws JsonProcessingException {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(host + "ping");
        log.error(
                objectMapper.writeValueAsString(
                        new OrdsErrorLog("Request Received", "getPing", empty)));
        try {
            HttpEntity<GetPingResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.GET,
                            new HttpEntity<>(new HttpHeaders()),
                            GetPingResponse.class);

            return resp.getBody();
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog("Error retrieving data from ords", "getPing", empty)));
            throw new ORDSException();
        }
    }
}
