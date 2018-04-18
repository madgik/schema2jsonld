package eu.eoscpilot.schema2jsonld.web.ebimetagenomics;

import eu.eoscpilot.schema2jsonld.web.tools.JsonldDocumentImpl;

import java.util.Date;
import java.util.List;

public interface JsonParsingHelper {
    public List<String> getTitle();

    public List<String> getDescription();

    public String getIdentifier();

    public List<JsonldDocumentImpl.Creator> getCreator();

    public Date getDateModified();

    public List<String> getSameAs();

}
