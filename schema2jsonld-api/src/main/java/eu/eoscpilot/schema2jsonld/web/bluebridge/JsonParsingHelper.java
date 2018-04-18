package eu.eoscpilot.schema2jsonld.web.bluebridge;

import eu.eoscpilot.schema2jsonld.web.tools.JsonldDocumentImpl;

import java.util.Date;
import java.util.List;

public interface JsonParsingHelper {
    public List<String> getTitle();

    public List<String> getDescription();

    public JsonldDocumentImpl.Identifier getIdentifier();

    public String getUrl();

    public List<JsonldDocumentImpl.Creator> getCreator();

    public List<JsonldDocumentImpl.License> getLicense();

    public Date getDateCreated();

    public Date getDateModified();

    public List<String> getKeyword();

}
