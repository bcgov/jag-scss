package ca.bc.gov.open.Scss.Models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrdsErrorLog {

    private String message;
    private String method;
    private Object request;
}
