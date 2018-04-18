package eu.eoscpilot.schema2jsonld.web.ebimetagenomics;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "ebimetagenomics.identifier")
public class IdentifierGeneratorConfig {
    private String schemaName;
    private String urlTemaplate;

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getUrlTemaplate() {
        return urlTemaplate;
    }

    public void setUrlTemaplate(String urlTemaplate) {
        this.urlTemaplate = urlTemaplate;
    }
}
