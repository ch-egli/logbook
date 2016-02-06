package ch.egli.training.controller;

import ch.egli.training.exception.BadRequestException;
import ch.egli.training.model.Workout;
import ch.egli.training.repository.WorkoutRepository;
import ch.egli.training.util.ResourceValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

/**
 * REST controller for accessing workouts/training units.
 *
 * @author Christian Egli
 * @since 2/1/16.
 */
@CrossOrigin // allow cross-origin requests for angular frontends...
@RestController
@RequestMapping({"/v1/", "/oauth2/v1/"})
public class WorkoutController {

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    ResourceValidator resourceValidator;

    @RequestMapping(value="/users/{benutzername}/workouts", method= RequestMethod.GET)
    public ResponseEntity<Iterable<Workout>> getAllWorkouts(@PathVariable String benutzername) {
        resourceValidator.validateUser(benutzername);
        final Iterable<Workout> allWorkouts = workoutRepository.findByBenutzername(benutzername);
        return new ResponseEntity<Iterable<Workout>>(allWorkouts, HttpStatus.OK);
    }

    @RequestMapping(value="/users/{benutzername}/workouts/{workoutId}", method= RequestMethod.GET)
    public ResponseEntity<?> getWorkout(@PathVariable String benutzername, @PathVariable Long workoutId) {
        resourceValidator.validateUser(benutzername);
        resourceValidator.validateWorkout(workoutId);
        final Workout workout = workoutRepository.findByBenutzernameAndId(benutzername, workoutId);
        return new ResponseEntity<>(workout, HttpStatus.OK);
    }

    @RequestMapping(value="/users/{benutzername}/workouts", method= RequestMethod.POST)
    public ResponseEntity<?> createWorkout(@PathVariable String benutzername, @Valid @RequestBody Workout workout) {
        resourceValidator.validateUser(benutzername);
        final HttpHeaders responseHeaders = new HttpHeaders();
        try {
            // save the new workout...
            workout.setBenutzername(benutzername);
            workout = workoutRepository.save(workout);

            // Set the location header for the newly created resource
            final URI newWorkoutUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(workout.getId()).toUri();
            responseHeaders.setLocation(newWorkoutUri);
        } catch (Exception e) {
            throw new BadRequestException("Workout could not be saved in the database: " + e.getMessage());
        }

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(value="/users/{benutzername}/workouts/{workoutId}", method= RequestMethod.PUT)
    public ResponseEntity<?> updateWorkout(@RequestBody Workout workout, @PathVariable String benutzername, @PathVariable Long workoutId) {
        resourceValidator.validateUser(benutzername);
        resourceValidator.validateWorkout(workoutId);

        // only allow updating a workout with an id that corresponds to the given workoutId!
        // otherwise we allow insert operation with PUT...
        workout.setId(workoutId);
        workout.setBenutzername(benutzername);

        workout = workoutRepository.save(workout);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value="/users/{benutzername}/workouts/{workoutId}", method= RequestMethod.DELETE)
    public ResponseEntity<?> deleteWorkout(@PathVariable String benutzername, @PathVariable Long workoutId) {
        resourceValidator.validateUser(benutzername);
        resourceValidator.validateWorkout(workoutId);
        workoutRepository.delete(workoutId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
