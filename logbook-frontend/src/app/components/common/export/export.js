/**
 * Definition der Export-Komponente
 *
 * @author Christian Egli
 * @version: 0.0.1
 * @since 07.05.2016
 */
import angular from 'angular';
import uiRouter from 'angular-ui-router';
import uiBootstrap from 'angular-ui-bootstrap';


import template from './export.html';
import controller from './export.controller';


let exportModule = angular.module('export', [
    uiRouter, uiBootstrap
])
    .config(/*@ngInject*/($stateProvider, $urlRouterProvider) => {
        $urlRouterProvider.otherwise('/');

        $stateProvider.state('export', {
            url: '/export', template: '<export></export>'
        });
    })

   .component('export', {
        template,
        controller
    });

export default exportModule;
