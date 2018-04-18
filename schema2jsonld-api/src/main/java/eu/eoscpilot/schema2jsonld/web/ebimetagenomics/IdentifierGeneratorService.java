package eu.eoscpilot.schema2jsonld.web.ebimetagenomics;

import eu.eoscpilot.schema2jsonld.web.tools.JsonldDocumentImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("ebiMetagenomicsIdentifierGenerator")
public class IdentifierGeneratorService {

    private IdentifierGeneratorConfig config;

    @Autowired
    public IdentifierGeneratorService(IdentifierGeneratorConfig config) {
        this.config = config;
    }

    public JsonldDocumentImpl.Identifier getIdentifier(String key){
        String value = this.config.getUrlTemaplate().replace("{id}", key);
        return new JsonldDocumentImpl.Identifier(this.config.getSchemaName(), value);
    }
}
