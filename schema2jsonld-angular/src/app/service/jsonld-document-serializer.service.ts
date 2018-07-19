import { Injectable } from "@angular/core";
import { JsonldDocument, Identifier, Person, License, Citation } from "../model/jsonld-document";
import * as _ from "lodash";

@Injectable()
export class JsonldDocumentSerializerService {
	constructor() { }

	serialize(doc: JsonldDocument): String {
		const buffer = {};
		buffer["@context"] = "http://schema.org";
		buffer["@type"] = "Dataset";
		if (doc.title && doc.title.length == 1) {
			buffer["name"] = doc.title[0];
		}
		else if (doc.title && doc.title.length > 1) {
			buffer["name"] = doc.title;
		}

		if (doc.description && doc.description.length == 1) {
			buffer["description"] = doc.description[0];
		}
		else if (doc.description && doc.description.length > 1) {
			buffer["description"] = doc.description;
		}

		if (doc.version && doc.version.length == 1) {
			buffer["version"] = doc.version[0];
		}
		else if (doc.version && doc.version.length > 1) {
			buffer["version"] = doc.version;
		}

		if (doc.identifier && doc.identifier.length == 1) {
			buffer["identifier"] = this.buildIdentifier(doc.identifier[0]);
		}
		else if (doc.identifier && doc.identifier.length > 1) {
			const array = new Array<any>();
			for (var i = 0; i < doc.identifier.length; i += 1) {
				array.push(this.buildIdentifier(doc.identifier[i]));
			}
			buffer["identifier"] = array;
		}

		if (doc.url && doc.url.length == 1) {
			buffer["url"] = doc.url[0];
		}
		else if (doc.url && doc.url.length > 1) {
			buffer["url"] = doc.url;
		}

		if (doc.sameAs && doc.sameAs.length == 1) {
			buffer["sameAs"] = doc.sameAs[0];
		}
		else if (doc.sameAs && doc.sameAs.length > 1) {
			buffer["sameAs"] = doc.sameAs;
		}

		if (doc.creator && doc.creator.length == 1) {
			buffer["creator"] = this.buildCreator(doc.creator[0]);
		}
		else if (doc.creator && doc.creator.length > 1) {
			const array = new Array<any>();
			for (var i = 0; i < doc.creator.length; i += 1) {
				array.push(this.buildCreator(doc.creator[i]));
			}
			buffer["creator"] = array;
		}

		if (doc.dateCreated && doc.dateCreated.length == 1) {
			buffer["dateCreated"] = doc.dateCreated[0];
		}
		else if (doc.dateCreated && doc.dateCreated.length > 1) {
			buffer["dateCreated"] = doc.dateCreated;
		}

		if (doc.dateModified && doc.dateModified.length == 1) {
			buffer["dateModified"] = doc.dateModified[0];
		}
		else if (doc.dateModified && doc.dateModified.length > 1) {
			buffer["dateModified"] = doc.dateModified;
		}

		if (doc.license && doc.license.length == 1) {
			buffer["license"] = this.buildLicense(doc.license[0]);
		}
		else if (doc.license && doc.license.length > 1) {
			const array = new Array<any>();
			for (var i = 0; i < doc.license.length; i += 1) {
				array.push(this.buildLicense(doc.license[i]));
			}
			buffer["license"] = array;
		}

		if (doc.keyword && doc.keyword.length == 1) {
			buffer["keywords"] = doc.keyword[0];
		}
		else if (doc.keyword && doc.keyword.length > 1) {
			buffer["keywords"] = _.join(doc.keyword, ", ");
		}

		if (doc.citation && doc.citation.length == 1) {
			buffer["citation"] = this.buildCitation(doc.citation[0]);
		}
		else if (doc.citation && doc.citation.length > 1) {
			const array = new Array<any>();
			for (var i = 0; i < doc.citation.length; i += 1) {
				array.push(this.buildCitation(doc.citation[i]));
			}
			buffer["citation"] = array;
		}

		return JSON.stringify(buffer, (key, value) => {
			if (value !== null) return value
		}, 4);
	}

	buildIdentifier(item: Identifier): any {
		return {
			"@type": "PropertyValue",
			"propertyID": item.schema,
			"value": item.id
		};
	}

	buildCreator(item: Person): any {
		return {
			"@type": "Person",
			"givenName": item.givenName,
			"familyName": item.familyName,
			"name": item.name
		};
	}

	buildLicense(item: License): any {
		const licenseBuffer = {
			"@type": "CreativeWork"
		};

		if (item.title && item.title.length == 1) {
			licenseBuffer["name"] = item.title[0];
		}
		else if (item.title && item.title.length > 1) {
			licenseBuffer["name"] = item.title;
		}

		if (item.identifier && item.identifier.length == 1) {
			licenseBuffer["identifier"] = this.buildIdentifier(item.identifier[0]);
		}
		else if (item.identifier && item.identifier.length > 1) {
			const array = new Array<any>();
			for (var i = 0; i < item.identifier.length; i += 1) {
				array.push(this.buildIdentifier(item.identifier[i]));
			}
			licenseBuffer["identifier"] = array;
		}

		return licenseBuffer;
	}

	buildCitation(item: Citation): any {
		const citationBuffer = {
			"@type": "CreativeWork"
		};

		if (item.title && item.title.length == 1) {
			citationBuffer["name"] = item.title[0];
		}
		else if (item.title && item.title.length > 1) {
			citationBuffer["name"] = item.title;
		}

		if (item.identifier && item.identifier.length == 1) {
			citationBuffer["identifier"] = this.buildIdentifier(item.identifier[0]);
		}
		else if (item.identifier && item.identifier.length > 1) {
			const array = new Array<any>();
			for (var i = 0; i < item.identifier.length; i += 1) {
				array.push(this.buildIdentifier(item.identifier[i]));
			}
			citationBuffer["identifier"] = array;
		}

		return citationBuffer;
	}

}
