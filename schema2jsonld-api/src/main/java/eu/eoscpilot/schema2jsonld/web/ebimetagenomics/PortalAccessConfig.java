package eu.eoscpilot.schema2jsonld.web.ebimetagenomics;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component("ebimetagenomics-portalaccessconfig")
@ConfigurationProperties(prefix = "ebimetagenomics.portalaccess")
public class PortalAccessConfig {
    private String urlEndpoint;

    public String getUrlEndpoint() {
        return urlEndpoint;
    }

    public void setUrlEndpoint(String urlEndpoint) {
        this.urlEndpoint = urlEndpoint;
    }
}
