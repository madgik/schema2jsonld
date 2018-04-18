package eu.eoscpilot.schema2jsonld.web.bluebridge;

import eu.eoscpilot.schema2jsonld.web.common.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("bluebridge-conversionservice")
public class ConversionServiceImpl extends ConversionService {
    public ConversionServiceImpl(
            @Qualifier("bluebridgeKeyResolver") KeyResolverService keyResolver,
            @Qualifier("bluebridgeDocumentRetrieval") DocumentRetrievalService documentRetrieval,
            @Qualifier("bluebridgeDocumentParsing") DocumentParsingService documentParsing,
            @Qualifier("toolsJsonldSerializer") JsonldSerializer documentSerializer) {
        super(keyResolver, documentRetrieval, documentParsing, documentSerializer);
    }
}
