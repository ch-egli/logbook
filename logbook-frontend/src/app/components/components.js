/**
 * Angular-Modul der Komponenten
 * - Hier werden alle Komponenten als einheitliches Modul exportiert
 *
 * @author Christian Egli
 * @version: 0.0.1
 * @since 28.01.2016
 */
import angular from 'angular';
import commonComponents from './common/common';
import securityComponents from './security/security';

let componentModule = angular.module('app.components', [
    commonComponents.name, securityComponents.name
]);

export default componentModule;