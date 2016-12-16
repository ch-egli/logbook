/**
 * Definition der EditStatus-Komponente
 *
 * @author Christian Egli
 * @version: 0.0.1
 * @since 20.06.2016
 */
import angular from 'angular';
import uiRouter from 'angular-ui-router';
import uiBootstrap from 'angular-ui-bootstrap';

import template from './editstatus.html';
import controller from './editstatus.controller';

let editStatusModule = angular.module('editstatus', [
    uiRouter, uiBootstrap
])
    .config(/*@ngInject*/($stateProvider) => {
        $stateProvider.state('editstatus', {
            url: '/editstatus/:id',
            template: '<editstatus></editstatus>'
        });
    })

    .component('editstatus', {
        template,
        controller
    });

export default editStatusModule;