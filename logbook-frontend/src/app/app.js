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
        logbookVersion: '1.4.3',

        //resourceServerUrl: window.location.origin === 'http://localhost:3001' ? 'http://ec2-52-57-16-191.eu-central-1.compute.amazonaws.com:8181/' : window.location.origin + '/',

        resourceServerUrl: 'https://logbook.snoopfish.ch:443/',
        authServerUrl: 'https://logbook.snoopfish.ch:443/',

/*
        resourceServerUrl: 'http://localhost:8080/',
        authServerUrl: 'http://localhost:8080/',
*/


        authClientId: 'logbookAngularClient',
        authClientSecret: 'myAbcdghij9876Secret',

        workoutDefaultOrt1: 'Wilderswil',
        workoutDefaultOrt2: null,
        workoutDefaultSchlaf: 9,
        workoutDefaultLead: null,
        workoutDefaultBouldern: null,
        workoutDefaultKraftraum: null,
        workoutDefaultDehnen: null,
        workoutDefaultCampus: null,
        workoutDefaultMentaltraining: null,
        workoutDefaultJogging: null,
        workoutDefaultGeraete: null,
        workoutDefaultBelastung: '14',
        workoutDefaultTrainingszeit: null,
        workoutDefaultZuege12: null,
        workoutDefaultZuege23: null,
        workoutDefaultZuege34: null,
        workoutDefaultGefuehl: null,
        workoutDefaultWettkampf: null,
        workoutDefaultSonstiges: null,

        workoutLocations: ["Wilderswil", "Griffbar", "K44", "O'Bloc", "Magnet", "NLZ Biel", "Bimano", "Klettertreff", "Home", "Anderer Ort:"]
    })
    // Die App als Direktive exportieren
    .directive('app', AppComponent);

