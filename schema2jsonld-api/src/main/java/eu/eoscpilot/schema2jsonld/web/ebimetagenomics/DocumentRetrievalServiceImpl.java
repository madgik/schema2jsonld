package eu.eoscpilot.schema2jsonld.web.ebimetagenomics;

import eu.eoscpilot.schema2jsonld.web.common.DocumentRetrievalService;
import eu.eoscpilot.schema2jsonld.web.exception.ApplicationException;
import eu.eoscpilot.schema2jsonld.web.exception.ValidationException;
import eu.eoscpilot.schema2jsonld.web.tools.HttpHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("ebiMetagenomicsDocumentRetrieval")
public class DocumentRetrievalServiceImpl implements DocumentRetrievalService {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private DocumentRetrievalConfig config;

    @Autowired
    public DocumentRetrievalServiceImpl(DocumentRetrievalConfig config){
        this.config = config;
    }

    @Override
    public String retrieve(eu.eoscpilot.schema2jsonld.web.common.Key key) {
        if (key == null || !(key instanceof EbiMetagenomicsKey))
            throw new ValidationException(String.format("unsupported key: %s", key == null ? "null" : key.getClass().getName()));

        EbiMetagenomicsKey myKey = (EbiMetagenomicsKey)key;
        String endpoint = null;
        switch(myKey.getType()) {
            case STUDY: {
                endpoint = this.config.getStudyEndpoint();
                break;
            }
            case SAMPLE: {
                endpoint = this.config.getSampleEndpoint();
                break;
            }
            default: {
                throw new ApplicationException(String.format("unrecognized key type: %s", myKey.getType().toString()));
            }
        }

        String url = endpoint.replace("{id}", myKey.getId());
        String content = null;
        try{
            content = HttpHelper.get(url);
        }catch(Exception ex) {
            this.logger.error(String.format("error retrieving url %s", url), ex);
            throw new ApplicationException(String.format("error retrieving document %s", myKey.getId()));
        }

        return content;
    }
}
