package eu.eoscpilot.schema2jsonld.web.openaire;

import eu.eoscpilot.schema2jsonld.web.exception.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("openAirePortalAccessUrlGenerator")
public class PortalAccessUrlGeneratorService {

    private PortalAccessConfig config;

    @Autowired
    public PortalAccessUrlGeneratorService(PortalAccessConfig config){
        this.config = config;
    }

    public String getUrl(OpenAireKey key){
        String template = null;
        switch(key.getType()){
            case PUBLICATION:{
                template = this.config.getPublicationEndpoint();
                break;
            }
            case SOFTWARE:{
                template = this.config.getSoftwareEndpoint();
                break;
            }
            case PROJECT: {
                template = this.config.getProjectEndpoint();
                break;
            }
            case DATASET:{
                template = this.config.getDatasetEndpoint();
                break;
            }
            default: throw new ApplicationException(String.format("unrecognized key type: %s", key.getType().toString()));
        }
        if(template == null || template.isEmpty()) return null;
        String url = template.replace("{id}", key.getId());
        return url;
    }
}
