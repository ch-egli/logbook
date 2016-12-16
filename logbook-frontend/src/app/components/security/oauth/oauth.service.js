/**
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2015.
 *
 * ESTA WebJS: Definition des OAuth-Services.
 *
 * @author u220374 (Reto Lehmann)
 * @version: 0.0.2
 * @since 06.11.2015, 2015.
 */
class OAuthService {

    /*@ngInject*/
    constructor($http, config, $cookies, $log, $location, $window,
                $httpParamSerializer, messagesService,
                $translate, $q) {

        this.$http = $http;
        this.$httpParamSerializer = $httpParamSerializer;
        this.$location = $location;
        this.$window = $window;
        this.config = config;
        this.messagesService = messagesService;
        this.$log = $log;
        this.$cookies = $cookies;
        this.$translate = $translate;
        this.$q = $q;

        this.user = {
            name: '',
            authenticated: false,
            roles: '',
            firstname: '',
            lastname: '',
            email: '',
            phone: '',
            authheader: ''
        }
    }

    /**
     * Diese Methode handelt das Login, sowie den Callback nach dem Login.
     * - Leitet den Browser zum Login-Server um falls er noch nicht eingeloggt ist
     */
    login(benutzername, passwort) {
        let service = this;
        this.user.name = benutzername;

        if (!service.isLoggedIn()) {
            //service.$log.debug('Now redirecting to login-page of auth-server.');
            //service.$window.location.replace(service.config.authServerUrl + service.config.authLoginUrl + encodeURIComponent(service.config.authRedirectUrl));

            service.$log.debug('get a token from of auth-server.');
            service.$http.post(service.config.authServerUrl + 'oauth/token',
                service.$httpParamSerializer({
                    'grant_type': 'password',
                    'client_id': service.config.authClientId,
                    'username': benutzername,
                    'password': passwort
                }),
                {
                    headers: service._getAppAuthHeader()
                }).success(response => {
                    service._handleLoginResponse(response);
                }).error(err => {
                    service._handleErrorResponse(err, true);
                });


        } else {
            service.$location.path('/');
        }
    }

    /**
     * Loggt den User aus. Entfernt das lokale Auth-Cookie.
     */
    logout() {
        let service = this;

        if (!service.isLoggedIn()) {
            service.$location.path('/');
            return;
        }

        service.$cookies.remove('auth');
        service.$location.path('/home');
    }

    /**
     * Gibt zurueck ob ein Benutzer eingeloggt ist.
     * @returns {boolean} Eingeloggt?
     */
    isLoggedIn() {
        return !!this.getAuthData().authenticated;
    }

    isAdmin() {
        if (this.isLoggedIn()) {
            let roles = this.getAuthData().roles;
            return roles.indexOf('admin') > -1;
        } else {
            return false;
        }
    }

    isTrainer() {
        if (this.isLoggedIn()) {
            let roles = this.getAuthData().roles;
            return roles.indexOf('trainer') > -1;
        } else {
            return false;
        }
    }

    /**
     * Gibt den Benutzername eines Users zurueck falls eingelogt. Sonst ''.
     * @returns {*} Benutzername oder ''.
     */
    getUsername() {
        if (this.isLoggedIn()) {
            return this.getAuthData().name;
        } else {
            return '';
        }
    }

    /**
     * Gibt den Vornamen eines Users zurueck falls eingelogt. Sonst ''.
     * @returns {*} Vorname oder ''.
     */
    getFirstname() {
        if (this.isLoggedIn()) {
            return this.getAuthData().firstname;
        } else {
            return '';
        }
    }

    /**
     * Laden der Benutzerdaten...
     * @param response Die Antwort vom token Aufruf.
     * @private
     */
    _handleLoginResponse(response) {
        let service = this;

        if (response && response.access_token) {
            service.$log.debug('Got an access_token, now trying to get user-info from backend');

            service.$http.get(service.config.resourceServerUrl + 'v1/usrs/' + this.user.name, {
                headers: {'Authorization': 'Bearer ' + response.access_token,
                          'Content-Type': 'application/json'}
            })
                .success(userResponse => {
                    if (userResponse) {
                        service.$log.debug("_handleLoginResponse - userResponse.vorname: " + userResponse.vorname);
                        this.user.authenticated = true;
                        this.user.roles = userResponse.rollen;
                        this.user.firstname = userResponse.vorname;
                        this.user.lastname = userResponse.nachname;
                        this.user.email = userResponse.email;
                        this.user.phone = userResponse.telefon;
                        this.user.authheader = 'Bearer ' + response.access_token;

                        // add token as default header
                        service.$http.defaults.headers.common['Authorization'] = this.user.authheader;

                        service._setAuthData(this.user);
                        service.$log.debug("_handleLoginResponse - before setting location");
                        service.$location.path('/home');
                    }
                })
                .error(err => {
                    service._handleErrorResponse(err, true);
                });
        } else {
            service.$log.debug('Did not get a valid access token...');
        }
    }

    /**
     * Behandelt eine feherhafte Antwort.
     * @param error Der Fehler.
     * @param logError Soll der Fehler geloggt werden?
     * @private
     */
    _handleErrorResponse(error, logError) {
        let service = this;
        service.$log.debug('Enter _handleErrorResponse');

        if (logError) {
            service.$log.error('Error while logging in: ', error);
            service.messagesService.errorMessage(service.$translate.instant('LOGIN_ERROR'), true);
        }

        //service.$location.path('/');
    }

    /**
     * Schreibt die authDaten in ein Cookie.
     * @param authData Die authDaten.
     * @private
     */
    _setAuthData(authData) {
        this.$cookies.putObject('auth', {
            authData
        });
    }

    /**
     * Liest die authDaten aus dem Cookie.
     * @returns {*} AuthDaten oder {}.
     */
    getAuthData() {
        let cookie = this.$cookies.getObject('auth');
        if (cookie && cookie.authData) {
            return cookie.authData;
        } else {
            return {};
        }
    }

    /**
     * Gibt die notwendigen Header fuer Auth-Server-Aufrufe zurueck.
     * @returns {Authorization: string, Content-Type: String("application/x-www-form-urlencoded")}
     * @private
     */
    _getAppAuthHeader() {
        return {
            'Authorization': 'Basic ' + btoa(this.config.authClientId + ':' + this.config.authClientSecret),
            'Content-Type': 'application/x-www-form-urlencoded'
        };
    }

    /**
     * Helfermethode um den Query-String zu parsen.
     * @param name Den zu suchenden Namen.
     * @returns {string} Den gefundenen Wert oder ''.
     * @private
     */
    static _getParameterByName(name) {
        name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
        var regex = new RegExp('[\\?&]' + name + '=([^&#]*)'),
            results = regex.exec(location.search);
        return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
    }
}

export default OAuthService;