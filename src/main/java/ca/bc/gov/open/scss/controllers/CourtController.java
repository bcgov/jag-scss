package ca.bc.gov.open.scss.controllers;

import ca.bc.gov.open.scss.configuration.SoapConfig;
import ca.bc.gov.open.scss.exceptions.ORDSException;
import ca.bc.gov.open.scss.models.OrdsErrorLog;
import ca.bc.gov.open.scss.models.RequestSuccessLog;
import ca.bc.gov.open.scss.wsdl.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.math.BigDecimal;
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
public class CourtController {

    @Value("${scss.host}")
    private String host = "http://127.0.0.1/";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public CourtController(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "getCourtFile")
    @ResponsePayload
    public GetCourtFileResponse getCourtFile(@RequestPayload GetCourtFile search)
            throws IOException {
        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(host + "GetCourtFile")
                        .queryParam("physicalFileId", search.getPhysicalFileId());
        addEndpointHeader("GetCourtFile");
        try {
            HttpEntity<GetCourtFileResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.GET,
                            new HttpEntity<>(new HttpHeaders()),
                            GetCourtFileResponse.class);

            log.info(
                    objectMapper.writeValueAsString(
                            new RequestSuccessLog("Request Success", "getCourtFile")));
            if (resp.getBody().getCourtFile() == null) {
                return null;
            }
            return resp.getBody();
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "GetCourtFile",
                                    ex.getMessage(),
                                    search)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "getCourtBasics")
    @ResponsePayload
    public GetCourtBasicsResponse getCourtBasics(@RequestPayload GetCourtBasics search)
            throws IOException {
        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(host + "GetCourtBasics")
                        .queryParam("physicalFileId", search.getPhysicalFileId());
        addEndpointHeader("GetCourtBasics");
        try {
            HttpEntity<CaseBasics> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.GET,
                            new HttpEntity<>(new HttpHeaders()),
                            CaseBasics.class);
            GetCourtBasicsResponse cbr = new GetCourtBasicsResponse();
            cbr.setCaseBasics(resp.getBody());
            log.info(
                    objectMapper.writeValueAsString(
                            new RequestSuccessLog("Request Success", "GetCourtBasics")));
            return cbr;
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "GetCourtBasics",
                                    ex.getMessage(),
                                    search)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "getCeisConnectInfo")
    @ResponsePayload
    public GetCeisConnectInfoResponse getCeisConnectInfo(@RequestPayload GetCeisConnectInfo search)
            throws IOException {
        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(host + "GetCeisConnectInfo");
        addEndpointHeader("getCeisConnectInfo");
        try {
            HttpEntity<GetCeisConnectInfoResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.GET,
                            new HttpEntity<>(new HttpHeaders()),
                            GetCeisConnectInfoResponse.class);
            log.info(
                    objectMapper.writeValueAsString(
                            new RequestSuccessLog("Request Success", "getCeisConnectInfo")));
            return resp.getBody();
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "GetCeisConnectInfo",
                                    ex.getMessage(),
                                    search)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "getParties")
    @ResponsePayload
    public GetPartiesResponse getParties(@RequestPayload GetParties search) throws IOException {
        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(host + "GetParties")
                        .queryParam("physicalFileId", search.getPhysicalFileId());
        addEndpointHeader("GetParties");
        try {
            HttpEntity<GetPartiesResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.GET,
                            new HttpEntity<>(new HttpHeaders()),
                            GetPartiesResponse.class);
            log.info(
                    objectMapper.writeValueAsString(
                            new RequestSuccessLog("Request Success", "getParties")));
            return resp.getBody();
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "GetParties",
                                    ex.getMessage(),
                                    search)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "partyNameSearch")
    @ResponsePayload
    public PartyNameSearchResponse partyNameSearch(@RequestPayload PartyNameSearch search)
            throws IOException {
        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(host + "PartyNameSearch")
                        .queryParam(
                                "courtClass",
                                search.getFilter() != null
                                        ? search.getFilter().getCourtClass()
                                        : null)
                        .queryParam(
                                "agencyId",
                                search.getFilter() != null
                                                && search.getFilter().getAgencyId() != null
                                                && !search.getFilter()
                                                        .getAgencyId()
                                                        .equals(BigDecimal.valueOf(-1))
                                        ? search.getFilter().getAgencyId()
                                        : null)
                        .queryParam(
                                "searchType",
                                search.getFilter() != null
                                        ? search.getFilter().getSearchType()
                                        : null)
                        .queryParam(
                                "firstName",
                                search.getFilter() != null
                                        ? search.getFilter().getFirstName()
                                        : null)
                        .queryParam(
                                "courtLevel",
                                search.getFilter() != null
                                        ? search.getFilter().getCourtLevel()
                                        : null)
                        .queryParam(
                                "page_",
                                search.getFilter() != null ? search.getFilter().getPage() : null)
                        .queryParam(
                                "name_",
                                search.getFilter() != null ? search.getFilter().getName() : null)
                        .queryParam(
                                "roleType",
                                search.getFilter() != null
                                        ? search.getFilter().getRoleType()
                                        : null);
        addEndpointHeader("PartyNameSearch");
        try {
            HttpEntity<SearchResults> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.GET,
                            new HttpEntity<>(new HttpHeaders()),
                            SearchResults.class);
            PartyNameSearchResponse pns = new PartyNameSearchResponse();
            pns.setSearchResults(
                    resp.getBody() != null && resp.getBody().getResults().size() > 0
                            ? resp.getBody()
                            : null);
            log.info(
                    objectMapper.writeValueAsString(
                            new RequestSuccessLog("Request Success", "PartyNameSearch")));
            return pns;
        } catch (Exception ex) {
            if (search.getFilter() != null) {
                search.getFilter().setName("");
                search.getFilter().setFirstName("");
            }
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "PartyNameSearch",
                                    ex.getMessage(),
                                    search)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "saveHearingResults")
    @ResponsePayload
    public SaveHearingResultsResponse saveHearingResults(@RequestPayload SaveHearingResults search)
            throws IOException {

        var inner =
                search.getHearingResult() != null
                                && search.getHearingResult().getHearingResult() != null
                                && search.getHearingResult().getHearingResult().getCaseDetails()
                                        != null
                        ? search.getHearingResult().getHearingResult().getCaseDetails()
                        : new CaseDetails();
        addEndpointHeader("saveHearingResults");

        if (inner.getCaseAugmentation() == null
                || inner.getCaseAugmentation().getCaseHearing() == null
                || inner.getCaseAugmentation().getCaseHearing().getCourtEventAppearance() == null) {
            log.warn(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Empty Search Request", "SaveHearingResult", "", null)));
            throw new ORDSException();
        }

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(host + "SaveHearingResult");

        HttpEntity<CaseDetails> payload = new HttpEntity<>(inner, new HttpHeaders());

        try {
            HttpEntity<String> resp =
                    restTemplate.exchange(
                            builder.toUriString(), HttpMethod.POST, payload, String.class);
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "SaveHearingResult",
                                    ex.getMessage(),
                                    inner)));
            throw new ORDSException();
        }
        log.info(
                objectMapper.writeValueAsString(
                        new RequestSuccessLog("Request Success", "SaveHearingResult")));
        return new SaveHearingResultsResponse();
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
