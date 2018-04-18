package eu.eoscpilot.schema2jsonld.web.openaire;

import eu.eoscpilot.schema2jsonld.web.common.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("openaire-conversionservice")
public class ConversionServiceImpl extends ConversionService {
    public ConversionServiceImpl(
            @Qualifier("openAireKeyResolver") KeyResolverService keyResolver,
            @Qualifier("openAireDocumentRetrieval") DocumentRetrievalService documentRetrieval,
            @Qualifier("openAireDocumentParsing") DocumentParsingService documentParsing,
            @Qualifier("toolsJsonldSerializer") JsonldSerializer documentSerializer) {
        super(keyResolver, documentRetrieval, documentParsing, documentSerializer);
    }
}
