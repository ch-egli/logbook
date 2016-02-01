package ch.egli.training.model;


import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Date;

/**
 * Created by christian on 1/24/16.
 */
@Entity
@Table(name="tr_unit")
public class Workout {

    @Id
    @SequenceGenerator(name="tr_unit_id_seq", sequenceName="tr_unit_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="tr_unit_id_seq")
    private Long id;

    @Column
    @NotNull
    private String benutzer;

    @Column
    @NotNull
    private Date datum;

    @Column
    @NotNull
    private String ort;

    @Column
    @Min(0) @Max(20)
    private Integer schlaf;

    @Column
    @Min(1) @Max(4)
    private Integer gefuehl;

    @Column
    @Min(0) @Max(1)
    private Integer lead;

    @Column
    @Min(0) @Max(1)
    private Integer bouldern;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBenutzer() {
        return benutzer;
    }

    public void setBenutzer(String benutzer) {
        this.benutzer = benutzer;
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
}
