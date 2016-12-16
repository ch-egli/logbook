/**
 * Definition der About-Komponente
 *
 * @author Christian Egli
 * @version: 0.0.1
 * @since 27.06.2016
 */
import angular from 'angular';
import uiRouter from 'angular-ui-router';

import template from './about.html';
import controller from './about.controller';

let aboutModule = angular.module('about', [
    uiRouter
])
    .config(/*@ngInject*/($stateProvider) => {
        $stateProvider.state('about', {
            url: '/about', template: '<about></about>'
        });
    })

    .component('about', {
        template,
        controller
    });

export default aboutModule;