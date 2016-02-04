package ch.egli.training.controller;

import ch.egli.training.exception.ResourceNotFoundException;
import ch.egli.training.model.Benutzer;
import ch.egli.training.model.Workout;
import ch.egli.training.repository.BenutzerRepository;
import ch.egli.training.repository.WorkoutRepository;
import ch.egli.training.util.ExcelExporter;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

/**
 * Created by christian on 1/30/16.
 */
@CrossOrigin // allow cross-origin requests for angular frontends...
@RestController
public class ComputeResultController {

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private BenutzerRepository benutzerRepository;

    @RequestMapping(value="/workouts/charts", method= RequestMethod.GET)
    public ResponseEntity<?> computeChart() {
        Iterable<Workout> allWorkouts = workoutRepository.findAll();

        // TODO: compute workout charts...

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value="/workouts/excelresults/{year}/{userId}", method=RequestMethod.GET)
    public void getExportedExcelFile(@PathVariable String userId, @PathVariable Integer year, HttpServletResponse response) {

        Benutzer benutzer = null;
        if (year < 2000 || year > 2030) {
            throw new ResourceNotFoundException("No workouts for year '" + year + "' found");
        }

        if (!NumberUtils.isDigits(userId)) {
            throw new ResourceNotFoundException("UserId '" + userId + "' is not a number");
        } else {
            benutzer = benutzerRepository.findOne(Long.valueOf(userId));
            if (benutzer == null) {
                throw new ResourceNotFoundException("User with with id " + userId + " not found");
            }

        }

        List<Workout> userWorkouts = workoutRepository.findByYearAndBenutzer(year, benutzer);
        if(userWorkouts.isEmpty()) {
            throw new ResourceNotFoundException("No workouts for year '" + year + "' and user '" + userId + "' found");
        }

        final Resource resource = new ClassPathResource("workouts.xlsx");
        try (InputStream inputStream = resource.getInputStream()) {
            ByteArrayOutputStream outputStream = (ByteArrayOutputStream) ExcelExporter.exportAllWorkouts(userWorkouts);
            InputStream myStream = new ByteArrayInputStream(outputStream.toByteArray());

            // Set the content type and attachment header.
            final String attachmentHeader = "attachment;filename=workouts2015-" + userId + ".xlsx";
            response.addHeader("Content-disposition", attachmentHeader);
            response.setContentType("txt/plain");
            response.getOutputStream();

            // Copy the stream to the response's output stream.
            IOUtils.copy(myStream, response.getOutputStream());
            response.flushBuffer();

        } catch (Exception ex) {
            throw new RuntimeException("unexpected exception during export to Excel: ", ex);
        }

    }

}
