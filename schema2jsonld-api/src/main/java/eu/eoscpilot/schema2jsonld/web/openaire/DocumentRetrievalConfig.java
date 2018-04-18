package eu.eoscpilot.schema2jsonld.web.openaire;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component("openaire-documentretrieval")
@ConfigurationProperties(prefix = "openaire.documentretrieval")
public class DocumentRetrievalConfig {

    private String publicationEndpoint;
    private String softwareEndpoint;
    private String datasetEndpoint;
    private String projectEndpoint;

    public String getPublicationEndpoint() {
        return publicationEndpoint;
    }

    public void setPublicationEndpoint(String publicationEndpoint) {
        this.publicationEndpoint = publicationEndpoint;
    }

    public String getSoftwareEndpoint() {
        return softwareEndpoint;
    }

    public void setSoftwareEndpoint(String softwareEndpoint) {
        this.softwareEndpoint = softwareEndpoint;
    }

    public String getDatasetEndpoint() {
        return datasetEndpoint;
    }

    public void setDatasetEndpoint(String datasetEndpoint) {
        this.datasetEndpoint = datasetEndpoint;
    }

    public String getProjectEndpoint() {
        return projectEndpoint;
    }

    public void setProjectEndpoint(String projectEndpoint) {
        this.projectEndpoint = projectEndpoint;
    }
}
