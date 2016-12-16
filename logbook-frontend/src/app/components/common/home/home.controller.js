/**
 * Definition des Home-Controllers
 *
 * @author Christian Egli
 * @version: 0.0.1
 * @since 28.01.2016
 */
import modalConfirmDeletion from "./confirmDeletion.html"

class HomeController {
    /*@ngInject*/
    constructor(config, workoutsService, statusService, oAuthService, $log, $uibModal, $state, $window, NgTableParams) {
        this.config = config;
        this.workoutsService = workoutsService;
        this.statusService = statusService;
        this.oAuthService = oAuthService;
        this.$uibModal = $uibModal;
        this.$log = $log;
        this.$state = $state;
        this.$window = $window;

        this.athletes = this.config.athletes;

        this.title = 'RZ-BeO Trainings-Logbook';
        this.welcomeMessage = 'Herzlich Willkommen, ' + this.oAuthService.getFirstname();

        this.workoutForm = {};

        this.authData = this.oAuthService.getAuthData();
        this.username = this.oAuthService.getUsername();

        this.activeTab = 0;
        let storageActiveTab = this.$window.localStorage['activeTab'];
        this.$log.debug('storageActiveTab: ' + storageActiveTab);
        if (storageActiveTab) {
            this.activeTab = parseInt(storageActiveTab);
        }
        this.$log.debug('activeTab: ' + this.activeTab);

        this.filter = false;
        let storageFilterVal = this.$window.localStorage['doFilter.' + this.username];
        this.$log.debug('storageFilterVal: ' + storageFilterVal);
        if (storageFilterVal) {
            this.filter = storageFilterVal === 'true' ? true : false;
        }
        let doFilter = this.filter;

        let woService = this.workoutsService;
        this.tableParamsWo = new NgTableParams(
            { page: 1, count: 10 },
            {
                counts: [10, 20, 40],
                paginationMaxBlocks: 7,
                getData: function(params) {
                    return woService.getAllWorkouts(params.page() - 1, params.count(), doFilter, params.filter()).$promise.then(function(data) {
                        params.total(data.totalElements);
                        return data.content;
                    });

                }
            }
        );

        let statusSvc = this.statusService;
        this.tableParamsStatus = new NgTableParams(
            { page: 1, count: 7 },
            {
                counts: [7, 14, 28],
                paginationMaxBlocks: 7,
                getData: function(params) {
                    return statusSvc.getAllStati(params.page() - 1, params.count(), doFilter, params.filter()).$promise.then(function(data) {
                        params.total(data.totalElements);
                        return data.content;
                    });

                }
            }
        );

        this.onFilterChanged = function($event) {
            this.$window.localStorage['doFilter.' + this.username] = $event;
            this.$state.reload();
        }

    }

    askAndDelete(workout) {
         let log = this.$log;
         let woService = this.workoutsService;
         let modalInstance = this.$uibModal.open({
             animation: true,
             template: modalConfirmDeletion
         });

         modalInstance.result.then(function () {
             log.debug('Deletion of workout ' + workout.id + ' has been confirmed');
             woService.deleteWorkout(workout);
         }, function () {
             log.debug('Confirmation dialog has been dismissed');
         });
    }

    isMyWorkout(workout) {
        return this.username === workout.benutzername;
    }

    isMyStatus(status) {
        return this.username === status.benutzername;
    }

    isTrainer() {
        let result = false;
        let userRoles = this.authData.roles;
        if (userRoles.indexOf('trainer') > -1) {
            result = true;
        }
        return result;
    }

    setActiveTab(activeTab) {
        let log = this.$log;
        this.$window.localStorage['activeTab'] = activeTab;
        this.activeTab = activeTab;
        log.debug("activeTab: " + activeTab);
    }

    hasEgliSistersRole() {
        return this.authData.roles.indexOf('egliSisters') > -1;
    }

}

export default HomeController;