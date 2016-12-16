/**
 * Definition des Login-Controllers
 *
 * @author Christian Egli
 * @version: 0.0.1
 * @since 28.01.2016
 */
class LoginController {

    /*@ngInject*/
    constructor(oAuthService, $log, $window) {
        this.oAuthService = oAuthService;
        this.$log = $log;
        this.$window = $window;

        this.title = 'Logbook for Climbing Workouts';
        this.welcomeMessage = 'Herzlich Willkommen';

        this.rememberMe;

        // restore login values if user have previously logged in
        let storedBenutzername = this.$window.localStorage['bn'];
        let storedRememberMe = this.$window.localStorage['rememberMe'];
        if (storedRememberMe && storedBenutzername) {
            this.rememberMe = true
            this.benutzername = storedBenutzername;
            this.passwort = this.$window.localStorage['ps'];
        } else {
            this.benutzername = "";
            this.passwort = "";
            this.rememberMe = false;
        }
    }

    submitLogin() {
        this.$log.debug('### submitted, benutzername: ' + this.benutzername);

        // save login in localStorage for next access
        if (this.rememberMe === true) {
            this.$window.localStorage['rememberMe'] = true;
            this.$window.localStorage['bn'] = this.benutzername;
            this.$window.localStorage['ps'] = this.passwort;
        } else {
            this.$window.localStorage['rememberMe'] = false;
            this.$window.localStorage['bn'] = '';
            this.$window.localStorage['ps'] = '';
        }

        this.oAuthService.login(this.benutzername, this.passwort);

        this.benutzername = "";
        this.passwort = "";
    }

}

export default LoginController;