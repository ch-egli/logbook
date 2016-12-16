package ch.egli.training.model;


import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Date;

/**
 * Entity containing a status unit.
 *
 * @author Christian Egli
 * @since 20.06.2016.
 */
@Entity
@Table(name="status")
public class Status {

    @Id
    @SequenceGenerator(name="status_id_seq", sequenceName="status_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="status_id_seq")
    private Long id;

    @NotNull
    private String benutzername;

    @Column
    @NotNull
    private Date datum;

    @Column
    @Min(0) @Max(20)
    private Float schlaf;

    @Column
    @Min(1) @Max(4)
    private Float gefuehl;

    @Column
    private String bemerkung;

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

    public Float getSchlaf() {
        return schlaf;
    }

    public void setSchlaf(Float schlaf) {
        this.schlaf = schlaf;
    }

    public Float getGefuehl() {
        return gefuehl;
    }

    public void setGefuehl(Float gefuehl) {
        this.gefuehl = gefuehl;
    }

    public String getBemerkung() {
        return bemerkung;
    }

    public void setBemerkung(String bemerkung) {
        this.bemerkung = bemerkung;
    }
}
