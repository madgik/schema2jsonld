package eu.eoscpilot.schema2jsonld.web.ebimetagenomics;

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

@Component("ebiMetagenomicsDocumentParsing")
public class DocumentParsingServiceImpl implements DocumentParsingService {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private IdentifierGeneratorService identifierGenerator;
    private PortalAccessUrlGeneratorService portalUrlGenerator;

    @Autowired
    public DocumentParsingServiceImpl(
            @Qualifier("ebiMetagenomicsIdentifierGenerator") IdentifierGeneratorService identifierGenerator,
            @Qualifier("ebimetagenomicsPortalAccessUrlGenerator") PortalAccessUrlGeneratorService portalUrlGenerator) {
        this.identifierGenerator = identifierGenerator;
        this.portalUrlGenerator = portalUrlGenerator;
    }

    @Override
    public JsonldDocument parse(eu.eoscpilot.schema2jsonld.web.common.Key key, String content) {
        if(!(key instanceof EbiMetagenomicsKey)) throw new ApplicationException("unsupported key type");
        try {
            JsonParsingHelper helper = null;

            switch(((EbiMetagenomicsKey) key).getType()){
                case STUDY:{
                    helper = new JsonParsingStudyHelper(content).build();
                    break;
                }
                case SAMPLE:{
                    helper = new JsonParsingSampleHelper(content).build();
                    break;
                }
                default:throw new ValidationException(String.format("unsupported type %s", ((EbiMetagenomicsKey) key).getType()));
            }

            JsonldDocumentImpl doc = new JsonldDocumentImpl();
            doc.setTitle(helper.getTitle());
            doc.setDescription(helper.getDescription());
            doc.setIdentifier(this.identifierGenerator.getIdentifier(helper.getIdentifier()));
            doc.setUrl(this.portalUrlGenerator.getUrl((EbiMetagenomicsKey)key));
            doc.setCreator(helper.getCreator());
            doc.setDateModified(helper.getDateModified());
            doc.setSameAs(helper.getSameAs());

            return doc;
        } catch (Exception ex) {
            logger.error("problem parsing document", ex);
            return null;
        }
    }

}
