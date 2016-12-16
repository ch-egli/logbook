/**
 * Definition der ViewWo-Komponente
 *
 * @author Christian Egli
 * @version: 0.0.1
 * @since 07.05.2016
 */
import angular from 'angular';
import uiRouter from 'angular-ui-router';
import uiBootstrap from 'angular-ui-bootstrap';

import template from './viewwo.html';
import controller from './viewwo.controller';

let viewWoModule = angular.module('viewwo', [
    uiRouter, uiBootstrap
])
    .config(/*@ngInject*/($stateProvider) => {
        $stateProvider.state('viewwo', {
            url: '/viewwo/:id',
            template: '<viewwo></viewwo>'
        });
    })

    .component('viewwo', {
        template,
        controller
    });

export default viewWoModule;