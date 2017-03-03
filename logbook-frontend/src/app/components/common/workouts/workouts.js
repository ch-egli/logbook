/**
 * Definition der Workouts Service Komponente
 *
 * @author Christian Egli
 * @version: 0.0.1
 * @since 28.01.2016
 */
import angular from 'angular';
import ngFileSaver from 'angular-file-saver';
import workoutsService from './workouts.service';

/*@ngInject*/
let workoutsModule = angular.module('workoutsService', ['ngResource', ngFileSaver])

.service('workoutsService', workoutsService);

export default workoutsModule;