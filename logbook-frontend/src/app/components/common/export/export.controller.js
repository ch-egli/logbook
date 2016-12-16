/**
 * Definition des Export-Controllers
 *
 * @author Christian Egli
 * @version: 0.0.1
 * @since 07.05.2016
 */
class ExportController {
    /*@ngInject*/
    constructor(config, $log, $state, workoutsService) {
        this.config = config;
        this.$log = $log;
        this.$state = $state;
        this.workoutsService = workoutsService;

        this.title = 'Trainingseinheiten nach Excel exportieren...';

        this.athlete = null;
        this.athletes = this.config.athletes;

        this.year = 2016;
    }

    getExport() {
        this.workoutsService.exportWorkoutsToExcel(this.year, this.athlete.name);

        // navigate home...
        this.$state.go('home');
    }

    cancelExport() {
        // navigate home...
        this.$state.go('home');
    }

}

export default ExportController;