/**
 * Definition der NewStatus-Komponente
 *
 * @author Christian Egli
 * @version: 0.0.1
 * @since 28.01.2016
 */
import angular from 'angular';
import uiRouter from 'angular-ui-router';
import uiBootstrap from 'angular-ui-bootstrap';

import template from './newstatus.html';
import controller from './newstatus.controller';

let newStatusModule = angular.module('newstatus', [
    uiRouter, uiBootstrap
])
    .config(/*@ngInject*/($stateProvider) => {
        $stateProvider.state('newstatus', {
            url: '/newstatus', template: '<newstatus></newstatus>'
        });
    })

    .component('newstatus', {
        template,
        controller
    });

export default newStatusModule;