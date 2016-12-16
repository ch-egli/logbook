/**
 * Angular-Modul der Common-Komponenten
 * - Hier werden alle Common-Komponenten als Modul exportiert
 *
 * @author Christian Egli
 * @version: 0.0.1
 * @since 28.01.2016
 */
import angular from 'angular';
import Home from './home/home';
import NewWo from './newwo/newwo';
import NewStatus from './newstatus/newstatus';
import EditWo from './editwo/editwo';
import EditStatus from './editstatus/editstatus';
import ViewWo from './viewwo/viewwo';
import Info from './info/info';
import Export from './export/export';
import Navbar from './navbar/navbar';
import Status from './status/status';
import Workouts from './workouts/workouts';
import Messages from './messages/messages';
import Theme from './theme/theme';
import About from './about/about';

let commonModule = angular.module('app.components.common', [
    Home.name, NewWo.name, NewStatus.name, EditWo.name, EditStatus.name, ViewWo.name, Info.name, Export.name, Navbar.name, Workouts.name, Status.name, Messages.name, Theme.name, About.name
]);

export default commonModule;