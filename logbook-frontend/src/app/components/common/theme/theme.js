/**
 * Definition der Theme-Komponente.
 *
 * @author Christian Egli
 * @version: 0.0.1
 * @since 28.01.2016
 */
import angular from 'angular';
import uiRouter from 'angular-ui-router';

import template from './theme.html';
import controller from './theme.controller';


let themeModule = angular.module('theme', [
    uiRouter
])
    .config(/*@ngInject*/($stateProvider, $urlRouterProvider) => {
        $urlRouterProvider.otherwise('/');

        $stateProvider.state('theme', {
            url: '/theme', template: '<theme></theme>'
        });
    })

    .component('theme', {
        template,
        controller
    });

export default themeModule;