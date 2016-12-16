/**
 * Definition der Status Service Komponente
 *
 * @author Christian Egli
 * @version: 0.0.1
 * @since 20.06.2016
 */
import angular from 'angular';
import statusService from './status.service';

/*@ngInject*/
let statusModule = angular.module('statusService', ['ngResource'])

.service('statusService', statusService);

export default statusModule;