/**
 * Definition eines Rest-Services
 *
 * @author Christian Egli
 * @version: 0.0.1
 * @since 20.12.2016
 */
class BenutzerService {
    /*@ngInject*/
    constructor(config, $resource, $http, $log) {
        this.$log = $log;
        this.config = config;
        this.$http = $http;
        this.$resource = $resource;
    }

    /**
     * Get all athlete's names and assemble array to be used e.g. in dropdown.
     */
    getAthletes() {
        let service = this;
        let athletes = [];

        let res = this.$http.get(service.config.resourceServerUrl + 'v1/athletes');
        res.success(function(data, status, headers, config) {
            if (data) {
                for (let i=0; i < data.length; i++) {
                    let newElement = {};
                    newElement['id'] = data[i];
                    newElement['name'] = data[i];
                    newElement['title'] = data[i];
                    athletes.push(newElement);
                }
            }
            return athletes;
        });
        res.error(function(data, status, headers, config) {
            alert( "failed to get usernames from database: " + JSON.stringify({data: data}));
        });

        return athletes;
    }

}

export default BenutzerService;