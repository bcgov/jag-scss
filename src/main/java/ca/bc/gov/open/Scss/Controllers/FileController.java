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
            throws JsonProcessingException {

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
        try {
            HttpEntity<FileNumberSearchResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.GET,
                            new HttpEntity<>(new HttpHeaders()),
                            FileNumberSearchResponse.class);

            return resp.getBody();
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS", "FileNumberSearch", search)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "linkFile")
    @ResponsePayload
    public LinkFileResponse linkFile(@RequestPayload LinkFile search)
            throws JsonProcessingException {
        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(host + "LinkFiles")
                        .queryParam("caseActionNumber", search.getCaseActionNumber())
                        .queryParam("physicalFileId", search.getPhysicalFileId());
        try {
            HttpEntity<LinkFileResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.POST,
                            new HttpEntity<>(new HttpHeaders()),
                            LinkFileResponse.class);
            return resp.getBody();
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog("Error received from ORDS", "LinkFiles", search)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "unlinkFile")
    @ResponsePayload
    public UnlinkFileResponse unlinkFile(@RequestPayload UnlinkFile search)
            throws JsonProcessingException {
        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(host + "UnlinkFiles")
                        .queryParam("caseActionNumber", search.getCaseActionNumber())
                        .queryParam("physicalFileId", search.getPhysicalFileId());

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
                            new OrdsErrorLog("Error received from ORDS", "UnlinkFiles", search)));
            throw new ORDSException();
        }
        return new UnlinkFileResponse();
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "fileNumbeSearchPublicAccess")
    @ResponsePayload
    public FileNumbeSearchPublicAccessResponse fileNumberSearchPublicAccess(
            @RequestPayload FileNumbeSearchPublicAccess search) throws JsonProcessingException {

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

            return resp.getBody();
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS", "FileNumberSearchPublic", search)));
            throw new ORDSException();
        }
    }
}
