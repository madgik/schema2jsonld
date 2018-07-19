// The file contents for the current environment will overwrite these during build.
// The build system defaults to the dev environment which uses `environment.ts`, but if you do
// `ng build --env=prod` then `environment.prod.ts` will be used instead.
// The list of which env maps to which file can be found in `.angular-cli.json`.

export const environment = {
	production: false,
	publicationRetrievalEndpoint: 'https://beta.services.openaire.eu/search/v2/api/publications/{ID}?format=json',
	publicationPortalEndpoint: 'https://beta.explore.openaire.eu/search/publication?articleId={ID}',
	softwareRetrievalEndpoint: 'https://beta.services.openaire.eu/search/v2/api/software/{ID}?format=json',
	softwarePortalEndpoint: 'https://beta.explore.openaire.eu/search/software?softwareId={ID}',
	datasetRetrievalEndpoint: 'https://beta.services.openaire.eu/search/v2/api/datasets/{ID}?format=json',
	datasetPortalEndpoint: 'https://beta.explore.openaire.eu/search/dataset?datasetId={ID}',
	projectRetrievalEndpoint: 'https://beta.services.openaire.eu/search/v2/api/projects/{ID}?format=json',
	projectPortalEndpoint: 'https://beta.explore.openaire.eu/search/project?projectId={ID}'
};
