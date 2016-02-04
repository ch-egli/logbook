package ch.egli.training.controller;

import ch.egli.training.exception.ResourceNotFoundException;
import ch.egli.training.model.Benutzer;
import ch.egli.training.repository.BenutzerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

/**
 * Created by christian on 2/4/16.
 */
@CrossOrigin // allow cross-origin requests for angular frontends...
@RestController
public class BenutzerController {

    @Autowired
    BenutzerRepository benutzerRepository;

    @RequestMapping(value="/users", method= RequestMethod.GET)
    public ResponseEntity<Iterable<Benutzer>> getAllUsers() {
        final Iterable<Benutzer> allUsers = benutzerRepository.findAll();
        return new ResponseEntity<Iterable<Benutzer>>(allUsers, HttpStatus.OK);
    }

    @RequestMapping(value="/users/{benutzername}", method= RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable String benutzername) {
        this.verifyUser(benutzername);
        final Benutzer benutzer = benutzerRepository.findByBenutzername(benutzername);
        return new ResponseEntity<>(benutzer, HttpStatus.OK);
    }

    @RequestMapping(value="/users", method= RequestMethod.POST)
    public ResponseEntity<?> createUser(@Valid @RequestBody Benutzer benutzer) {
        final HttpHeaders responseHeaders = new HttpHeaders();
        try {
            // save the new workout...
            benutzer = benutzerRepository.save(benutzer);

            // Set the location header for the newly created resource
            final URI newUserUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(benutzer.getId()).toUri();
            responseHeaders.setLocation(newUserUri);
        } catch (Exception e) {
            throw new ResourceNotFoundException("User could not be saved in the database: " + e.getMessage());
        }

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(value="/users/{benutzername}", method= RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@Valid @RequestBody Benutzer benutzer, @PathVariable String benutzername) {
        this.verifyUser(benutzername);

        // only allow updating a user with an benutzername that corresponds to the given benutzername!
        // otherwise we allow insert operations with PUT...
        benutzer.setBenutzername(benutzername);

        benutzer = benutzerRepository.save(benutzer);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value="/users/{benutzername}", method= RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable String benutzername) {
        this.verifyUser(benutzername);
        benutzerRepository.deleteByBenutzername(benutzername);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    protected void verifyUser(String benutzername) throws ResourceNotFoundException {
        Benutzer benutzer = benutzerRepository.findByBenutzername(benutzername);
        if (benutzer == null) {
            throw new ResourceNotFoundException("User '" + benutzername + "' not found");
        }
    }
}
