/**
 * Angular-Modul der Common-Komponenten
 * - Hier werden alle Security-Komponenten als Modul exportiert
 *
 * @author Christian Egli
 * @version: 0.0.1
 * @since 28.01.2016
 */
import angular from 'angular';
import Login from './login/login';
import OAuth from './oauth/oauth';
import Interceptor from './interceptor/interceptor';

let securityModule = angular.module('app.components.authentication', [
    Login.name, OAuth.name, Interceptor.name
]);

export default securityModule;