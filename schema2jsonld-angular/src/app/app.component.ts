import { Component } from '@angular/core';
import { DocumentRetrivalService } from './service/document-retrieval.service';
import { OpenAireJsonldConverterService } from './service/open-aire-jsonld-converter.service';
import { JsonldDocument } from './model/jsonld-document';
import { JsonldDocumentSerializerService } from './service/jsonld-document-serializer.service';

@Component({
	selector: 'app-root',
	templateUrl: './app.component.html',
	styleUrls: ['./app.component.css'],
	providers: [DocumentRetrivalService, OpenAireJsonldConverterService, JsonldDocumentSerializerService]
})
export class AppComponent {
	title = 'app';
	doc: String;

	constructor(
		private documentRetrieval: DocumentRetrivalService,
		private documentParser: OpenAireJsonldConverterService,
		private documentSerializer: JsonldDocumentSerializerService) { }

	public getPublication() {
		this.documentRetrieval.getPublication().subscribe(x => {
			const docOvject = this.documentParser.convertPublication(x);
			this.doc = this.documentSerializer.serialize(docOvject);
		});
	}
}
