package eu.eoscpilot.schema2jsonld.web.openaire;

import eu.eoscpilot.schema2jsonld.web.common.DocumentParsingService;
import eu.eoscpilot.schema2jsonld.web.common.JsonldDocument;
import eu.eoscpilot.schema2jsonld.web.exception.ApplicationException;
import eu.eoscpilot.schema2jsonld.web.tools.JsonldDocumentImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("openAireDocumentParsing")
public class DocumentParsingServiceImpl implements DocumentParsingService {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private PortalAccessUrlGeneratorService portalUrlgenerator;

    @Autowired
    public DocumentParsingServiceImpl(
            @Qualifier("openAirePortalAccessUrlGenerator") PortalAccessUrlGeneratorService portalUrlgenerator) {
        this.portalUrlgenerator = portalUrlgenerator;
    }

    @Override
    public JsonldDocument parse(eu.eoscpilot.schema2jsonld.web.common.Key key, String content) {
        if(!(key instanceof OpenAireKey)) throw new ApplicationException("unsupported key type");
        try {
            XmlParsingHelper helper = new XmlParsingHelper(content).build();

            JsonldDocumentImpl doc = new JsonldDocumentImpl();
            doc.setTitle(helper.getTitle());
            doc.setDescription(helper.getDescription());
            doc.setIdentifier(helper.getIdentifiers());
            doc.setUrl(this.portalUrlgenerator.getUrl((OpenAireKey)key));
            doc.setSameAs(helper.getSameAs());
            doc.setCreator(helper.getCreators());
            doc.setDateCreated(helper.getDateCreated());
            doc.setCitation(helper.getCitations());
            doc.setLicense(helper.getLicenses());
            doc.setKeyword(helper.getKeyword());

            return doc;
        } catch (Exception ex) {
            logger.error("problem parsing document", ex);
            return null;
        }
    }
}
