import { Injectable } from "@angular/core";
import { HttpClient } from '@angular/common/http';
import { Observable } from "rxjs/Observable";

@Injectable()
export class DocumentRetrivalService {
	constructor(private http: HttpClient) { }

	private publicationEndpoint = 'https://beta.services.openaire.eu/search/v2/api/publications/dedup_wf_001::577a25a49d82be67476edc87f3a503a2?format=json';

	getPublication(): Observable<any> {
		return this.http.get<any>(this.publicationEndpoint);
	}

}
