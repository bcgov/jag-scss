package ca.bc.gov.open.Scss.Controllers;

import ca.bc.gov.open.Scss.Configuration.SoapConfig;
import com.example.demp.wsdl.*;
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
public class CourtController {

    @Value("${scss.host}")
    private String host = "http://127.0.0.1/";

    private final RestTemplate restTemplate;

    @Autowired
    public CourtController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "getCourtFile")
    @ResponsePayload
    public GetCourtFileResponse getCourtFile(@RequestPayload GetCourtFile search) {
        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(host + "GetCourtFile")
                        .queryParam("physicalFileId", search.getPhysicalFileId());

        HttpEntity<GetCourtFileResponse> resp =
                restTemplate.exchange(
                        builder.toUriString(),
                        HttpMethod.GET,
                        new HttpEntity<>(new HttpHeaders()),
                        GetCourtFileResponse.class);

        return resp.getBody();
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "getCourtBasics")
    @ResponsePayload
    public GetCourtBasicsResponse getCourtBasics(@RequestPayload GetCourtBasics search) {
        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(host + "GetCourtBasics")
                        .queryParam("physicalFileId", search.getPhysicalFileId());

        HttpEntity<CaseBasics> resp =
                restTemplate.exchange(
                        builder.toUriString(),
                        HttpMethod.GET,
                        new HttpEntity<>(new HttpHeaders()),
                        CaseBasics.class);

        GetCourtBasicsResponse cbr = new GetCourtBasicsResponse();
        cbr.setCaseBasics(resp.getBody());
        return cbr;
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "getCeisConnectInfo")
    @ResponsePayload
    public GetCeisConnectInfoResponse getCeisConnectInfo(
            @RequestPayload GetCeisConnectInfo search) {
        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(host + "GetCeisConnectInfo");

        HttpEntity<GetCeisConnectInfoResponse> resp =
                restTemplate.exchange(
                        builder.toUriString(),
                        HttpMethod.GET,
                        new HttpEntity<>(new HttpHeaders()),
                        GetCeisConnectInfoResponse.class);

        return resp.getBody();
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "getParties")
    @ResponsePayload
    public GetPartiesResponse getParties(@RequestPayload GetParties search) {
        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(host + "GetParties")
                        .queryParam("physicalFileId", search.getPhysicalFileId());

        HttpEntity<GetPartiesResponse> resp =
                restTemplate.exchange(
                        builder.toUriString(),
                        HttpMethod.GET,
                        new HttpEntity<>(new HttpHeaders()),
                        GetPartiesResponse.class);

        return resp.getBody();
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "partyNameSearch")
    @ResponsePayload
    public PartyNameSearchResponse partyNameSearch(@RequestPayload PartyNameSearch search) {
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

        HttpEntity<SearchResults> resp =
                restTemplate.exchange(
                        builder.toUriString(),
                        HttpMethod.GET,
                        new HttpEntity<>(new HttpHeaders()),
                        SearchResults.class);

        PartyNameSearchResponse pns = new PartyNameSearchResponse();
        pns.setSearchResults(resp.getBody());
        return pns;
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "saveHearingResults")
    @ResponsePayload
    public SaveHearingResultsResponse saveHearingResults(
            @RequestPayload SaveHearingResults search) {

        var inner =
                search.getHearingResult() != null
                                && search.getHearingResult().getHearingResult() != null
                                && search.getHearingResult().getHearingResult().getCaseDetails()
                                        != null
                        ? search.getHearingResult().getHearingResult().getCaseDetails()
                        : new CaseDetails();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(host + "SaveHearingResult");

        HttpEntity<CaseDetails> payload = new HttpEntity<>(inner, new HttpHeaders());

        HttpEntity<String> resp =
                restTemplate.exchange(
                        builder.toUriString(), HttpMethod.POST, payload, String.class);

        return new SaveHearingResultsResponse();
    }
}
