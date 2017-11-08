class ChartsController {
    /*@ngInject*/
    constructor(config, $log, workoutsService) {
        this.$log = $log;
        this.config = config;
        this.workoutsService = workoutsService;
        this.title = "Charts";

/*
        this.woDataZ = [
        {"id":48,"benutzername":"liv","datum":"2016-01-11","ort":"K44","wettkampf":null,"schlaf":null,"gefuehl":1,"lead":null,"bouldern":null,"campus":null,"kraftraum":1,"dehnen":null,"mentaltraining":null,"jogging":null,"geraete":null,"routen":null,"art":null,"zuege":null,"wiederholungen":null,"bloecke":null,"serien":null,"pausen":null,"belastung":17,"zuege12":150,"zuege23":null,"zuege34":null,"trainingszeit":120,"sonstiges":null},
        {"id":49,"benutzername":"liv","datum":"2016-01-13","ort":"K44","wettkampf":null,"schlaf":null,"gefuehl":1,"lead":1,"bouldern":null,"campus":null,"kraftraum":null,"dehnen":null,"mentaltraining":null,"jogging":null,"geraete":null,"routen":null,"art":null,"zuege":null,"wiederholungen":null,"bloecke":null,"serien":null,"pausen":null,"belastung":17,"zuege12":null,"zuege23":225,"zuege34":225,"trainingszeit":180,"sonstiges":null},
        {"id":50,"benutzername":"liv","datum":"2016-01-15","ort":"Matten","wettkampf":null,"schlaf":null,"gefuehl":1,"lead":null,"bouldern":1,"campus":null,"kraftraum":null,"dehnen":null,"mentaltraining":null,"jogging":null,"geraete":null,"routen":null,"art":null,"zuege":null,"wiederholungen":null,"bloecke":null,"serien":null,"pausen":null,"belastung":16,"zuege12":300,"zuege23":null,"zuege34":150,"trainingszeit":120,"sonstiges":null}
        ]
        this.woDataL = [
        {"id":48,"benutzername":"liv","datum":"2016-01-11","ort":"K44","wettkampf":null,"schlaf":null,"gefuehl":1,"lead":null,"bouldern":null,"campus":null,"kraftraum":1,"dehnen":null,"mentaltraining":null,"jogging":null,"geraete":null,"routen":null,"art":null,"zuege":null,"wiederholungen":null,"bloecke":null,"serien":null,"pausen":null,"belastung":12,"zuege12":150,"zuege23":null,"zuege34":null,"trainingszeit":120,"sonstiges":null},
        {"id":49,"benutzername":"liv","datum":"2016-01-13","ort":"K44","wettkampf":null,"schlaf":null,"gefuehl":1,"lead":1,"bouldern":null,"campus":null,"kraftraum":null,"dehnen":null,"mentaltraining":null,"jogging":null,"geraete":null,"routen":null,"art":null,"zuege":null,"wiederholungen":null,"bloecke":null,"serien":null,"pausen":null,"belastung":14,"zuege12":null,"zuege23":225,"zuege34":225,"trainingszeit":180,"sonstiges":null},
        {"id":50,"benutzername":"liv","datum":"2016-01-15","ort":"Matten","wettkampf":null,"schlaf":null,"gefuehl":1,"lead":null,"bouldern":1,"campus":null,"kraftraum":null,"dehnen":null,"mentaltraining":null,"jogging":null,"geraete":null,"routen":null,"art":null,"zuege":null,"wiederholungen":null,"bloecke":null,"serien":null,"pausen":null,"belastung":15,"zuege12":300,"zuege23":null,"zuege34":150,"trainingszeit":120,"sonstiges":null}
        ]
*/

        this.woDataZ = this.workoutsService.getWorkoutsByUserAndYear("zoe", 2017);
        this.woDataL = this.workoutsService.getWorkoutsByUserAndYear("liv", 2017);

        //this.labels = ["Jan", "Feb", "Mar", "Apr", "Mai", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dez"];
        this.labels = ["1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50","51","52"];
        this.series = ['Zoé', 'Liv'];

/*
        this.data = [
            [65, 59, 80, 81, 56, 55, 40],
            [28, 48, 40, 19, 86, 27, 90]
        ];
*/

        this.data = [
            this.woDataZ,
            this.woDataL
        ];


        this.onClick = function (points, evt) {
            console.log(points, evt);
        };

        this.datasetOverride = [{ yAxisID: 'y-axis-1' }, { yAxisID: 'y-axis-2' }];
        this.options = {
            scales: {
                yAxes: [
                {
                    id: 'y-axis-1',
                    type: 'linear',
                    display: true,
                    position: 'left'
                },
                {
                    id: 'y-axis-2',
                    type: 'linear',
                    display: true,
                    position: 'right'
                }
                ]
            },
            legend: { display: true }
        };

    }

}

export default ChartsController;