package ca.bc.gov.open.scss.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "scss")
public class SCSSProperties {

    private String host;
    private String username;
    private String password;
    private String ordsReadTimeout;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOrdsReadTimeout() {
        return ordsReadTimeout;
    }

    public void setOrdsReadTimeout(String ordsReadTimeout) {
        this.ordsReadTimeout = ordsReadTimeout;
    }

}
