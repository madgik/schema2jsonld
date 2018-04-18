package eu.eoscpilot.schema2jsonld.web.ebimetagenomics;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component("ebimetagenomics-documentretrieval")
@ConfigurationProperties(prefix = "ebimetagenomics.documentretrieval")
public class DocumentRetrievalConfig {
    private String studyEndpoint;
    private String sampleEndpoint;

    public String getSampleEndpoint() {
        return sampleEndpoint;
    }

    public void setSampleEndpoint(String sampleEndpoint) {
        this.sampleEndpoint = sampleEndpoint;
    }

    public String getStudyEndpoint() {
        return studyEndpoint;
    }

    public void setStudyEndpoint(String studyEndpoint) {
        this.studyEndpoint = studyEndpoint;
    }
}
