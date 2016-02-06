package ch.egli.training.controller;

import ch.egli.training.exception.BadRequestException;
import ch.egli.training.model.Benutzer;
import ch.egli.training.repository.BenutzerRepository;
import ch.egli.training.util.ResourceValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

/**
 * REST controller for accessing users.
 *
 * @author Christian Egli
 * @since 2/1/16.
 */
@CrossOrigin // allow cross-origin requests for angular frontends...
@RestController
@RequestMapping({"/", "/oauth2/"})
public class BenutzerController {

    @Autowired
    BenutzerRepository benutzerRepository;

    @Autowired
    ResourceValidator resourceValidator;

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value="/users", method= RequestMethod.GET)
    public ResponseEntity<Iterable<Benutzer>> getAllUsers() {
        final Iterable<Benutzer> allUsers = benutzerRepository.findAll();
        return new ResponseEntity<Iterable<Benutzer>>(allUsers, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value="/users/{benutzername}", method= RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable String benutzername) {
        resourceValidator.validateUser(benutzername);
        final Benutzer benutzer = benutzerRepository.findByBenutzername(benutzername);
        return new ResponseEntity<>(benutzer, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('admin')")
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
            throw new BadRequestException("User could not be saved in the database: " + e.getMessage());
        }

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value="/users/{benutzername}", method= RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@Valid @RequestBody Benutzer benutzer, @PathVariable String benutzername) {
        resourceValidator.validateUser(benutzername);

        // only allow updating a user with an benutzername that corresponds to the given benutzername!
        // otherwise we allow insert operations with PUT...
        benutzer.setBenutzername(benutzername);

        benutzer = benutzerRepository.save(benutzer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value="/users/{benutzername}", method= RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable String benutzername) {
        resourceValidator.validateUser(benutzername);
        benutzerRepository.deleteByBenutzername(benutzername);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
