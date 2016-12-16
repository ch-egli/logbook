/**
 * Hauptkomponente der Angular-Applikation
 *
 * @author Christian Egli
 * @version: 0.0.1
 * @since 28.01.2016
 */
import template from './app.html';
import './app.css';

let appComponent = () => {
    return {
        template, restrict: 'E'
    };
};

export default appComponent;