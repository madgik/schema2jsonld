export class JsonldDocument {
	title: String[];
	description: String[];
	identifier: Identifier[];
	url: String[];
	sameAs: String[];
	version: String[];
	creator: Person[];
	dateCreated: String[];
	dateModified: String[];
	citation: Citation[];
	license: License[];
	keyword: String[];
}

export interface Identifier {
	schema?: string;
	id?: string;
}

export interface Person {
	givenName: string;
	familyName: string;
	name: string;
}

export class Citation {
	title: string[];
	identifier: Identifier[];
}

export interface License {
	title: string[];
	identifier: Identifier[];
}
