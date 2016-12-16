/**
 * Definition des NewWo-Controllers
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

class NewWoController {
    /*@ngInject*/
    constructor(workoutsService, statusService, oAuthService, config, $window, $log, $state) {
        this.workoutsService = workoutsService;
        this.statusService = statusService;
        this.$log = $log;
        this.$state = $state;
        this.$window = $window;

        this.oAuthService = oAuthService;
        this.config = config;

        this.title = 'Neue Trainingseinheit erfassen...';
        this.showInfoImage = false;
        this.workoutLocations = this.config.workoutLocations;

        this.username = null;
        let authData = this.oAuthService.getAuthData();
        if (authData !== null) {
            this.username = authData.name;
        }
        this.$log.debug('username (in constructor): ' + this.username);

        // init default values
        this.ort1 = this.config.workoutDefaultOrt1;
        this.ort2 = this.config.workoutDefaultOrt2;
        this.lead = this.config.workoutDefaultLead;
        this.bouldern = this.config.workoutDefaultBouldern;
        this.kraftraum = this.config.workoutDefaultKraftraum;
        this.dehnen = this.config.workoutDefaultDehnen;
        this.campus = this.config.workoutDefaultCampus;
        this.mentaltraining = this.config.workoutDefaultMentaltraining;
        this.geraete = this.config.workoutDefaultGeraete;
        this.belastung = this.config.workoutDefaultBelastung;
        this.trainingszeit = this.config.workoutDefaultTrainingszeit;
        this.zuege12 = this.config.workoutDefaultZuege12;
        this.zuege23 = this.config.workoutDefaultZuege23;;
        this.zuege34 = this.config.workoutDefaultZuege34;;
        this.wettkampf = this.config.workoutDefaultWettkampf;
        this.sonstiges = this.config.workoutDefaultSonstiges;

        this.statusData = {};
        this.statusData.id = null;
        this.statusData.schlaf = this.config.workoutDefaultSchlaf;
        this.statusData.gefuehl = this.config.workoutDefaultGefuehl;

        this.statusData.image1 = gefuehlImage1g;
        this.statusData.image2 = gefuehlImage2g;
        this.statusData.image3 = gefuehlImage3g;
        this.statusData.image4 = gefuehlImage4g;

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

        this.statusData = this.statusService.getStatusByDate(this.username, this.datum);
    }

    submitWorkout() {
        this.$log.debug('username: ' + this.username);
        this.$log.debug('datum: ' + this.datum);
        this.$log.debug('ort1: ' + this.ort1);
        this.$log.debug('ort2: ' + this.ort2);
        this.$log.debug('lead: ' + this.lead);
        this.$log.debug('bouldern: ' + this.bouldern);
        this.$log.debug('kraftraum: ' + this.kraftraum);
        this.$log.debug('dehnen: ' + this.dehnen);
        this.$log.debug('campus: ' + this.campus);
        this.$log.debug('mentaltraining: ' + this.mentaltraining);
        this.$log.debug('geraete: ' + this.geraete);
        this.$log.debug('belastung: ' + this.belastung);
        this.$log.debug('zuege12: ' + this.zuege12);
        this.$log.debug('zuege23: ' + this.zuege23);
        this.$log.debug('zuege34: ' + this.zuege34);
        this.$log.debug('wettkampf: ' + this.wettkampf);
        this.$log.debug('sonstiges: ' + this.sonstiges);

        this.$log.debug('gefuehl: ' + this.statusData.gefuehl);
        this.$log.debug('schlaf: ' + this.statusData.schlaf);

        // add workout
        let dataObj = {
            "benutzername": this.username,
            "datum": this.datum,
            "ort": this.ort2 != null ? this.ort2 : this.ort1,
            "lead": this.lead === true ? 1 : null,
            "bouldern": this.bouldern === true ? 1 : null,
            "kraftraum": this.kraftraum === true ? 1 : null,
            "dehnen": this.dehnen === true ? 1 : null,
            "campus": this.campus === true ? 1 : null,
            "mentaltraining": this.mentaltraining === true ? 1 : null,
            "geraete": this.geraete === true ? 1 : null,
            "belastung": this.belastung,
            "zuege12": this.zuege12,
            "zuege23": this.zuege23,
            "zuege34": this.zuege34,
            "trainingszeit": this.trainingszeit,
            "wettkampf": this.wettkampf,
            "sonstiges": this.sonstiges,
            "schlaf": this.statusData.schlaf,
            "gefuehl": this.statusData.gefuehl
        };
        this.workoutsService.addWorkout(dataObj);

        let statusDataObj = {
            "id": this.statusData.id,
            "benutzername": this.username,
            "datum": this.datum,
            "gefuehl": this.statusData.gefuehl,
            "schlaf": this.statusData.schlaf,
        }

        if (this.statusData.id) {
            this.statusService.editStatus(statusDataObj);
        } else {
            this.statusService.addStatus(statusDataObj);
        }

        this.ort1 = this.config.workoutDefaultOrt1;
        this.ort2 = this.config.workoutDefaultOrt2;
        this.lead = this.config.workoutDefaultLead;
        this.bouldern = this.config.workoutDefaultBouldern;
        this.kraftraum = this.config.workoutDefaultKraftraum;
        this.dehnen = this.config.workoutDefaultDehnen;
        this.campus = this.config.workoutDefaultCampus;
        this.mentaltraining = this.config.workoutDefaultMentaltraining;
        this.geraete = this.config.workoutDefaultGeraete;
        this.belastung = this.config.workoutDefaultBelastung;
        this.trainingszeit = this.config.workoutDefaultTrainingszeit;
        this.zuege12 = this.config.workoutDefaultZuege12;
        this.zuege23 = this.config.workoutDefaultZuege23;;
        this.zuege34 = this.config.workoutDefaultZuege34;;
        this.wettkampf = this.config.workoutDefaultWettkampf;
        this.sonstiges = this.config.workoutDefaultSonstiges;

        this.statusData.gefuehl = this.config.workoutDefaultGefuehl;
        this.statusData.schlaf = this.config.workoutDefaultSchlaf;

        this.workoutForm.$setPristine();

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
        this.$log.debug('Date changed: new date is ' + this.datum);
        this.statusData = this.statusService.getStatusByDate(this.username, this.datum);
    }

}

export default NewWoController;