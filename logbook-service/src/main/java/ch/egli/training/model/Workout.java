package ch.egli.training.model;


import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Date;

/**
 * Entity containing a training unit.
 *
 * @author Christian Egli
 * @since 2/1/16.
 */
@Entity
@Table(name="workout")
public class Workout {

    @Id
    @SequenceGenerator(name="workout_id_seq", sequenceName="workout_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="workout_id_seq")
    private Long id;

    @NotNull
    //@ManyToOne
    //@JoinColumn(name = "benutzer", nullable = false)
    private String benutzername;

    @Column
    @NotNull
    private Date datum;

    @Column
    private String ort;

    @Column
    private String wettkampf;

    @Column
    @Min(0) @Max(20)
    private Integer schlaf;

    @Column
    @Min(1) @Max(4)
    private Integer gefuehl;

    @Column
    @Min(0) @Max(9)
    private Integer lead;

    @Column
    @Min(0) @Max(9)
    private Integer bouldern;

    @Column
    @Min(0) @Max(9)
    private Integer campus;

    @Column
    @Min(0) @Max(9)
    private Integer kraftraum;

    @Column
    @Min(0) @Max(9)
    private Integer dehnen;

    @Column
    @Min(0) @Max(9)
    private Integer mentaltraining;

    @Column
    @Min(0) @Max(9)
    private Integer jogging;

    @Column
    private String geraete;

    @Column
    private String routen;

    @Column
    private String art;

    @Column
    private String zuege;

    @Column
    @Min(0) @Max(10000)
    private Integer wiederholungen;

    @Column
    @Min(0) @Max(10000)
    private Integer bloecke;

    @Column
    @Min(0) @Max(10000)
    private Integer serien;

    @Column
    @Min(0) @Max(10000)
    private Integer pausen;

    @Column
    @Min(6) @Max(20)
    private Integer belastung;

    @Column
    @Min(0)
    private Integer zuege12;

    @Column
    @Min(0)
    private Integer zuege23;

    @Column
    @Min(0)
    private Integer zuege34;

    @Column
    @Min(0) @Max(600)
    private Integer trainingszeit;

    @Column
    private String sonstiges;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBenutzername() {
        return benutzername;
    }

    public void setBenutzername(String benutzername) {
        this.benutzername = benutzername;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public String getWettkampf() {
        return wettkampf;
    }

    public void setWettkampf(String wettkampf) {
        this.wettkampf = wettkampf;
    }

    public Integer getSchlaf() {
        return schlaf;
    }

    public void setSchlaf(Integer schlaf) {
        this.schlaf = schlaf;
    }

    public Integer getGefuehl() {
        return gefuehl;
    }

    public void setGefuehl(Integer gefuehl) {
        this.gefuehl = gefuehl;
    }

    public Integer getLead() {
        return lead;
    }

    public void setLead(Integer lead) {
        this.lead = lead;
    }

    public Integer getBouldern() {
        return bouldern;
    }

    public void setBouldern(Integer bouldern) {
        this.bouldern = bouldern;
    }

    public Integer getCampus() {
        return campus;
    }

    public void setCampus(Integer campus) {
        this.campus = campus;
    }

    public Integer getKraftraum() {
        return kraftraum;
    }

    public void setKraftraum(Integer kraftraum) {
        this.kraftraum = kraftraum;
    }

    public Integer getDehnen() {
        return dehnen;
    }

    public void setDehnen(Integer dehnen) {
        this.dehnen = dehnen;
    }

    public Integer getMentaltraining() {
        return mentaltraining;
    }

    public void setMentaltraining(Integer mentaltraining) {
        this.mentaltraining = mentaltraining;
    }

    public Integer getJogging() {
        return jogging;
    }

    public void setJogging(Integer jogging) {
        this.jogging = jogging;
    }

    public String getGeraete() {
        return geraete;
    }

    public void setGeraete(String geraete) {
        this.geraete = geraete;
    }

    public String getRouten() {
        return routen;
    }

    public void setRouten(String routen) {
        this.routen = routen;
    }

    public String getArt() {
        return art;
    }

    public void setArt(String art) {
        this.art = art;
    }

    public String getZuege() {
        return zuege;
    }

    public void setZuege(String zuege) {
        this.zuege = zuege;
    }

    public Integer getWiederholungen() {
        return wiederholungen;
    }

    public void setWiederholungen(Integer wiederholungen) {
        this.wiederholungen = wiederholungen;
    }

    public Integer getBloecke() {
        return bloecke;
    }

    public void setBloecke(Integer bloecke) {
        this.bloecke = bloecke;
    }

    public Integer getSerien() {
        return serien;
    }

    public void setSerien(Integer serien) {
        this.serien = serien;
    }

    public Integer getPausen() {
        return pausen;
    }

    public void setPausen(Integer pausen) {
        this.pausen = pausen;
    }

    public Integer getBelastung() {
        return belastung;
    }

    public void setBelastung(Integer belastung) {
        this.belastung = belastung;
    }

    public Integer getZuege12() {
        return zuege12;
    }

    public void setZuege12(Integer zuege12) {
        this.zuege12 = zuege12;
    }

    public Integer getZuege23() {
        return zuege23;
    }

    public void setZuege23(Integer zuege23) {
        this.zuege23 = zuege23;
    }

    public Integer getZuege34() {
        return zuege34;
    }

    public void setZuege34(Integer zuege34) {
        this.zuege34 = zuege34;
    }

    public Integer getTrainingszeit() {
        return trainingszeit;
    }

    public void setTrainingszeit(Integer trainingszeit) {
        this.trainingszeit = trainingszeit;
    }

    public String getSonstiges() {
        return sonstiges;
    }

    public void setSonstiges(String bemerkungen) {
        this.sonstiges = bemerkungen;
    }
}
