# schema2jsonld

schema2jsonld is a tool for converting externally exposed metadata to schema.org(http://schema.org/) [json-ld](https://json-ld.org/) structured documents according to [EDMI](https://eoscpilot.eu/edmi-metadata-guidelines) (EOSC Datasets Minimum Information) metadata guidelines.

## Overview

The external metadata catalogues can be arbitrary, but need to be registered with the tool so that the respective profiles and transformation tools are generated. Currently, 3 such metadata catalogues have been implemented as a reference:
- Open AIRE
- EBI Metagenomics
- BlueBRIDGE

Each one of the registered metadata catalogues can expose internal / proprietary metadata schema that is tailored to the domain it operates on. The schema2jsonld tool utilizes the needed bridges to perform a crosswalk from the catalogues internal schema to an [EDMI](https://eoscpilot.eu/edmi-metadata-guidelines) compatible schema. The outcome of this crosswalk is then serialized as a [schema.org/Dataset](schema.org/Dataset) class (type) in json-ld syntax.

The purpose of this process is to demonstrate a simple and quick way to implement a solution which allowes metadata catalogues to expose a structured metadata schema that will facilitate findability and interoperability through a commonly defined, low barrier, research schema.

## schema.org & EDMI

The generated json-ld document conforms to the type defined under schema.org Dataset class (type) schema.org/Dataset.
The [EDMI](https://eoscpilot.eu/edmi-metadata-guidelines) (EOSC Datasets Minimum Information) metadata guidelines are a set of metadata properties and a metadata crosswalk (equivalence) across existing metadata models.

## Usage

### Web API

The project is a simple Spring Boot application. You will need maven (link) to build it and java (link) to run it. To try it, just clone / download the source code, open you command prompt and type
`$ cd schema2jsonld-api`
`$ mvn clean package`
`$ java -jar target/schema2jsonld-api-0.0.1-SNAPSHOT.jar`

Now you have your service running. Open up a browser / postman / curl and point it to perform a simple `GET` to `http://localhost:8080/json-ld/openaire/publication@dedup_wf_001::b8cea2465152e2ae541f56593604d2fc`

You will see the json-ld schema.org/Dataset markup for the publication exposed through OpenAIRE's portal `https://www.openaire.eu/search/publication?articleId=dedup_wf_001::b8cea2465152e2ae541f56593604d2fc`

### Angular App

There is also an angular app that displays tight coupling of an application that injects the json-ld document generation into its client side code. This is to demonstrate tighter coupling and provides a simple, proof of consept javascript implementation of the document parsing process.

The app is build with angular-cli so just run ng-serve, open up your browser, click on the button and see the generated json-ld
> the app is build using angular-cli version 1.7.4. If you are having problems building the app, please drop a line on the issues for assistance

## Profiles

To enable a wide range of backend metadata catalogue providers, profiles are utilized to identify the target endpoint that will provide the requested payload.
`http://localhost:8080/json-ld/<profile>/<key>`
In the url example above, the `<profile>` section indicates the profile identifier that will be used to serve the request. Depending on the profile requested, the `<key>` section will be transated entirely by the respective KeyResolver that is provided with the profile specific implementation.

### OpenAIRE
The OpenAIRE provider recognizes the following types of records maintained in the OpenAIRE catalogue:
- Publication
- Software
- Dataset
- Project

For each one of the above, the respective endpoint for the public API is utilized to retrieve the record's metadata eg. `http://api.openaire.eu/search/publications?openairePublicationID=dedup_wf_001::b8cea2465152e2ae541f56593604d2fc`. This will return a payload in the [oaf schema](https://www.openaire.eu/schema/1.0/doc/oaf-1.0.html). The response is parsed to generate a `JsonldDocument` and serialized to be added in the response

As an example, for the publication:
https://www.openaire.eu/search/publication?articleId=dedup_wf_001::b8cea2465152e2ae541f56593604d2fc
The generated json-ld document is retrieved with the request to:
`http://localhost:8080/json-ld/openaire/publication@dedup_wf_001::b8cea2465152e2ae541f56593604d2fc`
And it would be:
```json
{
    "@context": "http://schema.org",
    "@type": "Dataset",
    "name": "Human hair genealogies and stem cell latency",
    "description": "<p>Abstract</p> <p>Background</p> <p>Stem cells divide to reproduce themselves and produce differentiated progeny. A fundamental problem in human biology has been the inability to measure how often stem cells divide. Although it is impossible to observe every division directly, one method for counting divisions is to count replication errors; the greater the number of divisions, the greater the......</p> ",
    "identifier": [
        {
            "@type": "PropertyValue",
            "propertyID": "pmid",
            "value": "16457718"
        },
        {
            "@type": "PropertyValue",
            "propertyID": "pmc",
            "value": "PMC1386708"
        },
        {
            "@type": "PropertyValue",
            "propertyID": "doi",
            "value": "10.1186/1741-7007-4-2"
        }
    ],
    "url": "https://www.openaire.eu/search/publication?articleId=dedup_wf_001::b8cea2465152e2ae541f56593604d2fc",
    "sameAs": [
        "http://europepmc.org/articles/PMC1386708",
        "http://www.biomedcentral.com/1741-7007/4/2",
        "https://doaj.org/toc/1741-7007"
    ],
    "creator": [
        {
            "@type": "Person",
            "givenName": "Jung Yeon",
            "familyName": "Kim",
            "name": "Kim, Jung Yeon"
        },
        {
            "@type": "Person",
            "givenName": "Simon",
            "familyName": "Tavar",
            "name": "Tavar?, Simon"
        },
        {
            "@type": "Person",
            "givenName": "Darryl",
            "familyName": "Shibata",
            "name": "Shibata, Darryl"
        }
    ],
    "dateCreated": "2006-02-01",
    "citation": [
        {
            "@type": "CreativeWork",
            "name": "Bromham, L, Penny, D. The modern molecular clock. Nat Rev Genet. 2003; 4: 216-224",
            "identifier": [
                {
                    "@type": "PropertyValue",
                    "propertyID": "pmid",
                    "value": "12610526"
                },
                {
                    "@type": "PropertyValue",
                    "propertyID": "doi",
                    "value": "10.1038/nrg1020"
                }
            ]
        },
        {
            "@type": "CreativeWork",
            "name": "Issa, JP. CpG-island methylation in aging and cancer. Curr Top Microbiol Immunol. 2000; 249: 101-118",
            "identifier": {
                "@type": "PropertyValue",
                "propertyID": "pmid",
                "value": "10802941"
            }
        },
        {
            "@type": "CreativeWork",
            "name": "Yatabe, Y, Tavare, S, Shibata, D. Investigating stem cells in human colon by using methylation patterns. Proc Natl Acad Sci USA. 2001; 98: 10839-10844",
            "identifier": [
                {
                    "@type": "PropertyValue",
                    "propertyID": "openaire",
                    "value": "od_______908::7151567678600111ffc73986cea005f2"
                },
                {
                    "@type": "PropertyValue",
                    "propertyID": "pmid",
                    "value": "11517339"
                },
                {
                    "@type": "PropertyValue",
                    "propertyID": "doi",
                    "value": "10.1073/pnas.191225998"
                }
            ]
        }
        //more citations exist but removed for clarity
    ],
    "license": {
        "@type": "CreativeWork",
        "name": "Open Access",
        "identifier": {
            "@type": "PropertyValue",
            "propertyID": "dnet:access_modes",
            "value": "OPEN"
        }
    },
    "keywords": "Research Article, Biology (General), QH301-705.5"
}
```

### EBI Metagenomics
The EBI Metagenomics provider recognizes the following types of records meintained in the EBI Metagenomics catalogue:
- Study
- Sample

For each one of the above, the respective endpoint for the public API is utilized to retrieve the record's metadata eg. `https://www.ebi.ac.uk/metagenomics/api/latest/studies/PRJEB23732?format=application%2Fjson`. This will return a payload as documented in the [API docs](https://www.ebi.ac.uk/metagenomics/api/docs/#/studies/studies_read). The response is parsed to generate a `JsonldDocument` and serialized to be added in the response

As an example, for the record:
https://www.ebi.ac.uk/ena/data/view/PRJEB23732
The generated json-ld document is retrieved with the request to:
`http://localhost:8080/json-ld/ebi-metagenomics/study@PRJEB23732`
And it would be:
```json
{
    "@context": "http://schema.org",
    "@type": "Dataset",
    "name": "Antarctic Snow Algae 2014/15 16S and 18S Illumina Seq of snow algae communities around Ryder Bay, Antarctica",
    "description": "Antarctic Snow Algae 2014/15 16S and 18S Illumina Seq of snow algae communities around Ryder Bay, Antarctica. DNA extracted from green phase dominant and red phase dominant blooms on the snow surface. Field samples collected by Matt Davey (Univeristy of Cambridge, Department of Plant Sciences).",
    "identifier": {
        "@type": "PropertyValue",
        "propertyID": "identifiers.org",
        "value": "http://identifiers.org/ena/PRJEB23732"
    },
    "url": "https://www.ebi.ac.uk/ena/data/view/PRJEB23732",
    "sameAs": "https://www.ebi.ac.uk/metagenomics/api/v1/studies/ERP105504?format=application%2Fjson",
    "creator": {
        "@type": "Organization",
        "name": "UNIVERSITY OF CAMBRDGE"
    },
    "dateModified": "2018-03-08"
}
```

### BlueBRIDGE
The BlueBRIDGE probider recognizes datasets. A user may freely browse through the Reasearch Infrastructure exposed datasets at https://bluebridge.d4science.org/catalogue
The tool will utilize the public API exposed by the RI as documented in the [catalogue API docs](https://wiki.gcube-system.org/gcube/Catalogue_restful_service#Catalogue_Web_Service). 
Access is open through the portal but to utilize the API an access token is required and needs to be provided in the tool's configuration in `application.yml` file.
The endpoint to retrieve the respective metadata would be: `http://catalogue-ws.d4science.org/catalogue-ws/rest/api/items/show?id=furgaleus-macki3&gcube-token=<token>`

As an example, for the record:
https://ckan-bb1.d4science.org/dataset/furgaleus-macki3
The generated json-ld document is retrieved with the request to:
`http://localhost:8080/json-ld/bluebridge/dataset@furgaleus-macki3`
And it would be:
```json
{
    "@context": "http://schema.org",
    "@type": "Dataset",
    "name": "Furgaleus macki",
    "description": "This Furgaleus macki Species Distribution Map has been generated with the AquaMaps methodology by exploiting the technology and the computational resources provided by iMarine. In particular, this map has been produced using the HSPEC Native Range dataset, generated using AquaMaps NativeRange algorithm.",
    "identifier": {
        "@type": "PropertyValue",
        "propertyID": "Item URL",
        "value": "http://data.d4science.org/ctlg/d4science.research-infrastructures.eu/furgaleus-macki3"
    },
    "url": "http://data.d4science.org/ctlg/d4science.research-infrastructures.eu/furgaleus-macki3",
    "creator": {
        "@type": "Organization",
        "name": "iMarine Consortium,iMarine.eu"
    },
    "dateCreated": "2018-04-11",
    "dateModified": "2018-04-11",
    "license": {
        "@type": "CreativeWork",
        "name": "Creative Commons Attribution Share-Alike 4.0",
        "identifier": {
            "@type": "PropertyValue",
            "propertyID": "CC-BY-SA-4.0",
            "value": "https://creativecommons.org/licenses/by-sa/4.0/"
        }
    },
    "keywords": "AquaMaps, Ecological niche modelling, Furgaleus macki, SpeciesDistribution, Whiskery shark, iMarine"
}
```
