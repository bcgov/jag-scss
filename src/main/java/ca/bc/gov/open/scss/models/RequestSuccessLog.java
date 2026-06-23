package ca.bc.gov.open.scss.models;

public class RequestSuccessLog {

    public RequestSuccessLog(String type, String endpoint) {
        this.type = type;
        this.endpoint = endpoint;
    }

    private String type;
    private String endpoint;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

}
