/**
 * Definition des EditStatus-Controllers
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

class EditStatusController {
    /*@ngInject*/
    constructor(statusService, oAuthService, config, $window, $log, $state, $stateParams) {
        this.statusService = statusService;
        this.$log = $log;
        this.$state = $state;
        this.$window = $window;

        this.oAuthService = oAuthService;
        this.config = config;

        this.title = 'Status bearbeiten...';
        this.id = $stateParams.id;
        this.$log.debug('status-id: ' + this.id);

        this.username = null;
        let authData = this.oAuthService.getAuthData();
        if (authData !== null) {
            this.username = authData.name;
        }
        this.$log.debug('username (in constructor): ' + this.username);

        this.statusData = {};
        this.resetImages();
        this.statusData = statusService.getStatusById(this.id);
        this.statusData.id = this.id;
        this.statusData.username = this.username;

        // angular-ui settings
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
        this.$log.debug('id: ' + this.statusData.id);
        this.$log.debug('benutzername: ' + this.statusData.username);
        this.$log.debug('datum: ' + this.statusData.datum);
        this.$log.debug('schlaf: ' + this.statusData.schlaf);
        this.$log.debug('gefuehl: ' + this.statusData.gefuehl);
        this.$log.debug('bemerkung: ' + this.statusData.bemerkung);

        // add status
        let dataObj = {
            "id": this.statusData.id,
            "benutzername": this.statusData.username,
            "datum": this.statusData.datum,
            "schlaf": this.statusData.schlaf,
            "gefuehl": this.statusData.gefuehl,
            "bemerkung": this.statusData.bemerkung
        };
        this.statusService.editStatus(dataObj);

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
        this.statusData.gefuehl = 1;
        this.resetImages();
        this.statusData.image1 = gefuehlImage1;
    }

    selectImage2() {
        this.$log.debug('image 2 selected...');
        this.statusData.gefuehl = 2;
        this.resetImages();
        this.statusData.image2 = gefuehlImage2;
    }

    selectImage3() {
        this.$log.debug('image 3 selected...');
        this.statusData.gefuehl = 3;
        this.resetImages();
        this.statusData.image3 = gefuehlImage3;
    }

    selectImage4() {
        this.$log.debug('image 4 selected...');
        this.statusData.gefuehl = 4;
        this.resetImages();
        this.statusData.image4 = gefuehlImage4;
    }

    resetImages() {
        this.statusData.image1 = gefuehlImage1g;
        this.statusData.image2 = gefuehlImage2g;
        this.statusData.image3 = gefuehlImage3g;
        this.statusData.image4 = gefuehlImage4g;
    }

}

export default EditStatusController;