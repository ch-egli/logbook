/**
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2015.
 *
 * ESTA WebJS: Definition der Login-Komponente.
 *
 * @author u220374 (Reto Lehmann)
 * @version: 0.0.2
 * @since 06.11.2015, 2015.
 */
import angular from 'angular';
import uiRouter from 'angular-ui-router';

import template from './login.html';
import controller from './login.controller';

let loginModule = angular.module('login', [
    uiRouter
])
    .config(/*@ngInject*/($stateProvider) => {
        $stateProvider
            .state('login', {
                url: '/login', template: '<login></login>'
            })
            .state('logout', {
                url: '/logout',
                controller: ['oAuthService', (oAuthService) => {
                    oAuthService.logout();
                }]
            })
            .state('logincallback', {
                url: '/logincallback',
                controller: ['oAuthService', (oAuthService) => {
                    oAuthService.handleCallback();
                }]
            });
    })

    .component('login', {
        template,
        controller
    });

export default loginModule;
