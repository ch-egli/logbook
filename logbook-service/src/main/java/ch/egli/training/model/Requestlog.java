package ch.egli.training.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author Christian Egli
 * @since 12/30/16.
 */
@Entity
@Table(name="requestlog")
public class Requestlog {

    @Id
    @SequenceGenerator(name="requestlog_id_seq", sequenceName="requestlog_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="requestlog_id_seq")
    private Long id;

    @Column
    private Timestamp datum;

    @Column
    private String benutzer;

    @Column
    private String urifilter;

    @Column
    private String message;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getDatum() {
        return datum;
    }

    public void setDatum(Timestamp datum) {
        this.datum = datum;
    }

    public String getBenutzer() {
        return benutzer;
    }

    public void setBenutzer(String benutzer) {
        this.benutzer = benutzer;
    }

    public String getUrifilter() {
        return urifilter;
    }

    public void setUrifilter(String urifilter) {
        this.urifilter = urifilter;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
