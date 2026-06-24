package ca.bc.gov.open.scss.models;

public class OrdsErrorLog {

    public OrdsErrorLog(String message, String method, String exception, Object request) {
        this.message = message;
        this.method = method;
        this.exception = exception;
        this.request = request;
    }

    private String message;
    private String method;
    private String exception;
    private Object request;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public Object getRequest() {
        return request;
    }

    public void setRequest(Object request) {
        this.request = request;
    }

}
