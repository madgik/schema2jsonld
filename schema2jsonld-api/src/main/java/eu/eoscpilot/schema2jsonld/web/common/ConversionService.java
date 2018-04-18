package eu.eoscpilot.schema2jsonld.web.common;

public abstract class ConversionService {

    protected KeyResolverService keyResolver;
    protected DocumentRetrievalService documentRetrieval;
    protected DocumentParsingService documentParsing;
    protected JsonldSerializer documentSerializer;

    public ConversionService(KeyResolverService keyResolver,
                                 DocumentRetrievalService documentRetrieval,
                                 DocumentParsingService documentParsing,
                                 JsonldSerializer documentSerializer) {
        this.keyResolver = keyResolver;
        this.documentRetrieval = documentRetrieval;
        this.documentParsing = documentParsing;
        this.documentSerializer = documentSerializer;
    }

    public String Convert(String id){
        Key key = this.keyResolver.resolve(id);
        String content = this.documentRetrieval.retrieve(key);
        JsonldDocument document = this.documentParsing.parse(key, content);
        String jsonld = this.documentSerializer.serialize(document);
        return jsonld;
    }
}
