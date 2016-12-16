/**
 * Definition eines Wochentag Filters.
 * Input: Dreistelliger Wochentag in Englisch.
 * Output: Zweistelliger Wochentag in Deutsch.
 *
 * @author Christian Egli
 * @version: 0.0.1
 * @since 28.06.2016
 */
export default () => {
    return (input) => {
        let output = '';
        switch (input) {
          case 'Mon':
            output = 'Mo';
            break;
          case 'Tue':
            output = 'Di';
            break;
          case 'Wed':
            output = 'Mi';
            break;
          case 'Thu':
            output = 'Do';
            break;
          case 'Fri':
            output = 'Fr';
            break;
          case 'Sat':
            output = 'Sa';
            break;
          case 'Sun':
            output = 'So';
            break;
          default:
            output = input;
            break;
        }
        //console.log('weekdayFilter: ' + input + ' => ' + output);

        return output;
    };
};