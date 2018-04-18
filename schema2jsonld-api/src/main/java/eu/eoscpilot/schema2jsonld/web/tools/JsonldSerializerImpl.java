package eu.eoscpilot.schema2jsonld.web.tools;

import eu.eoscpilot.schema2jsonld.web.common.JsonldDocument;
import eu.eoscpilot.schema2jsonld.web.common.JsonldSerializer;
import eu.eoscpilot.schema2jsonld.web.exception.ApplicationException;
import org.springframework.stereotype.Component;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component("toolsJsonldSerializer")
public class JsonldSerializerImpl implements JsonldSerializer {

    private static SimpleDateFormat iso8601DateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public String serialize(JsonldDocument document) {
        if(!(document instanceof JsonldDocumentImpl)) throw new ApplicationException("unsupported document type");

        JsonldDocumentImpl doc = (JsonldDocumentImpl)document;

        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("@context", "http://schema.org")
                .add("@type", "Dataset");
        this.addStrings(builder, "name", doc.getTitle());
        this.addStrings(builder, "description", doc.getDescription());
        this.addIdentifiers(builder, "identifier", doc.getIdentifier());
        this.addStrings(builder, "url", doc.getUrl());
        this.addStrings(builder, "sameAs", doc.getSameAs());
        this.addCreators(builder, "creator", doc.getCreator());
        this.addDates(builder, "dateCreated", doc.getDateCreated());
        this.addDates(builder, "dateModified", doc.getDateModified());
        this.addCitations(builder, "citation", doc.getCitation());
        this.addLicenses(builder, "license", doc.getLicense());
        this.addCsv(builder, "keywords", doc.getKeyword());

        return builder.build().toString();
    }

    private void addStrings(JsonObjectBuilder builder, String name, List<String> values){
        if(values == null) return;

        if(values.size() == 1){
            builder.add(name, values.get(0));
        }
        else {
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            for (String val : values) {
                arrayBuilder.add(val);
            }
            builder.add(name, arrayBuilder);
        }
    }

    private void addCsv(JsonObjectBuilder builder, String name, List<String> values){
        if(values == null || values.size() == 0) return;

        if(values.size() == 1){
            builder.add(name, values.get(0));
        }
        else {
            String csv = String.join(", ", values);
            builder.add(name, csv);
        }
    }

    private void addDates(JsonObjectBuilder builder, String name, List<Date> values){
        if(values == null) return;

        if(values.size() == 1){
            builder.add(name, JsonldSerializerImpl.iso8601DateFormatter.format(values.get(0)));
        }
        else {
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            for (Date val : values) {
                arrayBuilder.add(JsonldSerializerImpl.iso8601DateFormatter.format(val));
            }
            builder.add(name, arrayBuilder);
        }
    }

    private void addIdentifiers(JsonObjectBuilder builder, String name, List<JsonldDocumentImpl.Identifier> values){
        if(values == null) return;

        if(values.size() == 1){
            builder.add(name, this.create(values.get(0)));
        }
        else {
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            for (JsonldDocumentImpl.Identifier val : values) {
                arrayBuilder.add(this.create(val));
            }
            builder.add(name, arrayBuilder);
        }
    }

    private JsonObjectBuilder create(JsonldDocumentImpl.Identifier value){
        return Json.createObjectBuilder()
                .add("@type", "PropertyValue")
                .add("propertyID", value.getSchema())
                .add("value", value.getId());
    }

    private void addCreators(JsonObjectBuilder builder, String name, List<JsonldDocumentImpl.Creator> values){
        if(values == null) return;

        if(values.size() == 1){
            builder.add(name, this.create(values.get(0)));
        }
        else {
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            for (JsonldDocumentImpl.Creator val : values) {
                arrayBuilder.add(this.create(val));
            }
            builder.add(name, arrayBuilder);
        }
    }

    private JsonObjectBuilder create(JsonldDocumentImpl.Creator value){

        JsonObjectBuilder builder = Json.createObjectBuilder();
        if(value instanceof JsonldDocumentImpl.Person) {
            JsonldDocumentImpl.Person person = (JsonldDocumentImpl.Person)value;
            builder.add("@type", "Person");
            if (person.getGivenName() != null && !person.getGivenName().isEmpty())
                builder.add("givenName", person.getGivenName());
            if (person.getFamilyName() != null && !person.getFamilyName().isEmpty())
                builder.add("familyName", person.getFamilyName());
            if (person.getName() != null && !person.getName().isEmpty()) builder.add("name", person.getName());
        }
        else if(value instanceof JsonldDocumentImpl.Organization) {
            JsonldDocumentImpl.Organization organization = (JsonldDocumentImpl.Organization)value;
            builder.add("@type", "Organization");
            if (organization.getName() != null && !organization.getName().isEmpty()) builder.add("name", organization.getName());
        }

        return builder;
    }

    private void addCitations(JsonObjectBuilder builder, String name, List<JsonldDocumentImpl.Citation> values){
        if(values == null) return;

        if(values.size() == 1){
            builder.add(name, this.create(values.get(0)));
        }
        else {
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            for (JsonldDocumentImpl.Citation val : values) {
                arrayBuilder.add(this.create(val));
            }
            builder.add(name, arrayBuilder);
        }
    }

    private JsonObjectBuilder create(JsonldDocumentImpl.Citation value){
        JsonObjectBuilder builder = Json.createObjectBuilder()
                .add("@type", "CreativeWork");
        this.addStrings(builder, "name", value.getTitle());
        this.addIdentifiers(builder, "identifier", value.getIdentifier());

        return builder;
    }

    private void addLicenses(JsonObjectBuilder builder, String name, List<JsonldDocumentImpl.License> values){
        if(values == null) return;

        if(values.size() == 1){
            builder.add(name, this.create(values.get(0)));
        }
        else {
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            for (JsonldDocumentImpl.License val : values) {
                arrayBuilder.add(this.create(val));
            }
            builder.add(name, arrayBuilder);
        }
    }

    private JsonObjectBuilder create(JsonldDocumentImpl.License value){
        JsonObjectBuilder builder = Json.createObjectBuilder()
                .add("@type", "CreativeWork");
        this.addStrings(builder, "name", value.getTitle());
        this.addIdentifiers(builder, "identifier", value.getIdentifier());

        return builder;
    }
}
