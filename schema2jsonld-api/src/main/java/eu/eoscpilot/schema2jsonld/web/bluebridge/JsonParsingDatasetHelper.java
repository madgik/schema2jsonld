package eu.eoscpilot.schema2jsonld.web.bluebridge;

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

public class JsonParsingDatasetHelper implements JsonParsingHelper {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static SimpleDateFormat iso8601DateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    private String content;
    private JsonNode document;
    private static final ObjectMapper mapper = new ObjectMapper();


    public JsonParsingDatasetHelper(String content){
        this.content = content;
    }

    public JsonParsingDatasetHelper build() {
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
        String val = this.document.path("result").path("title").asText();
        if(val == null || val.isEmpty()) return null;
        return Arrays.asList(val);
    }

    public List<String> getDescription(){
        String val = this.document.path("result").path("notes").asText();
        if(val == null || val.isEmpty()) return null;
        return Arrays.asList(val);
    }

    public JsonldDocumentImpl.Identifier getIdentifier(){
        JsonNode extras = this.document.path("result").path("extras");
        if(extras == null) return null;

        for(JsonNode item : extras){
            String key = item.path("key").asText();
            if(key == null || key.isEmpty()) continue;
            if(!key.equalsIgnoreCase("item url")) continue;
            String value = item.path("value").asText();
            if(value == null || value.isEmpty()) continue;

            return new JsonldDocumentImpl.Identifier(key, value);
        }
        return null;
    }

    public String getUrl(){
        JsonNode extras = this.document.path("result").path("extras");
        if(extras == null) return null;

        for(JsonNode item : extras){
            String key = item.path("key").asText();
            if(key == null || key.isEmpty()) continue;
            if(!key.equalsIgnoreCase("item url")) continue;
            String value = item.path("value").asText();
            if(value == null || value.isEmpty()) continue;

            return value;
        }
        return null;
    }

    public List<JsonldDocumentImpl.Creator> getCreator(){
        String val = this.document.path("result").path("author").asText();
        if(val == null || val.isEmpty()) return null;
        return Arrays.asList(new JsonldDocumentImpl.Organization(val));
    }

    public Date getDateCreated() {
        String dateString = this.document.path("result").path("metadata_created").asText();
        if (dateString == null || dateString.isEmpty()) return null;
        Date d = null;
        try {
            d = JsonParsingDatasetHelper.iso8601DateFormatter.parse(dateString);
        } catch (Exception ex) {
            logger.error(String.format("problem parsing date %s", dateString), ex);
        }
        return d;
    }

    public Date getDateModified() {
        String dateString = this.document.path("result").path("metadata_modified").asText();
        if (dateString == null || dateString.isEmpty()) return null;
        Date d = null;
        try {
            d = JsonParsingDatasetHelper.iso8601DateFormatter.parse(dateString);
        } catch (Exception ex) {
            logger.error(String.format("problem parsing date %s", dateString), ex);
        }
        return d;
    }

    public List<JsonldDocumentImpl.License> getLicense(){
        String lic_id = this.document.path("result").path("license_id").asText();
        String lic_title = this.document.path("result").path("license_title").asText();
        String lic_url = this.document.path("result").path("license_url").asText();

        JsonldDocumentImpl.License license = new JsonldDocumentImpl.License();
        if(lic_title !=null && !lic_title.isEmpty()) license.setTitle(Arrays.asList(lic_title));
        if(lic_id != null && !lic_id.isEmpty() && lic_url != null && !lic_url.isEmpty()) license.setIdentifier(Arrays.asList(new JsonldDocumentImpl.Identifier(lic_id, lic_url)));

        return Arrays.asList(license);
    }

    public List<String> getKeyword(){
        JsonNode tags = this.document.path("result").path("tags");
        if(tags == null) return null;

        List<String> values = new ArrayList<>();
        for(JsonNode item : tags){
            String tag = item.path("display_name").asText();
            if(tag == null || tag.isEmpty()) continue;

            values.add(tag);
        }
        return values;
    }
}
