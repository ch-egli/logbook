/**
 * Definition des Export-Controllers
 *
 * @author Christian Egli
 * @version: 0.0.1
 * @since 07.05.2016
 */
class ExportController {
    /*@ngInject*/
    constructor(config, $log, $state, workoutsService, benutzerService, oAuthService, ) {
        this.config = config;
        this.$log = $log;
        this.$state = $state;
        this.workoutsService = workoutsService;
        this.benutzerService = benutzerService;
        this.oAuthService = oAuthService;

        this.title = 'Trainingseinheiten nach Excel exportieren...';

        this.athlete = null;
        this.athletes = benutzerService.getAthletes();

        this.year = new Date().getFullYear();

        this.metaInfo = document.head.querySelector("[name=app-kind]").content;
        this.isAndroid = this.metaInfo === "android";
    }

    getExport() {
        if (!this.athlete) {
            this.workoutsService.exportWorkoutsToExcel(this.year);
        } else {
            this.workoutsService.exportWorkoutsToExcel(this.year, this.athlete.name);
        }

        // navigate home...
        this.$state.go('home');
    }

    cancelExport() {
        // navigate home...
        this.$state.go('home');
    }

}

export default ExportController;