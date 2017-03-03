class AboutController {
    /*@ngInject*/
    constructor(config, $log) {
        this.$log = $log;
        this.config = config;

        this.aboutMessage = 'Logbook App';
        this.logbookVersion = this.config.logbookVersion;
        this.metaInfo = document.head.querySelector("[name=app-kind]").content;
    }

}

export default AboutController;