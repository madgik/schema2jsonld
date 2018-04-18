package eu.eoscpilot.schema2jsonld.web.ebimetagenomics;

import eu.eoscpilot.schema2jsonld.web.common.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("ebimetagenomics-conversionservice")
public class ConversionServiceImpl extends ConversionService {
    public ConversionServiceImpl(
            @Qualifier("ebiMetagenomicsKeyResolver") KeyResolverService keyResolver,
            @Qualifier("ebiMetagenomicsDocumentRetrieval") DocumentRetrievalService documentRetrieval,
            @Qualifier("ebiMetagenomicsDocumentParsing") DocumentParsingService documentParsing,
            @Qualifier("toolsJsonldSerializer") JsonldSerializer documentSerializer) {
        super(keyResolver, documentRetrieval, documentParsing, documentSerializer);
    }
}
