package ca.bc.gov.open.scss.controllers;

import ca.bc.gov.open.scss.configuration.SoapConfig;
import ca.bc.gov.open.scss.exceptions.ORDSException;
import ca.bc.gov.open.scss.models.OrdsErrorLog;
import ca.bc.gov.open.scss.models.RequestSuccessLog;
import ca.bc.gov.open.scss.wsdl.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.math.BigDecimal;
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
import org.springframework.ws.transport.context.TransportContext;
import org.springframework.ws.transport.context.TransportContextHolder;
import org.springframework.ws.transport.http.HttpServletConnection;

@Endpoint
@Slf4j
public class FileController {

    @Value("${scss.host}")
    private String host = "https://127.0.0.1/";

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    @Autowired
    public FileController(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "fileNumberSearch")
    @ResponsePayload
    public FileNumberSearchResponse fileNumberSearch(@RequestPayload FileNumberSearch search)
            throws IOException {

        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(host + "FileNumberSearch")
                        .queryParam(
                                "courtFileNumber",
                                search.getFilter() != null
                                        ? search.getFilter().getCourtFileNumber()
                                        : null)
                        .queryParam(
                                "locationId",
                                search.getFilter() != null
                                                && search.getFilter().getLocationId() != null
                                                && !search.getFilter()
                                                        .getLocationId()
                                                        .equals(BigDecimal.valueOf(-1))
                                        ? search.getFilter().getLocationId()
                                        : null)
                        .queryParam(
                                "courtLevelCode",
                                search.getFilter() != null
                                        ? search.getFilter().getCourtLevelCode()
                                        : null)
                        .queryParam(
                                "courtClassCode",
                                search.getFilter() != null
                                                && search.getFilter().getCourtClassCode().length()
                                                        > 0
                                        ? search.getFilter().getCourtClassCode()
                                        : null);
        addEndpointHeader("FileNumberSearch");
        try {
            HttpEntity<FileNumberSearchResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.GET,
                            new HttpEntity<>(new HttpHeaders()),
                            FileNumberSearchResponse.class);
            if (resp.getBody().getCourtFiles().isEmpty()) {
                resp.getBody().setCourtFiles(null);
            }
            log.info(
                    objectMapper.writeValueAsString(
                            new RequestSuccessLog("Request Success", "FileNumberSearch")));
            return resp.getBody();
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "FileNumberSearch",
                                    ex.getMessage(),
                                    search)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "linkFile")
    @ResponsePayload
    public LinkFileResponse linkFile(@RequestPayload LinkFile search) throws IOException {
        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(host + "LinkFiles")
                        .queryParam("caseActionNumber", search.getCaseActionNumber())
                        .queryParam("physicalFileId", search.getPhysicalFileId());
        addEndpointHeader("linkFile");
        try {
            HttpEntity<LinkFileResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.POST,
                            new HttpEntity<>(new HttpHeaders()),
                            LinkFileResponse.class);
            log.info(
                    objectMapper.writeValueAsString(
                            new RequestSuccessLog("Request Success", "linkFile")));
            return resp.getBody();
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "LinkFiles",
                                    ex.getMessage(),
                                    search)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "unlinkFile")
    @ResponsePayload
    public UnlinkFileResponse unlinkFile(@RequestPayload UnlinkFile search) throws IOException {
        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(host + "UnlinkFiles")
                        .queryParam("caseActionNumber", search.getCaseActionNumber())
                        .queryParam("physicalFileId", search.getPhysicalFileId());
        addEndpointHeader("UnlinkFiles");
        try {
            HttpEntity<HashMap> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.PUT,
                            new HttpEntity<>(new HttpHeaders()),
                            HashMap.class);
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "UnlinkFiles",
                                    ex.getMessage(),
                                    search)));
            throw new ORDSException();
        }
        log.info(
                objectMapper.writeValueAsString(
                        new RequestSuccessLog("Request Success", "unlinkFile")));
        return new UnlinkFileResponse();
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "fileNumbeSearchPublicAccess")
    @ResponsePayload
    public FileNumbeSearchPublicAccessResponse fileNumberSearchPublicAccess(
            @RequestPayload FileNumbeSearchPublicAccess search) throws IOException {

        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(host + "FileNumberSearchPublic")
                        .queryParam(
                                "courtFileNumber",
                                search.getFilter() != null
                                        ? search.getFilter().getCourtFileNumber()
                                        : null)
                        .queryParam(
                                "locationId",
                                search.getFilter() != null
                                                && search.getFilter().getLocationId() != null
                                                && !search.getFilter()
                                                        .getLocationId()
                                                        .equals(BigDecimal.valueOf(-1))
                                        ? search.getFilter().getLocationId()
                                        : null)
                        .queryParam(
                                "courtLevelCode",
                                search.getFilter() != null
                                        ? search.getFilter().getCourtLevelCode()
                                        : null)
                        .queryParam(
                                "courtClassCode",
                                search.getFilter() != null
                                        ? search.getFilter().getCourtClassCode()
                                        : null);
        addEndpointHeader("fileNumbeSearchPublicAccess");
        try {
            HttpEntity<FileNumbeSearchPublicAccessResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.GET,
                            new HttpEntity<>(new HttpHeaders()),
                            FileNumbeSearchPublicAccessResponse.class);

            if (resp.getBody() != null
                    && (resp.getBody().getCourtFiles() == null
                            || resp.getBody().getCourtFiles().size() == 0)) {
                resp.getBody().setCourtFiles(null);
            }
            log.info(
                    objectMapper.writeValueAsString(
                            new RequestSuccessLog(
                                    "Request Success", "fileNumbeSearchPublicAccess")));
            return resp.getBody();
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "FileNumberSearchPublic",
                                    ex.getMessage(),
                                    search)));
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
