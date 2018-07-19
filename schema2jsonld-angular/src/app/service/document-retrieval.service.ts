import { Injectable } from "@angular/core";
import { HttpClient } from '@angular/common/http';
import { Observable } from "rxjs/Observable";
import { environment } from "../../environments/environment";

@Injectable()
export class DocumentRetrivalService {
	constructor(private http: HttpClient) { }

	getPublication(id: string): Observable<any> {
		const endpoint = environment.publicationRetrievalEndpoint.replace("{ID}", id);
		return this.http.get<any>(endpoint);
	}

}
