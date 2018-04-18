package eu.eoscpilot.schema2jsonld.web.bluebridge;

import eu.eoscpilot.schema2jsonld.web.common.DocumentParsingService;
import eu.eoscpilot.schema2jsonld.web.common.JsonldDocument;
import eu.eoscpilot.schema2jsonld.web.exception.ApplicationException;
import eu.eoscpilot.schema2jsonld.web.exception.ValidationException;
import eu.eoscpilot.schema2jsonld.web.tools.JsonldDocumentImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component("bluebridgeDocumentParsing")
public class DocumentParsingServiceImpl implements DocumentParsingService {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
//    private PortalAccessUrlGeneratorService portalUrlGenerator;

    @Autowired
    public DocumentParsingServiceImpl(
//            @Qualifier("bluebridgePortalAccessUrlGenerator") PortalAccessUrlGeneratorService portalUrlGenerator
        ) {
//        this.portalUrlGenerator = portalUrlGenerator;
    }

    @Override
    public JsonldDocument parse(eu.eoscpilot.schema2jsonld.web.common.Key key, String content) {
        if(!(key instanceof BlueBridgeKey)) throw new ApplicationException("unsupported key type");
        try {
            JsonParsingHelper helper = null;

            switch(((BlueBridgeKey) key).getType()){
                case DATASET:{
                    helper = new JsonParsingDatasetHelper(content).build();
                    break;
                }
                default:throw new ValidationException(String.format("unsupported type %s", ((BlueBridgeKey) key).getType()));
            }

            JsonldDocumentImpl doc = new JsonldDocumentImpl();
            doc.setTitle(helper.getTitle());
            doc.setDescription(helper.getDescription());
            doc.setIdentifier(helper.getIdentifier());
            doc.setUrl(helper.getUrl());
            doc.setCreator(helper.getCreator());
            doc.setDateCreated(helper.getDateCreated());
            doc.setDateModified(helper.getDateModified());
            doc.setLicense(helper.getLicense());
            doc.setKeyword(helper.getKeyword());

            return doc;
        } catch (Exception ex) {
            logger.error("problem parsing document", ex);
            return null;
        }
    }

}
