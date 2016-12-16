/**
 * Definition des ViewWo-Controllers
 *
 * @author Christian Egli
 * @version: 0.0.1
 * @since 07.05.2016
 */
class ViewWoController {
    /*@ngInject*/
    constructor(workoutsService, oAuthService, config, $log, $state, $stateParams) {
        this.workoutsService = workoutsService;
        this.$log = $log;
        this.$state = $state;

        this.oAuthService = oAuthService;
        this.config = config;

        this.title = 'Trainingseinheit anzeigen...';
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
    }

    cancelWorkout() {
        // navigate home...
        this.$state.go('home');
    }

}

export default ViewWoController;