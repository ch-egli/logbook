/**
 * Definition der Charts-Komponente
 *
 * @author Christian Egli
 * @version: 0.0.1
 * @since 04.11.2017
 */
import angular from 'angular';
import uiRouter from 'angular-ui-router';

import template from './charts.html';
import controller from './charts.controller';

let chartsModule = angular.module('charts', [
    uiRouter, 'chart.js'
])
    .config(/*@ngInject*/($stateProvider, ChartJsProvider) => {
        $stateProvider.state('charts', {
            url: '/charts', template: '<charts></charts>'
        });

        ChartJsProvider.setOptions({
          //chartColors: ['#FF5252', '#FF8A80'],
          chartColors: ['#FF5252', '#80aaff'],
          responsive: false
        });
        // Configure all line charts
        ChartJsProvider.setOptions('line', {
          showLines: true
        });

    })

    .component('charts', {
        template,
        controller
    });

export default chartsModule;