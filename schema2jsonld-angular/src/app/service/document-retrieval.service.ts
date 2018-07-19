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

	getSoftware(id: string): Observable<any> {
		const endpoint = environment.softwareRetrievalEndpoint.replace("{ID}", id);
		return this.http.get<any>(endpoint);
	}

	getDataset(id: string): Observable<any> {
		const endpoint = environment.datasetRetrievalEndpoint.replace("{ID}", id);
		return this.http.get<any>(endpoint);
	}

	getProject(id: string): Observable<any> {
		const endpoint = environment.projectRetrievalEndpoint.replace("{ID}", id);
		return this.http.get<any>(endpoint);
	}

}
