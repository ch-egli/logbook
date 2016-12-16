class AboutController {
    /*@ngInject*/
    constructor(config, $log) {
        this.$log = $log;
        this.config = config;

        this.aboutMessage = 'Logbook App';
        this.logbookVersion = this.config.logbookVersion;
    }

}

export default AboutController;