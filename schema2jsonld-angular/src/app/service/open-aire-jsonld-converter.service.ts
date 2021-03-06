import { Injectable } from "@angular/core";
import { JsonldDocument, Identifier, Person, License, Citation } from "../model/jsonld-document";
import * as _ from "lodash";
import { environment } from "../../environments/environment";

@Injectable()
export class OpenAireJsonldConverterService {
	constructor() { }

	convertPublication(id: string, result: any): JsonldDocument {
		const doc = new JsonldDocument();

		const endpoint = environment.publicationPortalEndpoint.replace("{ID}", id);

		doc.title = this.getTitle(result);
		doc.description = this.getDescription(result);
		doc.identifier = this.getIdentifier(result);
		doc.url = [endpoint];
		doc.sameAs = this.getSameAs(result);
		doc.creator = this.getCreator(result);
		doc.dateCreated = this.getDateCreated(result);
		doc.citation = this.getCitation(result);
		doc.license = this.getLicense(result);
		doc.keyword = this.getKeywords(result);

		return doc;
	}

	convertSoftware(id: string, result: any): JsonldDocument {
		const doc = new JsonldDocument();

		const endpoint = environment.softwarePortalEndpoint.replace("{ID}", id);

		doc.title = this.getTitle(result);
		doc.description = this.getDescription(result);
		doc.identifier = this.getIdentifier(result);
		doc.url = [endpoint];
		doc.sameAs = this.getSameAs(result);
		doc.creator = this.getCreator(result);
		doc.dateCreated = this.getDateCreated(result);
		doc.citation = this.getCitation(result);
		doc.license = this.getLicense(result);
		doc.keyword = this.getKeywords(result);

		return doc;
	}

	convertDataset(id: string, result: any): JsonldDocument {
		const doc = new JsonldDocument();

		const endpoint = environment.datasetPortalEndpoint.replace("{ID}", id);

		doc.title = this.getTitle(result);
		doc.description = this.getDescription(result);
		doc.identifier = this.getIdentifier(result);
		doc.version = this.getVersion(result);
		doc.url = [endpoint];
		doc.sameAs = this.getSameAs(result);
		doc.creator = this.getCreator(result);
		doc.dateCreated = this.getDateCreated(result);
		doc.citation = this.getCitation(result);
		doc.license = this.getLicense(result);
		doc.keyword = this.getKeywords(result);

		return doc;
	}

	convertProject(id: string, result: any): JsonldDocument {
		const doc = new JsonldDocument();

		const endpoint = environment.projectPortalEndpoint.replace("{ID}", id);

		doc.title = this.getProjectTitle(result);
		doc.identifier = this.getProjectIdentifier(result);
		doc.url = [endpoint];
		doc.keyword = this.getProjectKeywords(result);

		return doc;
	}

	private getTitle(result: any): String[] {
		const path = "result.metadata.oaf:entity.oaf:result.title.content";
		return this.getTitleInternal(path, result);
	}

	private getProjectTitle(result: any): String[] {
		const path = "result.metadata.oaf:entity.oaf:project.title";
		return this.getTitleInternal(path, result);
	}

	private getTitleInternal(path: string, result: any): String[] {
		const item = _.get(result, path, null);
		if (!item) return null;
		return [item as String];
	}

	private getDescription(result: any): String[] {
		const item = _.get(result, "result.metadata.oaf:entity.oaf:result.description", null);
		if (!item) return null;
		return [item as String];
	}

	private getVersion(result: any): String[] {
		const item = _.get(result, "result.metadata.oaf:entity.oaf:result.version", null);
		if (!item) return null;
		return [item as String];
	}

	private getDateCreated(result: any): String[] {
		const item = _.get(result, "result.metadata.oaf:entity.oaf:result.dateofacceptance", null);
		if (!item) return null;
		return [item as String];
	}

	private getLicense(result: any): License[] {
		const item = _.get(result, "result.metadata.oaf:entity.oaf:result.bestaccessright", null);
		if (!item) return null;
		if (!_.has(item, "classid")) return null;
		if (!_.has(item, "classname")) return null;
		if (!_.has(item, "schemeid")) return null;

		return [{
			title: [_.get(item, "classname")],
			identifier: [{
				id: _.get(item, "classid"),
				schema: _.get(item, "schemeid")
			}]
		}];
	}

	private getProjectIdentifier(result: any): Identifier[] {
		const path = "result.metadata.oaf:entity.oaf:project.pid";
		return this.getIdentifierInternal(path, result);
	}

	private getIdentifier(result: any): Identifier[] {
		const path = "result.metadata.oaf:entity.oaf:result.pid";
		return this.getIdentifierInternal(path, result);
	}

	private getIdentifierInternal(path: string, result: any): Identifier[] {
		const item = _.get(result, path, null);
		if (!item) return null;
		const array = new Array<Identifier>();
		if (Array.isArray(item)) {
			const itemArray = item as Array<any>;
			for (var i = 0; i < itemArray.length; i += 1) {
				const val = this.getSingleIdentifier(itemArray[i]);
				if (val) array.push(val);
			}
		}
		else {
			const val = this.getSingleIdentifier(item);
			if (val) array.push(val);
		}
		if (array.length == 0) return null;
		return array;
	}

	private getSingleIdentifier(item: any): Identifier {
		if (!_.has(item, "classname")) return null;
		if (!_.has(item, "content")) return null;
		return {
			schema: _.get(item, "classname"),
			id: _.get(item, "content")
		};
	}

	private getSameAs(result: any): String[] {
		var instances = _.get(result, "result.metadata.oaf:entity.oaf:result.children.instance", null);
		if (!instances) return null;
		if (!Array.isArray(instances)) {
			const tmpInstances = new Array();
			tmpInstances.push(instances);
			instances = tmpInstances;
		}

		const array = new Array<String>();

		const instanceArray = instances as Array<any>;
		for (var i = 0; i < instanceArray.length; i += 1) {
			const webresources = _.get(instanceArray[i], "webresource", null);
			if (!webresources) continue;
			if (Array.isArray(webresources)) {
				const webresourceArray = webresources as Array<any>;
				for (var q = 0; q < webresourceArray.length; q += 1) {
					const url = _.get(webresourceArray[q], "url", null);
					if (!url) continue;
					array.push(url as String);
				}
			}
			else {
				const url = _.get(webresources, "url", null);
				if (!url) continue;
				array.push(url as String);
			}
		}
		if (array.length == 0) return null;
		return array;
	}

	private getProjectKeywords(result: any): String[] {
		var subjects = _.get(result, "result.metadata.oaf:entity.oaf:project.subjects", null);
		if (!subjects) {
			subjects = new Array();
		}
		else {
			if (!Array.isArray(subjects)) {
				const tmpSubjects = new Array();
				tmpSubjects.push(subjects);
				subjects = tmpSubjects;
			}
		}

		const array = new Array<String>();

		const subjectArray = subjects as Array<any>;
		for (var i = 0; i < subjectArray.length; i += 1) {
			const classid = _.get(subjectArray[i], "classid", null);
			if (classid !== "keyword") continue;

			const sub = _.get(subjectArray[i], "content", null);
			if (!sub) continue;

			array.push(sub as String);
		}

		var keywords = _.get(result, "result.metadata.oaf:entity.oaf:project.keywords", null);
		if (!keywords) {
			keywords = new Array();
		}
		else {
			if (!Array.isArray(keywords)) {
				const tmpKeywords = new Array();
				tmpKeywords.push(keywords);
				keywords = tmpKeywords;
			}
		}

		const keywordArray = keywords as Array<any>;
		for (var i = 0; i < keywordArray.length; i += 1) {
			const sub = keywordArray[i];
			if (!sub) continue;
			array.push(sub);
		}

		if (array.length == 0) return null;
		return array;
	}

	private getKeywords(result: any): String[] {
		var subjects = _.get(result, "result.metadata.oaf:entity.oaf:result.subject", null);
		if (!subjects) return null;
		if (!Array.isArray(subjects)) {
			const tmpSubjects = new Array();
			tmpSubjects.push(subjects);
			subjects = tmpSubjects;
		}

		const array = new Array<String>();

		const subjectArray = subjects as Array<any>;
		for (var i = 0; i < subjectArray.length; i += 1) {
			const classid = _.get(subjectArray[i], "classid", null);
			if (classid !== "keyword") continue;

			const sub = _.get(subjectArray[i], "content", null);
			if (!sub) return null;

			array.push(sub as String);
		}
		if (array.length == 0) return null;
		return array;
	}

	private getCreator(result: any): Person[] {
		const item = _.get(result, "result.metadata.oaf:entity.oaf:result.creator", null);
		if (!item) return null;
		const array = new Array<Person>();
		if (Array.isArray(item)) {
			const itemArray = item as Array<any>;
			for (var i = 0; i < itemArray.length; i += 1) {
				const val = this.getSinglePerson(itemArray[i]);
				if (val) array.push(val);
			}
		}
		else {
			const val = this.getSinglePerson(item);
			if (val) array.push(val);
		}
		if (array.length == 0) return null;
		return array;
	}

	private getSinglePerson(item: any): Person {
		if (!_.has(item, "surname") && !_.has(item, "name") && !_.has(item, "content")) return null;
		return {
			familyName: _.get(item, "surname", null),
			givenName: _.get(item, "name", null),
			name: _.get(item, "content", null)
		};
	}

	private getCitation(result: any): Citation[] {
		const item = _.get(result, "result.metadata.oaf:entity.extraInfo.citations.citation", null);
		if (!item) return null;
		const array = new Array<Citation>();
		if (Array.isArray(item)) {
			const itemArray = item as Array<any>;
			for (var i = 0; i < itemArray.length; i += 1) {
				const val = this.getSingleCitation(itemArray[i]);
				if (val) array.push(val);
			}
		}
		else {
			const val = this.getSingleCitation(item);
			if (val) array.push(val);
		}
		if (array.length == 0) return null;
		return array;
	}

	private getSingleCitation(item: any): Citation {
		if (!_.has(item, "rawText")) return null;
		if (!_.has(item, "id")) return null;

		const array = new Array<Identifier>();

		const ids = _.get(item, "id", null);
		if (Array.isArray(ids)) {
			const idsArray = ids as Array<any>;

			for (var i = 0; i < idsArray.length; i += 1) {
				const type = _.get(idsArray[i], "type", null);
				const value = _.get(idsArray[i], "value", null);
				if (!type || !value) continue;
				array.push({
					id: value,
					schema: type
				});
			}
		}
		else {
			const type = _.get(ids, "type", null);
			const value = _.get(ids, "value", null);
			if (type && value) {
				array.push({
					id: value,
					schema: type
				});
			}
		}

		if (array.length == 0) return null;

		return {
			title: [_.get(item, "rawText")],
			identifier: array
		};
	}


}
