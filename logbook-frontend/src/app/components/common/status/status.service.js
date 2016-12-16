/**
 * Definition eines Rest-Services für Benutzer Stati
 *
 * @author Christian Egli
 * @version: 0.0.1
 * @since 20.06.2016
 */
import gefuehlImage1 from "../images/grinning.png"
import gefuehlImage2 from "../images/smirking.png"
import gefuehlImage3 from "../images/frowning.png"
import gefuehlImage4 from "../images/fearful.png"
import gefuehlImage1g from "../images/grinning-g.png"
import gefuehlImage2g from "../images/smirking-g.png"
import gefuehlImage3g from "../images/frowning-g.png"
import gefuehlImage4g from "../images/fearful-g.png"

class StatusService {
    /*@ngInject*/
    constructor(config, $resource, $http, $log, $cookies, $state, $filter) {
        this.$log = $log;
        this.config = config;
        this.$http = $http;
        this.$resource = $resource;
        this.$cookies = $cookies;
        this.$state = $state;
        this.$filter = $filter;
        this.authData = {};

        this.$log.debug('username (in StatusService constructor): ' + this.authData.name);
    }

    /**
     * Get all status depending of user role.
     */
    getAllStati(mPage, mSize, mFilter, nameFilter) {
        let service = this;
        this._setAuthorizationHeader();

        let userRoles = this.authData.roles;
        let statusDiscriminator = null;
        if (userRoles.indexOf('trainer') > -1) {
            if (nameFilter.benutzername) {
                statusDiscriminator = nameFilter.benutzername;
            } else {
                statusDiscriminator = 'all';
            }
        } else {
            statusDiscriminator = this.authData.name;
        }

        let statusApi = this.$resource(service.config.resourceServerUrl + 'v1/users/' + statusDiscriminator + '/status');
        let queryParams = {
            page:mPage,
            size:mSize
        };
        let status = statusApi.get(queryParams);
        return status;
    }


    getStatusByUser(username) {
        let service = this;
        this._setAuthorizationHeader();
        if (username) {
            let status = this.$resource(service.config.resourceServerUrl + 'v1/users/' + username + '/status/top/' + 7);
            return status.query();
        }
        return {};
    }

    /**
     * Liefert einen bestimmten Status von der REST-Resource zurueck
     * @param id Die zu suchende statusId
     * @returns {*} Den Status als Objekt
     */
    getStatusById(id) {
        let service = this;
        let statusData = {};
        if (id) {
            this._setAuthorizationHeader();
            let res = this.$http.get(service.config.resourceServerUrl + 'v1/users/all/status/' + id);
            res.success(function(data, status, headers, config) {
                if (data) {
                    statusData.datum = new Date(data.datum);
                    statusData.schlaf = data.schlaf;
                    statusData.gefuehl = '' + data.gefuehl;
                    statusData.image1 = (data.gefuehl === 1) ? gefuehlImage1 : gefuehlImage1g;
                    statusData.image2 = (data.gefuehl === 2) ? gefuehlImage2 : gefuehlImage2g;
                    statusData.image3 = (data.gefuehl === 3) ? gefuehlImage3 : gefuehlImage3g;
                    statusData.image4 = (data.gefuehl === 4) ? gefuehlImage4 : gefuehlImage4g;
                    statusData.bemerkung = data.bemerkung;
                }
                return statusData;
            });
            res.error(function(data, status, headers, config) {
                alert( "failure message: " + JSON.stringify({data: data}));
            });
        }
        return statusData;
    }

    getStatusByDate(username, date) {
        let service = this;
        let statusData = {};
        statusData.id = null;
        statusData.schlaf = this.config.workoutDefaultSchlaf;
        statusData.gefuehl = this.config.workoutDefaultGefuehl;
        statusData.image1 = gefuehlImage1g;
        statusData.image2 = gefuehlImage2g;
        statusData.image3 = gefuehlImage3g;
        statusData.image4 = gefuehlImage4g;

        if (date && username) {
            this._setAuthorizationHeader();
            let res = this.$http.get(service.config.resourceServerUrl + 'v1/users/' + username + '/status/datum/' + this.formatDate(date));
            res.success(function(data, status, headers, config) {
                if (data) {
                    statusData.id = data.id;
                    statusData.schlaf = data.schlaf;
                    statusData.gefuehl = '' + data.gefuehl;
                    statusData.image1 = (data.gefuehl === 1) ? gefuehlImage1 : gefuehlImage1g;
                    statusData.image2 = (data.gefuehl === 2) ? gefuehlImage2 : gefuehlImage2g;
                    statusData.image3 = (data.gefuehl === 3) ? gefuehlImage3 : gefuehlImage3g;
                    statusData.image4 = (data.gefuehl === 4) ? gefuehlImage4 : gefuehlImage4g;

                    //console.log('## id     : ' + data.id);
                    //console.log('## gefuehl: ' + data.gefuehl);
                    //console.log('## schlaf : ' + data.schlaf);
                }
                return statusData;
            });
            res.error(function(data, status, headers, config) {
                alert( "failure message: " + JSON.stringify({data: data}));
            });
        }
        return statusData;
    }

    formatDate(date) {
        let log = this.$log;
        let myFormat = 'yyyy-MM-dd';
        let formattedDate = this.$filter('date')(date, myFormat);
        log.debug('formattedDate: ' + formattedDate);
        return formattedDate;
    }

    addStatus(status) {
        let service = this;
        this._setAuthorizationHeader();
        let res = this.$http.post(service.config.resourceServerUrl + 'v1/users/' + status.benutzername + '/status', status);
        res.success(function(data, status, headers, config) {
            //console.log('added status with id: ' + data.id);
            service.$state.reload();
        });
        res.error(function(data, status, headers, config) {
            alert( "failure message: " + JSON.stringify({data: data}));
        });
    }

    editStatus(status) {
        let service = this;
        this._setAuthorizationHeader();
        let res = this.$http.put(service.config.resourceServerUrl + 'v1/users/' + status.benutzername + '/status/' + status.id, status);
        res.success(function(data, status, headers, config) {
            //console.log('updated status with id: ' + data.id);
            service.$state.reload();
        });
        res.error(function(data, status, headers, config) {
            alert( "PUT failure message: " + JSON.stringify({data: data}));
        });
    }

    deleteStatus(status) {
        let service = this;
        this._setAuthorizationHeader();
        let res = this.$http.delete(service.config.resourceServerUrl + 'v1/users/' + status.benutzername + '/status/' + status.id);
        res.success(function(data, status, headers, config) {
            //console.log('deleted status with id: ' + data.id);
            service.$state.reload();
        });
        res.error(function(data, status, headers, config) {
            alert( "DELETE failure message: " + JSON.stringify({data: data}));
        });
    }

    exportStatiToExcel(year, user) {
        let service = this;
        this._setAuthorizationHeader();

        if (!user) {
            user = service.authData.name;
        }
        let downloadUrl = service.config.resourceServerUrl + 'v1/user/' + user + '/excelresults/' + year;

        // with jQuery: $("body").append("<iframe src='" + downloadUrl + "' style='display: none;' ></iframe>");
        let iframe = document.createElement("iframe");
        iframe.setAttribute("src", downloadUrl);
        iframe.setAttribute("style", "display: none");
        document.body.appendChild(iframe);
    }

    _getAuthData() {
        let cookie = this.$cookies.getObject('auth');
        if (cookie && cookie.authData) {
            return cookie.authData;
        } else {
            return {};
        }
    }

    _setAuthorizationHeader() {
        this.authData = this._getAuthData();
        this.$http.defaults.headers.common['Authorization'] = this.authData.authheader;
    }
}

export default StatusService;