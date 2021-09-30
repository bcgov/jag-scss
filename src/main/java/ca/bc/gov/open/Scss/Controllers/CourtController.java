package ca.bc.gov.open.Scss.Controllers;

import ca.bc.gov.open.Scss.Configuration.SoapConfig;
import ca.bc.gov.open.Scss.Exceptions.BadDateException;
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
            throws JsonProcessingException {
        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(host + "GetCourtFile")
                        .queryParam("physicalFileId", search.getPhysicalFileId());

        try {
            HttpEntity<GetCourtFileResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.GET,
                            new HttpEntity<>(new HttpHeaders()),
                            GetCourtFileResponse.class);

            return resp.getBody();
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog("Error received from ORDS", "GetCourtFile", search)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "getCourtBasics")
    @ResponsePayload
    public GetCourtBasicsResponse getCourtBasics(@RequestPayload GetCourtBasics search)
            throws JsonProcessingException {
        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(host + "GetCourtBasics")
                        .queryParam("physicalFileId", search.getPhysicalFileId());
        try {
            HttpEntity<CaseBasics> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.GET,
                            new HttpEntity<>(new HttpHeaders()),
                            CaseBasics.class);
            GetCourtBasicsResponse cbr = new GetCourtBasicsResponse();
            cbr.setCaseBasics(resp.getBody());
            return cbr;
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS", "GetCourtBasics", search)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "getCeisConnectInfo")
    @ResponsePayload
    public GetCeisConnectInfoResponse getCeisConnectInfo(@RequestPayload GetCeisConnectInfo search)
            throws JsonProcessingException {
        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(host + "GetCeisConnectInfo");

        try {
            HttpEntity<GetCeisConnectInfoResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.GET,
                            new HttpEntity<>(new HttpHeaders()),
                            GetCeisConnectInfoResponse.class);
            return resp.getBody();
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS", "GetCeisConnectInfo", search)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "getParties")
    @ResponsePayload
    public GetPartiesResponse getParties(@RequestPayload GetParties search)
            throws JsonProcessingException {
        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(host + "GetParties")
                        .queryParam("physicalFileId", search.getPhysicalFileId());

        try {
            HttpEntity<GetPartiesResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.GET,
                            new HttpEntity<>(new HttpHeaders()),
                            GetPartiesResponse.class);
            return resp.getBody();
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog("Error received from ORDS", "GetParties", search)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "partyNameSearch")
    @ResponsePayload
    public PartyNameSearchResponse partyNameSearch(@RequestPayload PartyNameSearch search)
            throws JsonProcessingException {
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
            return pns;
        } catch (Exception ex) {
            search.getFilter().setName("");
            search.getFilter().setFirstName("");
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS", "PartyNameSearch", search)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "saveHearingResults")
    @ResponsePayload
    public SaveHearingResultsResponse saveHearingResults(@RequestPayload SaveHearingResults search)
            throws BadDateException, JsonProcessingException {

        var inner =
                search.getHearingResult() != null
                                && search.getHearingResult().getHearingResult() != null
                                && search.getHearingResult().getHearingResult().getCaseDetails()
                                        != null
                        ? search.getHearingResult().getHearingResult().getCaseDetails()
                        : new CaseDetails();

        if (inner.getCaseAugmentation()
                        .getCaseHearing()
                        .getCourtEventAppearance()
                        .getCourtAppearanceDate()
                == null) {
            throw new BadDateException();
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
                                    "Error received from ORDS", "SaveHearingResult", inner)));
            throw new ORDSException();
        }
        return new SaveHearingResultsResponse();
    }
}
