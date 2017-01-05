package ch.egli.training.controller;

import ch.egli.training.exception.BadRequestException;
import ch.egli.training.exception.InternalServerException;
import ch.egli.training.model.Benutzer;
import ch.egli.training.model.Requestlog;
import ch.egli.training.model.Status;
import ch.egli.training.model.Workout;
import ch.egli.training.repository.BenutzerRepository;
import ch.egli.training.repository.RequestlogRepository;
import ch.egli.training.repository.StatusRepository;
import ch.egli.training.repository.WorkoutRepository;
import ch.egli.training.util.ExcelExporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * REST controller for accessing logbook analysis results.
 *
 * @author Christian Egli
 * @since 2/1/16.
 */
@CrossOrigin // allow cross-origin requests for angular frontends...
@RestController
@RequestMapping({"/v1/"})
public class ComputeResultController {

    private static final Logger LOGGER = LogManager.getLogger(ComputeResultController.class.getName());

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private BenutzerRepository benutzerRepository;

    @Autowired
    private RequestlogRepository requestlogRepository;

    @Autowired
    ExcelExporter excelExporter;

    @RequestMapping(value="/workouts/charts", method= RequestMethod.GET)
    public ResponseEntity<?> computeChart() {
        Iterable<Workout> allWorkouts = workoutRepository.findAll();

        // TODO: compute workout charts...

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value="/user/{benutzername}/excelresults/{year}", method=RequestMethod.GET)
    public void getExportedExcelFile(@PathVariable Integer year, @PathVariable String benutzername,
                                     @RequestParam(name = "requester", required = false) String requester,
                                     HttpServletResponse response) {
        LOGGER.info("calling getExportedExcelFile with params '{}' and '{}'", benutzername, year);
        logRequest(requester, "exportToExcel for user '" + benutzername + "' and year " + year);

        if (year < 2000 || year > 2030) {
            throw new BadRequestException("No workouts for year '" + year + "' found");
        }

        Benutzer benutzer = benutzerRepository.findByBenutzername(benutzername);
        if (benutzer == null) {
            throw new BadRequestException("User with with id " + benutzername + " not found");
        }

        List<Workout> userWorkouts = workoutRepository.findByYearAndBenutzer(year, benutzername);
        if (userWorkouts.isEmpty()) {
            LOGGER.warn("No workouts for year '{}' and user '{}' found", year, benutzername);
        }
        List<Status> userStatuses = statusRepository.findByYearAndBenutzer(year, benutzername);

        try {
            ByteArrayOutputStream outputStream = (ByteArrayOutputStream) excelExporter.exportAllWorkouts(userWorkouts, userStatuses);
            InputStream myStream = new ByteArrayInputStream(outputStream.toByteArray());

            // Set the content type and attachment header.
            final String attachmentHeader = "attachment;filename=workouts-" + year + "-" + benutzername + ".xlsx";
            response.addHeader("Content-disposition", attachmentHeader);
            response.setContentType("txt/plain");
            response.getOutputStream();

            // Copy the stream to the response's output stream.
            IOUtils.copy(myStream, response.getOutputStream());
            response.flushBuffer();

        } catch (Exception ex) {
            LOGGER.error("unexpected exception during export to Excel: ", ex);
            throw new InternalServerException("unexpected exception during export to Excel: ", ex);
        }

    }

    private void logRequest(String requester, String logMessage) {
        Requestlog requestlog = new Requestlog();
        Date date = new Date();
        requestlog.setDatum(new Timestamp(date.getTime()));
        requester = requester != null ? requester : "unknown";
        requestlog.setBenutzer(requester);
        requestlog.setUrifilter("excel-exporter");
        requestlog.setMessage(logMessage);
        requestlogRepository.save(requestlog);
    }


}
