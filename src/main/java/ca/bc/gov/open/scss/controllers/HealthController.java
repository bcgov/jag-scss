package ca.bc.gov.open.scss.controllers;

import ca.bc.gov.open.scss.configuration.SoapConfig;
import ca.bc.gov.open.scss.exceptions.ORDSException;
import ca.bc.gov.open.scss.models.OrdsErrorLog;
import ca.bc.gov.open.scss.models.RequestSuccessLog;
import ca.bc.gov.open.scss.wsdl.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.SneakyThrows;
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
import org.springframework.ws.transport.context.TransportContext;
import org.springframework.ws.transport.context.TransportContextHolder;
import org.springframework.ws.transport.http.HttpServletConnection;

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
    public GetHealthResponse getHealth(@RequestPayload GetHealth empty) throws IOException {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(host + "health");
        addEndpointHeader("getHealth");
        try {
            HttpEntity<GetHealthResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.GET,
                            new HttpEntity<>(new HttpHeaders()),
                            GetHealthResponse.class);

            log.info(
                    objectMapper.writeValueAsString(
                            new RequestSuccessLog("Request Success", "getHealth")));
            return resp.getBody();
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error retrieving data from ords",
                                    "getPing",
                                    ex.getMessage(),
                                    empty)));
            throw new ORDSException();
        }
    }

    @SneakyThrows
    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "getPing")
    @ResponsePayload
    public GetPingResponse getPing(@RequestPayload GetPing empty) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(host + "ping");
        addEndpointHeader("getPing");

        try {
            HttpEntity<GetPingResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.GET,
                            new HttpEntity<>(new HttpHeaders()),
                            GetPingResponse.class);

            log.info(
                    objectMapper.writeValueAsString(
                            new RequestSuccessLog("Request Success", "getPing")));
            return resp.getBody();
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error retrieving data from ords",
                                    "getPing",
                                    ex.getMessage(),
                                    empty)));
            throw new ORDSException();
        }
    }

    private void addEndpointHeader(String endpoint) {
        try {
            TransportContext context = TransportContextHolder.getTransportContext();
            HttpServletConnection connection = (HttpServletConnection) context.getConnection();
            connection.addResponseHeader("Endpoint", endpoint);
        } catch (Exception ex) {
            log.warn("Failed to add endpoint response header");
        }
    }
}
