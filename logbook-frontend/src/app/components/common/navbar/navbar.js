/**
 * Definition der Navbar-Komponente
 *
 * @author Christian Egli
 * @version: 0.0.1
 * @since 28.01.2016
 */
import angular from 'angular';
import uiRouter from 'angular-ui-router';

import template from './navbar.html';
import controller from './navbar.controller';


let navbarModule = angular.module('navbar', [
    uiRouter
])
    .component('navbar', {
        template,
        controller
    });

export default navbarModule;