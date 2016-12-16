/**
 * Definition des EditWo-Controllers
 *
 * @author Christian Egli
 * @version: 0.0.1
 * @since 28.01.2016
 */
import infoDialogImage from "./images/belastung.png"

import gefuehlImage1 from "../images/grinning.png"
import gefuehlImage2 from "../images/smirking.png"
import gefuehlImage3 from "../images/frowning.png"
import gefuehlImage4 from "../images/fearful.png"
import gefuehlImage1g from "../images/grinning-g.png"
import gefuehlImage2g from "../images/smirking-g.png"
import gefuehlImage3g from "../images/frowning-g.png"
import gefuehlImage4g from "../images/fearful-g.png"

class EditWoController {
    /*@ngInject*/
    constructor(workoutsService, statusService, oAuthService, config, $window, $log, $state, $stateParams) {
        this.workoutsService = workoutsService;
        this.statusService = statusService;
        this.$log = $log;
        this.$state = $state;
        this.$window = $window;

        this.oAuthService = oAuthService;
        this.config = config;

        this.title = 'Trainingseinheit bearbeiten...';
        this.showInfoImage = false;
        this.workoutLocations = this.config.workoutLocations;
        this.id = $stateParams.id;
        this.$log.debug('workout-id: ' + this.id);

        this.username = null;
        let authData = this.oAuthService.getAuthData();
        if (authData !== null) {
            this.username = authData.name;
        }
        this.$log.debug('username (in constructor): ' + this.username);

        this.woData = workoutsService.getWorkoutById(this.id);
        this.woData.id = this.id;
        this.woData.username = this.username;

        this.statusData = {};
        this.statusData.id = null;
        this.statusData.schlaf = this.config.workoutDefaultSchlaf;
        this.statusData.gefuehl = this.config.workoutDefaultGefuehl;

        this.statusData.image1 = gefuehlImage1g;
        this.statusData.image2 = gefuehlImage2g;
        this.statusData.image3 = gefuehlImage3g;
        this.statusData.image4 = gefuehlImage4g;

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

    submitWorkout() {
        this.$log.debug('id: ' + this.woData.id);
        this.$log.debug('benutzername: ' + this.woData.username);
        this.$log.debug('datum: ' + this.woData.datum);
        this.$log.debug('ort1: ' + this.woData.ort1);
        this.$log.debug('ort2: ' + this.woData.ort2);
        this.$log.debug('lead: ' + this.woData.lead);
        this.$log.debug('bouldern: ' + this.woData.bouldern);
        this.$log.debug('kraftraum: ' + this.woData.kraftraum);
        this.$log.debug('dehnen: ' + this.woData.dehnen);
        this.$log.debug('campus: ' + this.woData.campus);
        this.$log.debug('mentaltraining: ' + this.woData.mentaltraining);
        this.$log.debug('geraete: ' + this.woData.geraete);
        this.$log.debug('belastung: ' + this.woData.belastung);
        this.$log.debug('zuege12: ' + this.woData.zuege12);
        this.$log.debug('zuege23: ' + this.woData.zuege23);
        this.$log.debug('zuege34: ' + this.woData.zuege34);
        this.$log.debug('wettkampf: ' + this.woData.wettkampf);
        this.$log.debug('sonstiges: ' + this.woData.sonstiges);

        this.$log.debug('gefuehl: ' + this.statusData.gefuehl);
        this.$log.debug('schlaf: ' + this.statusData.schlaf);

        // add workout
        let dataObj = {
            "id": this.woData.id,
            "benutzername": this.woData.username,
            "datum": this.woData.datum,
            "ort": this.woData.ort2 != null ? this.woData.ort2 : this.woData.ort1,
            "lead": this.woData.lead === true ? 1 : null,
            "bouldern": this.woData.bouldern === true ? 1 : null,
            "kraftraum": this.woData.kraftraum === true ? 1 : null,
            "dehnen": this.woData.dehnen === true ? 1 : null,
            "campus": this.woData.campus === true ? 1 : null,
            "mentaltraining": this.woData.mentaltraining === true ? 1 : null,
            "geraete": this.woData.geraete === true ? 1 : null,
            "belastung": this.woData.belastung,
            "zuege12": this.woData.zuege12,
            "zuege23": this.woData.zuege23,
            "zuege34": this.woData.zuege34,
            "trainingszeit": this.woData.trainingszeit,
            "wettkampf": this.woData.wettkampf,
            "sonstiges": this.woData.sonstiges,
            "schlaf": this.woData.schlaf,
            "gefuehl": this.woData.gefuehl
        };
        this.workoutsService.editWorkout(dataObj);

        // navigate home...
        this.$window.localStorage['activeTab'] = 0;
        this.$state.go('home');
    }

    cancelWorkout() {
        // navigate home...
        this.$state.go('home');
    }

    showInfo() {
        this.showInfoImage = !this.showInfoImage;
    }

    getInfoImage() {
        //this.$log.debug('got info image...');
        return infoDialogImage;
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

    onDateChange() {
        this.$log.debug('Date changed: new date is ' + this.woData.datum);
        this.statusData = this.statusService.getStatusByDate(this.username, this.woData.datum);
    }

}

export default EditWoController;