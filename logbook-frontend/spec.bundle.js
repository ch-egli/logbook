/**
 * ESTA WebJS: Vorarbeiten fuer Unit-Tests
 * - Karma kann zurzeit nur ES5 Tests ausfuehren, mit folgendem Code
 *   werden die *.spec.js Dateien geladen und transpiled.
 */
import 'angular';
import 'angular-mocks';

let context = require.context('./src/app', true, /\.spec\.js/);
context.keys().forEach(context);