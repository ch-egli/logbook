/**
 * Definition der Benutzer Service Komponente
 *
 * @author Christian Egli
 * @version: 0.0.1
 * @since 20.12.2016
 */
import angular from 'angular';
import benutzerService from './benutzer.service';

/*@ngInject*/
let benutzerModule = angular.module('benutzerService', ['ngResource'])

.service('benutzerService', benutzerService);

export default benutzerModule;