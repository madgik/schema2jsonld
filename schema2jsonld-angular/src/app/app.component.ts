import { Component } from '@angular/core';
import { DocumentRetrivalService } from './service/document-retrieval.service';
import { OpenAireJsonldConverterService } from './service/open-aire-jsonld-converter.service';
import { JsonldDocument } from './model/jsonld-document';

@Component({
	selector: 'app-root',
	templateUrl: './app.component.html',
	styleUrls: ['./app.component.css'],
	providers: [DocumentRetrivalService, OpenAireJsonldConverterService]
})
export class AppComponent {
	title = 'app';
	doc: JsonldDocument;

	constructor(
		private documentRetrieval: DocumentRetrivalService,
		private documentParser: OpenAireJsonldConverterService) { }

	public getPublication() {
		this.documentRetrieval.getPublication().subscribe(x => {
			this.doc = this.documentParser.convertPublication(x);
			console.log(this.doc);
		});
	}
}
