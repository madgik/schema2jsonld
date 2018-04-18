package eu.eoscpilot.schema2jsonld.web.openaire;

import eu.eoscpilot.schema2jsonld.web.exception.ApplicationException;
import eu.eoscpilot.schema2jsonld.web.tools.JsonldDocumentImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.*;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class XmlParsingHelper {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static Object lockMe = new Object();
    private static XPathExpression titleXPath = null;
    private static XPathExpression descriptionXPath = null;
    private static XPathExpression pidXPath = null;
    private static XPathExpression sameAsXPath = null;
    private static XPathExpression creatorXPath = null;
    private static XPathExpression dateCreatedXPath = null;
    private static XPathExpression citationXPath = null;
    private static XPathExpression citationNameXPath = null;
    private static XPathExpression citationIdXPath = null;
    private static XPathExpression licenseXPath = null;
    private static XPathExpression keywordXPath = null;

    private static SimpleDateFormat iso8601DateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    private String content;
    private Document document;

    public XmlParsingHelper(String content){
        this.content = content;
    }

    public XmlParsingHelper build() {
        this.initializeExpressions();
        this.parseDocument(this.content);
        this.content = null;
        return this;
    }

    private void initializeExpressions(){
        if(XmlParsingHelper.titleXPath == null){
            synchronized (XmlParsingHelper.lockMe){
                if(XmlParsingHelper.titleXPath == null) {
                    try {
                        XmlParsingHelper.descriptionXPath = XPathFactory.newInstance().newXPath().compile("/response/results/result/metadata/entity/result/description/text()");
                        XmlParsingHelper.pidXPath = XPathFactory.newInstance().newXPath().compile("/response/results/result/metadata/entity/result/pid");
                        XmlParsingHelper.sameAsXPath = XPathFactory.newInstance().newXPath().compile("/response/results/result/metadata/entity/result/children/instance/webresource/url/text()");
                        XmlParsingHelper.creatorXPath = XPathFactory.newInstance().newXPath().compile("/response/results/result/metadata/entity/result/creator");
                        XmlParsingHelper.dateCreatedXPath = XPathFactory.newInstance().newXPath().compile("/response/results/result/metadata/entity/result/dateofacceptance/text()");
                        XmlParsingHelper.citationXPath = XPathFactory.newInstance().newXPath().compile("/response/results/result/metadata/entity/extraInfo/citations/citation");
                        XmlParsingHelper.citationNameXPath = XPathFactory.newInstance().newXPath().compile("./rawText/text()");
                        XmlParsingHelper.citationIdXPath = XPathFactory.newInstance().newXPath().compile("./id");
                        XmlParsingHelper.licenseXPath = XPathFactory.newInstance().newXPath().compile("/response/results/result/metadata/entity/result/bestaccessright");
                        XmlParsingHelper.keywordXPath = XPathFactory.newInstance().newXPath().compile("/response/results/result/metadata/entity/result/subject[@classid = \"keyword\"]/text()");
                        //GOTCHA: we set title last as it is used to check if the initialization of xPaths is calready performed
                        XmlParsingHelper.titleXPath = XPathFactory.newInstance().newXPath().compile("/response/results/result/metadata/entity/result/title/text()");
                    }catch (Exception ex){
                        logger.error("problem initializing xpath expressions", ex);
                        throw new ApplicationException("problem initializing xpath expressions");
                    }
                }
            }
        }
    }

    private void parseDocument(String content) {
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document xmlDocument = builder.parse(new InputSource(new StringReader(content)));
            this.document = xmlDocument;
        } catch (Exception ex) {
            logger.error("problem parsing document", ex);
            throw new ApplicationException("problem parsing document");
        }
    }

    public List<String> getTitle() throws XPathExpressionException {
        return this.getStings(XmlParsingHelper.titleXPath);
    }

    public List<String> getKeyword() throws XPathExpressionException {
        return this.getStings(XmlParsingHelper.keywordXPath);
    }

    public List<String> getDescription() throws XPathExpressionException {
        return this.getStings(XmlParsingHelper.descriptionXPath);
    }

    public List<String> getSameAs() throws XPathExpressionException {
        return this.getStings(XmlParsingHelper.sameAsXPath);
    }

    //GOTCHA: multiple formatters are expected to be needed for parsing. right now it is a passthrough
    public List<Date> getDateCreated() throws XPathExpressionException {
        List<Date> dates = new ArrayList<Date>();
        List<String> dateStrings = this.getStings(XmlParsingHelper.dateCreatedXPath);
        for(String date : dateStrings){
            if(date == null || date.isEmpty()) continue;
            Date d = null;
            try {
                d = XmlParsingHelper.iso8601DateFormatter.parse(date);
            }catch(Exception ex){
                logger.error(String.format("problem parsing date %s", date), ex);
            }
            if(d != null) dates.add(d);
        }
        return dates;
    }

    public List<JsonldDocumentImpl.Identifier> getIdentifiers() throws XPathExpressionException {
        NodeList nodes = (NodeList) XmlParsingHelper.pidXPath.evaluate(this.document, XPathConstants.NODESET);

        List<JsonldDocumentImpl.Identifier> values = new ArrayList<JsonldDocumentImpl.Identifier>();
        for (int i = 0; i < nodes.getLength(); i += 1) {
            String className = this.getAttributeValue(nodes.item(i), "classname");
            if (className == null || className.isEmpty()) continue;
            NodeList children = nodes.item(i).getChildNodes();
            for (int q = 0; q < children.getLength(); q += 1) {
                String identifierValue = children.item(q).getNodeValue();
                if (identifierValue == null || identifierValue.isEmpty()) continue;
                values.add(new JsonldDocumentImpl.Identifier(className, identifierValue));
            }
        }
        return values;
    }

    public List<JsonldDocumentImpl.Citation> getCitations() throws XPathExpressionException {
        NodeList nodes = (NodeList) XmlParsingHelper.citationXPath.evaluate(this.document, XPathConstants.NODESET);

        List<JsonldDocumentImpl.Citation> values = new ArrayList<>();
        for (int i = 0; i < nodes.getLength(); i += 1) {
            JsonldDocumentImpl.Citation citation = new JsonldDocumentImpl.Citation();

            NodeList nameNodes = (NodeList) XmlParsingHelper.citationNameXPath.evaluate(nodes.item(i), XPathConstants.NODESET);
            citation.setTitle(this.getStings(nameNodes));

            List<JsonldDocumentImpl.Identifier> identifiers = new ArrayList<>();
            NodeList idNodes = (NodeList) XmlParsingHelper.citationIdXPath.evaluate(nodes.item(i), XPathConstants.NODESET);
            for (int q = 0; q < idNodes.getLength(); q += 1) {
                String typeName = this.getAttributeValue(idNodes.item(q), "type");
                if (typeName == null || typeName.isEmpty()) continue;

                String valueName = this.getAttributeValue(idNodes.item(q), "value");
                if (valueName == null || valueName.isEmpty()) continue;

                identifiers.add(new JsonldDocumentImpl.Identifier(typeName, valueName));
            }
            citation.setIdentifier(identifiers);

            values.add(citation);
        }
        return values;
    }

    public List<JsonldDocumentImpl.License> getLicenses() throws XPathExpressionException {
        NodeList nodes = (NodeList) XmlParsingHelper.licenseXPath.evaluate(this.document, XPathConstants.NODESET);

        List<JsonldDocumentImpl.License> values = new ArrayList<>();
        for (int i = 0; i < nodes.getLength(); i += 1) {
            JsonldDocumentImpl.License license = new JsonldDocumentImpl.License();

            String classId = this.getAttributeValue(nodes.item(i), "classid");
            if (classId == null || classId.isEmpty()) continue;

            String className = this.getAttributeValue(nodes.item(i), "classname");
            if (className == null || className.isEmpty()) continue;

            String schemaid = this.getAttributeValue(nodes.item(i), "schemeid");
            if (schemaid == null || schemaid.isEmpty()) continue;

            license.setTitle(Arrays.asList(className));
            license.setIdentifier(Arrays.asList(new JsonldDocumentImpl.Identifier(schemaid, classId)));

            values.add(license);
        }
        return values;
    }

    public List<JsonldDocumentImpl.Creator> getCreators() throws XPathExpressionException {
        NodeList nodes = (NodeList) XmlParsingHelper.creatorXPath.evaluate(this.document, XPathConstants.NODESET);

        List<JsonldDocumentImpl.Creator> values = new ArrayList<>();
        for (int i = 0; i < nodes.getLength(); i += 1) {
            String givenName = this.getAttributeValue(nodes.item(i), "name");
            String familyName = this.getAttributeValue(nodes.item(i), "surname");
            String name = null;

            NodeList children = nodes.item(i).getChildNodes();
            for (int q = 0; q < children.getLength(); q += 1) {
                name = children.item(q).getNodeValue();
                if (name != null && !name.isEmpty()) break;
            }
            if ((givenName == null || givenName.isEmpty()) &&
                    (familyName == null || familyName.isEmpty()) &&
                    (name == null || name.isEmpty())) continue;
            values.add(new JsonldDocumentImpl.Person(givenName, familyName, name));
        }
        return values;
    }

    private String getAttributeValue(Node node, String attribute){
        Node attributeNode = node.getAttributes().getNamedItem(attribute);
        if(attributeNode == null) return null;
        return attributeNode.getNodeValue();
    }

    private List<String> getStings(XPathExpression expression) throws XPathExpressionException {
        NodeList nodes = (NodeList) expression.evaluate(this.document, XPathConstants.NODESET);

        List<String> values = new ArrayList<String>();
        for (int i = 0; i < nodes.getLength(); i += 1) {
            String val = nodes.item(i).getNodeValue();
            if(val == null || val.isEmpty()) continue;
            values.add(val);
        }
        return values;
    }

    private List<String> getStings(NodeList nodes) {
        List<String> values = new ArrayList<String>();
        for (int i = 0; i < nodes.getLength(); i += 1) {
            String val = nodes.item(i).getNodeValue();
            if(val == null || val.isEmpty()) continue;
            values.add(val);
        }
        return values;
    }

}
