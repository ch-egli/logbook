/**
 * Definition des Info-Controllers
 *
 * @author Christian Egli
 * @version: 0.0.1
 * @since 28.04.2016
 */
class InfoController {
    /*@ngInject*/
    constructor($log, $uibModalInstance, wo) {
        this.$log = $log;
        this.wo = wo;
        this.$uibModalInstance = $uibModalInstance;
    }

    closeModal() {
        this.$log.debug('closeModal...');
    }

}

export default InfoController;