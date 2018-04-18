package eu.eoscpilot.schema2jsonld.web.tools;

import eu.eoscpilot.schema2jsonld.web.common.JsonldDocument;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class JsonldDocumentImpl implements JsonldDocument {

    public static class Identifier{
        private String schema;
        private String id;

        public Identifier(){}

        public Identifier(String schema, String id){
            this.schema = schema;
            this.id = id;
        }

        public String getSchema() {
            return schema;
        }

        public void setSchema(String schema) {
            this.schema = schema;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public static class Creator {}

    public static class Person extends Creator {
        private String givenName;
        private String familyName;
        private String name;

        public Person(){}

        public Person(String givenName, String familyName, String name){
            this.givenName = givenName;
            this.familyName = familyName;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGivenName() {
            return givenName;
        }

        public void setGivenName(String givenName) {
            this.givenName = givenName;
        }

        public String getFamilyName() {
            return familyName;
        }

        public void setFamilyName(String familyName) {
            this.familyName = familyName;
        }
    }

    public static class Organization extends Creator {
        private String name;

        public Organization(){}

        public Organization(String name){
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class Citation {
        private List<String> title;
        private List<Identifier> identifier;

        public List<String> getTitle() {
            return title;
        }

        public void setTitle(List<String> title) {
            this.title = title;
        }

        public List<Identifier> getIdentifier() {
            return identifier;
        }

        public void setIdentifier(List<Identifier> identifier) {
            this.identifier = identifier;
        }
    }

    public static class License {
        private List<String> title;
        private List<Identifier> identifier;

        public List<String> getTitle() {
            return title;
        }

        public void setTitle(List<String> title) {
            this.title = title;
        }

        public List<Identifier> getIdentifier() {
            return identifier;
        }

        public void setIdentifier(List<Identifier> identifier) {
            this.identifier = identifier;
        }
    }

    private List<String> title;
    private List<String> description;
    private List<Identifier> identifier;
    private List<String> url;
    private List<String> sameAs;
    private List<Creator> creator;
    private List<Date> dateCreated;
    private List<Date> dateModified;
    private List<Citation> citation;
    private List<License> license;
    private List<String> keyword;

    public List<String> getKeyword() {
        return keyword;
    }

    public void setKeyword(List<String> keyword) {
        this.keyword = keyword;
    }

    public List<License> getLicense() {
        return license;
    }

    public void setLicense(List<License> license) {
        this.license = license;
    }

    public List<Citation> getCitation() {
        return citation;
    }

    public void setCitation(List<Citation> citation) {
        this.citation = citation;
    }

    public List<Date> getDateModified() {
        return dateModified;
    }

    public void setDateModified(List<Date> dateModified) {
        this.dateModified = dateModified;
    }

    public void setDateModified(Date dateModified) {
        if(dateModified == null) this.dateModified = null;
        else this.dateModified = Arrays.asList(dateModified);
    }

    public List<Date> getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        if(dateCreated == null) this.dateCreated = null;
        else this.dateCreated = Arrays.asList(dateCreated);
    }

    public void setDateCreated(List<Date> dateCreated) {
        this.dateCreated = dateCreated;
    }

    public List<Creator> getCreator() {
        return creator;
    }

    public void setCreator(List<Creator> creator) {
        this.creator = creator;
    }

    public List<String> getSameAs() {
        return sameAs;
    }

    public void setSameAs(List<String> sameAs) {
        this.sameAs = sameAs;
    }

    public List<String> getUrl() {
        return url;
    }

    public void setUrl(String url) {
        if(url == null || url.isEmpty()) return;
        this.setUrl(Arrays.asList(url));
    }

    public void setUrl(List<String> url) {
        this.url = url;
    }

    public List<Identifier> getIdentifier() {
        return identifier;
    }

    public void setIdentifier(List<Identifier> identifier) {
        this.identifier = identifier;
    }

    public void setIdentifier(Identifier identifier) {
        if(identifier == null) this.identifier = null;
        else this.identifier = Arrays.asList(identifier);
    }

    public List<String> getDescription() {
        return description;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }

    public List<String> getTitle() {
        return title;
    }

    public void setTitle(List<String> title) {
        this.title = title;
    }

}
