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
		this.documentRetrieval.getPublication('dedup_wf_001::b8cea2465152e2ae541f56593604d2fc').subscribe(x => {
			const docOvject = this.documentParser.convertPublication('dedup_wf_001::b8cea2465152e2ae541f56593604d2fc', x);
			this.doc = this.documentSerializer.serialize(docOvject);
		});
	}

	public getSoftware() {
		this.documentRetrieval.getSoftware('____EGIAppDB::8065d22be3ec33088913b9b97cf155ed').subscribe(x => {
			const docOvject = this.documentParser.convertSoftware('____EGIAppDB::8065d22be3ec33088913b9b97cf155ed', x);
			this.doc = this.documentSerializer.serialize(docOvject);
		});
	}

	public getDataset() {
		this.documentRetrieval.getDataset('datacite____::6a2d98e203ece74516a94a8abf280c9f').subscribe(x => {
			const docOvject = this.documentParser.convertDataset('datacite____::6a2d98e203ece74516a94a8abf280c9f', x);
			this.doc = this.documentSerializer.serialize(docOvject);
		});
	}

	public getProject() {
		this.documentRetrieval.getProject('nih_________::bc9b6366557df17ae199d49d30507ce0').subscribe(x => {
			const docOvject = this.documentParser.convertProject('nih_________::bc9b6366557df17ae199d49d30507ce0', x);
			this.doc = this.documentSerializer.serialize(docOvject);
		});
	}
}
