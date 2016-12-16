/**
 * Definition des NewStatus-Controllers
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

class NewStatusController {
    /*@ngInject*/
    constructor(statusService, oAuthService, config, $window, $log, $state) {
        this.statusService = statusService;
        this.$log = $log;
        this.$state = $state;
        this.$window = $window;

        this.oAuthService = oAuthService;
        this.config = config;

        this.title = 'Neuen Status erfassen...';

        this.username = null;
        let authData = this.oAuthService.getAuthData();
        if (authData !== null) {
            this.username = authData.name;
        }
        this.$log.debug('username (in constructor): ' + this.username);

        // init default values
        this.schlaf = this.config.workoutDefaultSchlaf;
        this.gefuehl = this.config.workoutDefaultGefuehl;

        this.statusData = this.statusService.getStatusByDate(this.username, this.datum);

        // angular-ui settings
        this.today = function() {
            this.datum = new Date();
        };
        this.today();

        this.clear = function() {
            this.datum = null;
        };

        this.dateOptions = {
            formatDay: 'dd',
            formatMonth: 'MM',
            formatYear: 'yyyy',
            startingDay: 1
        };

        this.open1 = function() {
            this.popup1.opened = true;
        };

        this.popup1 = {
            opened: false
        };
    }

    submitStatus() {
        this.$log.debug('username: ' + this.username);
        this.$log.debug('datum: ' + this.datum);
        this.$log.debug('schlaf: ' + this.schlaf);
        this.$log.debug('gefuehl: ' + this.gefuehl);
        this.$log.debug('bemerkung: ' + this.gefuhltext);

        // add status
        let dataObj = {
            "benutzername": this.username,
            "datum": this.datum,
            "schlaf": this.schlaf,
            "gefuehl": this.gefuehl,
            "bemerkung": this.bemerkung
        };
        this.statusService.addStatus(dataObj);

        this.schlaf = this.config.workoutDefaultSchlaf;
        this.gefuehl = this.config.workoutDefaultGefuehl;
        this.bemerkung = null;

        this.statusForm.$setPristine();

        // navigate home...
        this.$window.localStorage['activeTab'] = 1;
        this.$state.go('home');
    }

    cancelStatus() {
        // navigate home...
        this.$state.go('home');
    }

    selectImage1() {
        this.$log.debug('image 1 selected...');
        this.gefuehl = 1;
        this.$state.go('newstatus');
    }

    selectImage2() {
        this.$log.debug('image 2 selected...');
        this.gefuehl = 2;
        this.$state.go('newstatus');
    }

    selectImage3() {
        this.$log.debug('image 3 selected...');
        this.gefuehl = 3;
        this.$state.go('newstatus');
    }

    selectImage4() {
        this.$log.debug('image 4 selected...');
        this.gefuehl = 4;
        this.$state.go('newstatus');
    }

    getImage1() {
        if (this.gefuehl === 1) {
            return gefuehlImage1;
        } else {
        return gefuehlImage1g;
        }
    }

    getImage2() {
        if (this.gefuehl === 2) {
            return gefuehlImage2;
        } else {
        return gefuehlImage2g;
        }
    }

    getImage3() {
        if (this.gefuehl === 3) {
            return gefuehlImage3;
        } else {
        return gefuehlImage3g;
        }
    }

    getImage4() {
        if (this.gefuehl === 4) {
            return gefuehlImage4;
        } else {
        return gefuehlImage4g;
        }
    }

}

export default NewStatusController;