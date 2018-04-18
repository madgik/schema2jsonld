package eu.eoscpilot.schema2jsonld.web.bluebridge;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component("bluebridge-documentretrieval")
@ConfigurationProperties(prefix = "bluebridge.documentretrieval")
public class DocumentRetrievalConfig {
    private String token;
    private String datasetEndpoint;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDatasetEndpoint() {
        return datasetEndpoint;
    }

    public void setDatasetEndpoint(String datasetEndpoint) {
        this.datasetEndpoint = datasetEndpoint;
    }
}
