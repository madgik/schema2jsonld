package eu.eoscpilot.schema2jsonld.web.ebimetagenomics;

import eu.eoscpilot.schema2jsonld.web.exception.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("ebimetagenomicsPortalAccessUrlGenerator")
public class PortalAccessUrlGeneratorService {

    private PortalAccessConfig config;

    @Autowired
    public PortalAccessUrlGeneratorService(PortalAccessConfig config){
        this.config = config;
    }

    public String getUrl(EbiMetagenomicsKey key){
        String template = this.config.getUrlEndpoint();
        if(template == null || template.isEmpty()) return null;
        String url = template.replace("{id}", key.getId());
        return url;
    }
}
