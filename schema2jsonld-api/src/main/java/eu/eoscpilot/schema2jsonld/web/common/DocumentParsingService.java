package eu.eoscpilot.schema2jsonld.web.common;

public interface DocumentParsingService {
    public JsonldDocument parse(Key key, String content);
}
