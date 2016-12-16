/**
 * Definition der Info-Komponente
 *
 * @author Christian Egli
 * @version: 0.0.1
 * @since 28.01.2016
 */
import angular from 'angular';
import uiRouter from 'angular-ui-router';
import uiBootstrap from 'angular-ui-bootstrap';


import template from './info.html';
import controller from './info.controller';


let infoModule = angular.module('info', [
    uiRouter, uiBootstrap
])
    .config(/*@ngInject*/($stateProvider, $urlRouterProvider) => {
        $urlRouterProvider.otherwise('/');

        $stateProvider.state('info', {
            url: '/info', template: '<info></info>'
        });
    })

   .component('info', {
        template,
        controller
    });

export default infoModule;
