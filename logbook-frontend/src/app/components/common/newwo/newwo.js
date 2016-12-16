/**
 * Definition der NewWo-Komponente
 *
 * @author Christian Egli
 * @version: 0.0.1
 * @since 28.01.2016
 */
import angular from 'angular';
import uiRouter from 'angular-ui-router';
import uiBootstrap from 'angular-ui-bootstrap';

import template from './newwo.html';
import controller from './newwo.controller';
import './newwo.css';

let newWoModule = angular.module('newwo', [
    uiRouter, uiBootstrap
])
    .config(/*@ngInject*/($stateProvider) => {
        $stateProvider.state('newwo', {
            url: '/newwo', template: '<newwo></newwo>'
        });
    })

    .component('newwo', {
        template,
        controller
    });

export default newWoModule;