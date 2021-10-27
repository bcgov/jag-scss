package ca.bc.gov.open.scss.exceptions;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

@SoapFault(
        faultCode = FaultCode.CLIENT,
        faultStringOrReason = "Unparsable date must be of form yyyy-MM-dd")
public class BadDateException extends Exception {
    public BadDateException() {
        super();
    }
}
