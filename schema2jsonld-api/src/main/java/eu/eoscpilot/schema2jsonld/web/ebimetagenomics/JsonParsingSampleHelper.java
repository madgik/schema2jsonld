package eu.eoscpilot.schema2jsonld.web.ebimetagenomics;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.eoscpilot.schema2jsonld.web.exception.ApplicationException;
import eu.eoscpilot.schema2jsonld.web.tools.JsonldDocumentImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class JsonParsingSampleHelper implements JsonParsingHelper {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static SimpleDateFormat iso8601DateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    private String content;
    private JsonNode document;
    private static final ObjectMapper mapper = new ObjectMapper();


    public JsonParsingSampleHelper(String content){
        this.content = content;
    }

    public JsonParsingSampleHelper build() {
        this.parseDocument(this.content);
        this.content = null;
        return this;
    }

    private void parseDocument(String content) {
        try {
            this.document = mapper.readTree(content);
        } catch (Exception ex) {
            logger.error("problem parsing document", ex);
            throw new ApplicationException("problem parsing document");
        }
    }

    public List<String> getTitle(){
        String val = this.document.path("data").path("attributes").path("sample-name").asText();
        if(val == null || val.isEmpty()) return null;
        return Arrays.asList(val);
    }

    public List<String> getDescription(){
        String val = this.document.path("data").path("attributes").path("sample-desc").asText();
        if(val == null || val.isEmpty()) return null;
        return Arrays.asList(val);
    }

    public String getIdentifier(){
        String val = this.document.path("data").path("attributes").path("biosample").asText();
        if(val == null || val.isEmpty()) return null;
        return val;
    }

    public List<JsonldDocumentImpl.Creator> getCreator(){
        return null;
    }

    public Date getDateModified() {
        String dateString = this.document.path("data").path("attributes").path("last-update").asText();
        if (dateString == null || dateString.isEmpty()) return null;
        Date d = null;
        try {
            d = JsonParsingSampleHelper.iso8601DateFormatter.parse(dateString);
        } catch (Exception ex) {
            logger.error(String.format("problem parsing date %s", dateString), ex);
        }
        return d;
    }

    public List<String> getSameAs(){
        String val = this.document.path("data").path("links").path("self").asText();
        if(val == null || val.isEmpty()) return null;
        return Arrays.asList(val);
    }
}
