package ch.egli.training.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Created by christian on 2/1/16.
 */
@Entity
@Table(name="benutzer")
public class Benutzer {

    @Id
    @SequenceGenerator(name="benutzer_id_seq", sequenceName="benutzer_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="benutzer_id_seq")
    private Long id;

    @Column
    @NotNull
    private String benutzername;

    @Column
    private String passwort;

    @Column
    private String rollen;

/*
    @OneToMany(cascade = { CascadeType.ALL })
    private Set<Workout> workouts;
*/

    @Column
    private String vorname;

    @Column
    private String nachname;

    @Column
    private String email;

    @Column
    private String telefon;

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

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public String getRollen() {
        return rollen;
    }

    public void setRollen(String rollen) {
        this.rollen = rollen;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }
}
