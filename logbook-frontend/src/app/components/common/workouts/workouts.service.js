/**
 * Definition eines Rest-Services
 *
 * @author Christian Egli
 * @version: 0.0.1
 * @since 28.01.2016
 */
class WorkoutsService {
    /*@ngInject*/
    constructor(config, $resource, $http, $log, $cookies, $state, FileSaver, Blob) {
        this.$log = $log;
        this.config = config;
        this.$http = $http;
        this.$resource = $resource;
        this.$cookies = $cookies;
        this.$state = $state;
        this.FileSaver = FileSaver;

        this.authData = {};

        this.$log.debug('username (in WorkoutsService constructor): ' + this.authData.name);
    }

    /**
     * Get all workouts depending of user role.
     */
    getAllWorkouts(mPage, mSize, mFilter, nameFilter) {
        let service = this;
        this._setAuthorizationHeader();

        let userRoles = this.authData.roles;
        let woDiscriminator = null;
        if (userRoles.indexOf('trainer') > -1) {
            if (nameFilter.benutzername) {
                woDiscriminator = nameFilter.benutzername;
            } else {
                woDiscriminator = 'all';
            }
        } else if ((mFilter === false) && (userRoles.indexOf('egliSisters') > -1)) {
            woDiscriminator = 'groupEgliSisters';
        } else {
            woDiscriminator = this.authData.name;
        }

        let workoutsApi = this.$resource(service.config.resourceServerUrl + 'v1/users/' + woDiscriminator + '/workouts');
        let queryParams = {
            page:mPage,
            size:mSize
        };
        let workouts = workoutsApi.get(queryParams);
        return workouts;
    }


    getWorkoutsByUser(username) {
        let service = this;
        this._setAuthorizationHeader();
        if (username) {
            let workouts = this.$resource(service.config.resourceServerUrl + 'v1/users/' + username + '/workouts/top/' + 8);
            return workouts.query();
        }
        return {};
    }

    getWorkoutsByUserAndYear1(username, year) {
        return [1,2,3,4];
    }

    getWorkoutsByUserAndYear3(username, year) {
        let service = this;
        let woData;

        this._setAuthorizationHeader();
        return this.$http.get(service.config.resourceServerUrl + 'v1/users/' + username + '/workouts/year/' + year);
    }

    getWorkoutsByUserAndYear2(username, year) {
        let service = this;
        let woData;
        let test1 = [2,4,6,8];
        if (username) {
            this._setAuthorizationHeader();
            let res = this.$http.get(service.config.resourceServerUrl + 'v1/users/' + username + '/workouts/year/' + year);
            res.success(function(data, status, headers, config) {
                //console.log('got data: ' + data.length);
                if (data.length > 0) {
                    woData = data.map(wo => wo.belastung);
                }
                //console.log('data: ' + woData);
                return woData;
            });
            res.error(function(data, status, headers, config) {
                alert( "failure message: " + JSON.stringify({data: data}));
            });
        }
        //return {};
    }

    getWorkoutsByUserAndYear(username, year) {
        let service = this;
        let workouts = [];

        let res = this.$http.get(service.config.resourceServerUrl + 'v1/users/' + username + '/workouts/year/' + year);
        res.success(function(data, status, headers, config) {
            if (data.length > 0) {
                for (let i=0; i < data.length; i++) {
                    workouts.push(data[i].belastung);
                }
            }
            return workouts;
        });
        res.error(function(data, status, headers, config) {
            alert( "failed to get usernames from database: " + JSON.stringify({data: data}));
        });

        return workouts;
    }


    // /users/{benutzername}/workouts/year/{year}

    /**
     * Liefert einen bestimmten Workout von der REST-Resource zurueck
     * @param id Die zu suchende workoutId
     * @returns {*} Den Workout als Objekt
     */
    getWorkoutById(id) {
        let service = this;
        let woData = {};
        if (id) {
            this._setAuthorizationHeader();
            let res = this.$http.get(service.config.resourceServerUrl + 'v1/users/all/workouts/' + id);
            res.success(function(data, status, headers, config) {
                //console.log('got data: ' + status);
                if (data) {
                    woData.datum = new Date(data.datum);
                    if (service.config.workoutLocations.indexOf(data.ort) > -1) {
                        woData.ort1 = data.ort;
                        woData.ort2 = null;
                    } else {
                        woData.ort1 = service.config.workoutLocations[service.config.workoutLocations.length - 1];
                        woData.ort2 = data.ort;
                    }
                    woData.ort = data.ort;
                    woData.schlaf = data.schlaf;
                    woData.lead = data.lead ? true : false;
                    woData.bouldern = data.bouldern ? true : false;
                    woData.kraftraum = data.kraftraum ? true : false;
                    woData.dehnen = data.dehnen ? true : false;
                    woData.campus = data.campus ? true : false;
                    woData.mentaltraining = data.mentaltraining ? true : false;
                    woData.jogging = data.jogging ? true : false;
                    woData.geraete = data.geraete ? true : false;
                    woData.belastung = '' + data.belastung;
                    woData.zuege12 = data.zuege12;
                    woData.zuege23 = data.zuege23;
                    woData.zuege34 = data.zuege34;
                    woData.trainingszeit = data.trainingszeit;
                    woData.gefuehl = '' + data.gefuehl;
                    woData.wettkampf = data.wettkampf;
                    woData.sonstiges = data.sonstiges;
                }
                return woData;
            });
            res.error(function(data, status, headers, config) {
                alert( "failure message: " + JSON.stringify({data: data}));
            });
        }
        return woData;
    }

    addWorkout(workout) {
        let service = this;
        this._setAuthorizationHeader();
        let res = this.$http.post(service.config.resourceServerUrl + 'v1/users/' + workout.benutzername + '/workouts', workout);
        res.success(function(data, status, headers, config) {
            //console.log('added workout with id: ' + data.id);
            service.$state.reload();
        });
        res.error(function(data, status, headers, config) {
            alert( "failure message: " + JSON.stringify({data: data}));
        });
    }

    editWorkout(workout) {
        let service = this;
        this._setAuthorizationHeader();
        let res = this.$http.put(service.config.resourceServerUrl + 'v1/users/' + workout.benutzername + '/workouts/' + workout.id, workout);
        res.success(function(data, status, headers, config) {
            //console.log('updated workout with id: ' + data.id);
            service.$state.reload();
        });
        res.error(function(data, status, headers, config) {
            alert( "PUT failure message: " + JSON.stringify({data: data}));
        });
    }

    deleteWorkout(workout) {
        let service = this;
        this._setAuthorizationHeader();
        let res = this.$http.delete(service.config.resourceServerUrl + 'v1/users/' + workout.benutzername + '/workouts/' + workout.id);
        res.success(function(data, status, headers, config) {
            //console.log('deleted workout with id: ' + data.id);
            service.$state.reload();
        });
        res.error(function(data, status, headers, config) {
            alert( "DELETE failure message: " + JSON.stringify({data: data}));
        });
    }

    exportWorkoutsToExcel(year, user) {
        let service = this;
        this._setAuthorizationHeader();

        if (!user) {
            user = service.authData.name;
        }
        let downloadUrl = service.config.resourceServerUrl + 'v1/user/' + user + '/excelresults/' + year + '?requester=' + service.authData.name;

/*
        // with jQuery: $("body").append("<iframe src='" + downloadUrl + "' style='display: none;' ></iframe>");
        let iframe = document.createElement("iframe");
        iframe.setAttribute("src", downloadUrl);
        iframe.setAttribute("style", "display: none");
        document.body.appendChild(iframe);
*/

        // Version mit Filesaver: funktioniert in Chrome und Opera, Firefox nur vor der Webpack Optimierung
        this.$http({
            url: downloadUrl,
            method: "GET",
            responseType: "blob"
        }).then(function successCallback(response) {
            let filename = 'workouts-' + year + '-' + user + '.xlsx';
            service.FileSaver.saveAs(response.data, filename, {type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"});
        }, function errorCallback(response) {
            alert( "failed to get Excel file from server: " + JSON.stringify({data: response.data}));
        });
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

export default WorkoutsService;