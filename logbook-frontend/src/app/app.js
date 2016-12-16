/**
 * Angular-Modul der Hauptkomponente
 *
 * @author Christian Egli
 * @version: 0.0.1
 * @since 28.01.2016
 */

// Vendor-Imports
import angular from 'angular';
import uiRouter from 'angular-ui-router';
import ngResource from 'angular-resource';
import ngCookies from 'angular-cookies';
import ngTranslate from 'angular-translate';
import ngTranslateStaticFilesLoader from 'angular-translate-loader-static-files';
import uiBootstrap from 'angular-ui-bootstrap';
import ngTouch from 'angular-touch';

// SBB Imports
import 'esta-webjs-style/build/css/style.css';

// Interne Modul-Imports
import Components from './components/components';
import AppComponent from './app.component';

// Language file import
import langDe from './languages/lang-de.json';
import langEn from './languages/lang-en.json';

angular.module('app', [
    uiRouter, ngTranslate, ngTranslateStaticFilesLoader, ngCookies, ngResource,
    uiBootstrap, ngTouch, Components.name
])
    .config(/*@ngInject*/($translateProvider, $httpProvider) => {

        // Translation settings
        $translateProvider.translations('de', langDe);
        $translateProvider.translations('en', langEn);
        $translateProvider.preferredLanguage('de').useSanitizeValueStrategy('escape');

        // Http service settings
        $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
        $httpProvider.interceptors.push('oAuthInterceptorService');
    })

    // Globale Konfigurationeinstellungen
    .constant('config', {
        logbookVersion: '1.0.6',

        //resourceServerUrl: window.location.origin === 'http://localhost:3001' ? 'http://ec2-52-57-16-191.eu-central-1.compute.amazonaws.com:8181/' : window.location.origin + '/',
        resourceServerUrl: 'http://ec2-52-57-16-191.eu-central-1.compute.amazonaws.com:8181/',
        authServerUrl: 'http://ec2-52-57-16-191.eu-central-1.compute.amazonaws.com:8181/',

/*
        resourceServerUrl: 'http://192.168.1.120:8181/',
        authServerUrl: 'http://192.168.1.120:8181/',
*/

        authClientId: 'logbookAngularClient',
        authClientSecret: 'myAbcdghij9876Secret',

        athletes: [
            {id: 'zoe', name: 'zoe', title: 'zoe'},
            {id: 'liv', name: 'liv', title: 'liv'},
            {id: 'joelle', name: 'joelle', title: 'joelle'}
        ],

        workoutDefaultOrt1: 'K44',
        workoutDefaultOrt2: null,
        workoutDefaultSchlaf: 9,
        workoutDefaultLead: null,
        workoutDefaultBouldern: null,
        workoutDefaultKraftraum: null,
        workoutDefaultDehnen: null,
        workoutDefaultCampus: null,
        workoutDefaultMentaltraining: null,
        workoutDefaultGeraete: null,
        workoutDefaultBelastung: '14',
        workoutDefaultTrainingszeit: null,
        workoutDefaultZuege12: null,
        workoutDefaultZuege23: null,
        workoutDefaultZuege34: null,
        workoutDefaultGefuehl: null,
        workoutDefaultWettkampf: null,
        workoutDefaultSonstiges: null,

        workoutLocations: ["K44", "Matten", "Griffbar", "O'Bloc", "Magnet", "Klettertreff", "Home", "Anderer Ort:"]
    })
    // Die App als Direktive exportieren
    .directive('app', AppComponent);

