/**
 * Definition der Messages-Komponente
 *
 * @author Christian Egli
 * @version: 0.0.1
 * @since 28.01.2016
 */
import angular from 'angular';

import service from './messages.service';
import template from './messages.html';
import controller from './messages.controller';


let messagesModule = angular.module('messages', [])
    .service('messagesService', service)

    .component('messages', {
        template,
        controller
    });

export default messagesModule;