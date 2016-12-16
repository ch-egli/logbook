package ch.egli.training.controller;

import ch.egli.training.exception.BadRequestException;
import ch.egli.training.model.Status;
import ch.egli.training.repository.StatusRepository;
import ch.egli.training.util.PagingInfo;
import ch.egli.training.util.ResourceValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.Date;

/**
 * REST controller for accessing status units.
 *
 * @author Christian Egli
 * @since 20.06.2016
 */
@CrossOrigin // allow cross-origin requests for angular frontends...
@RestController
@RequestMapping({"/v1/"})
public class StatusController {

    private final static String PAGING_INFO = "PAGING_INFO";

    private final static String USERS_ALL = "all";


    // Sorting of statuses is done by datum DESC
    private Sort statusSort = new Sort(Sort.Direction.DESC, "datum");

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    ResourceValidator resourceValidator;

    @RequestMapping(value="/users/{benutzername}/status", method= RequestMethod.GET)
    public ResponseEntity<Iterable<Status>> getStatuses(@PathVariable String benutzername,
            @RequestParam(value = "page", required = false, defaultValue = "0") final int page,
            @RequestParam(value = "size", required = false, defaultValue = "7") final int size,
            HttpServletResponse response) {

        if (USERS_ALL.equalsIgnoreCase(benutzername)) {
            // return all statuses of all users
            return getAllStatusEntries(page, size, response);

        } else {
            // return all statuses of a given user
            return getUsersStatusEntries(benutzername, page, size, response);
        }
    }

    private ResponseEntity<Iterable<Status>> getAllStatusEntries(int page, int size, HttpServletResponse response) {
        final PagingInfo pagingInfo = new PagingInfo(page, size, statusRepository.count());
        response.addHeader(PAGING_INFO, pagingInfo.toString());

        final Iterable<Status> allStatusEntries = statusRepository.findAll(new PageRequest(page, size, statusSort));
        return new ResponseEntity<Iterable<Status>>(allStatusEntries, HttpStatus.OK);
    }

    private ResponseEntity<Iterable<Status>> getUsersStatusEntries(String benutzername, int page, int size, HttpServletResponse response) {
        resourceValidator.validateUser(benutzername);

        final PagingInfo pagingInfo = new PagingInfo(page, size, statusRepository.countByBenutzername(benutzername));
        response.addHeader(PAGING_INFO, pagingInfo.toString());

        final Iterable<Status> allStatusEntries = statusRepository.findByBenutzername(benutzername, new PageRequest(page, size, statusSort));
        return new ResponseEntity<Iterable<Status>>(allStatusEntries, HttpStatus.OK);
    }


    @RequestMapping(value="/users/{benutzername}/status/top/{top}", method= RequestMethod.GET)
    public ResponseEntity<Iterable<Status>> getAllStatusEntries(@PathVariable String benutzername, @PathVariable Integer top) {
        resourceValidator.validateUser(benutzername);
        Pageable topPage = new PageRequest(0, top);
        Page<Status> page = statusRepository.findTopByBenutzername(benutzername, topPage);
        final Iterable<Status> topStatusEntries = page.getContent();
        return new ResponseEntity<Iterable<Status>>(topStatusEntries, HttpStatus.OK);
    }


    @RequestMapping(value="/users/{benutzername}/status/{statusId}", method= RequestMethod.GET)
    public ResponseEntity<?> getStatus(@PathVariable String benutzername, @PathVariable Long statusId) {
        resourceValidator.validateUser(benutzername);
        final Status status = statusRepository.findByBenutzernameAndId(benutzername, statusId);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }


    @RequestMapping(value="/users/{benutzername}/status/datum/{date}", method= RequestMethod.GET)
    public ResponseEntity<?> getStatusByDate(@PathVariable String benutzername, @PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") Date date) {
        resourceValidator.validateUser(benutzername);
        final Status status = statusRepository.findByBenutzernameAndDatum(benutzername, new java.sql.Date(date.getTime()));
        return new ResponseEntity<>(status, HttpStatus.OK);
    }


    @RequestMapping(value="/users/all/status/{statusId}", method= RequestMethod.GET)
    public ResponseEntity<?> getStatus(@PathVariable Long statusId) {
        final Status status = statusRepository.findById(statusId);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }


    @RequestMapping(value="/users/{benutzername}/status", method= RequestMethod.POST)
    public ResponseEntity<?> createStatus(@PathVariable String benutzername, @Valid @RequestBody Status status) {
        resourceValidator.validateUser(benutzername);
        final HttpHeaders responseHeaders = new HttpHeaders();

        /* update status if status for given date already exists */
        Status existingStatus = statusRepository.findByBenutzernameAndDatum(benutzername, status.getDatum());
        if (existingStatus != null) {
            status.setId(existingStatus.getId());
            status.setBenutzername(benutzername);

            statusRepository.save(status);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        try {
            // save the new status...
            status.setBenutzername(benutzername);
            status = statusRepository.save(status);

            // Set the location header for the newly created resource
            final URI newStatusUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(status.getId()).toUri();
            responseHeaders.setLocation(newStatusUri);
        } catch (Exception e) {
            throw new BadRequestException("Status could not be saved in the database: " + e.getMessage());
        }

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }


    @RequestMapping(value="/users/{benutzername}/status/{statusId}", method= RequestMethod.PUT)
    public ResponseEntity<?> updateStatus(@RequestBody Status status, @PathVariable String benutzername, @PathVariable Long statusId) {
        resourceValidator.validateUser(benutzername);

        // only allow updating a status with an id that corresponds to the given statusId!
        // otherwise we allow insert operation with PUT...
        status.setId(statusId);
        status.setBenutzername(benutzername);

        statusRepository.save(status);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @RequestMapping(value="/users/{benutzername}/status/{statusId}", method= RequestMethod.DELETE)
    public ResponseEntity<?> deleteStatus(@PathVariable String benutzername, @PathVariable Long statusId) {
        resourceValidator.validateUser(benutzername);
        statusRepository.delete(statusId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
