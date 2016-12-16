/**
 * Definition der EditWo-Komponente
 *
 * @author Christian Egli
 * @version: 0.0.1
 * @since 28.01.2016
 */
import angular from 'angular';
import uiRouter from 'angular-ui-router';
import uiBootstrap from 'angular-ui-bootstrap';

import template from './editwo.html';
import controller from './editwo.controller';

let editWoModule = angular.module('editwo', [
    uiRouter, uiBootstrap
])
    .config(/*@ngInject*/($stateProvider) => {
        $stateProvider.state('editwo', {
            url: '/editwo/:id',
            template: '<editwo></editwo>'
        });
    })

    .component('editwo', {
        template,
        controller
    });

export default editWoModule;