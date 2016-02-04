package ch.egli.training.controller;

import ch.egli.training.exception.ResourceNotFoundException;
import ch.egli.training.model.Workout;
import ch.egli.training.repository.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

/**
 * Created by christian on 1/24/16.
 */
@CrossOrigin // allow cross-origin requests for angular frontends...
@RestController
public class WorkoutController {

    @Autowired
    private WorkoutRepository workoutRepository;

    @RequestMapping(value="/workouts", method= RequestMethod.GET)
    public ResponseEntity<Iterable<Workout>> getAllWorkouts() {
        final Iterable<Workout> allWorkouts = workoutRepository.findAll();
        return new ResponseEntity<Iterable<Workout>>(allWorkouts, HttpStatus.OK);
    }

    @RequestMapping(value="/workouts/{workoutId}", method= RequestMethod.GET)
    public ResponseEntity<?> getWorkout(@PathVariable Long workoutId) {
        this.verifyWorkout(workoutId);
        final Workout workout = workoutRepository.findOne(workoutId);
        return new ResponseEntity<>(workout, HttpStatus.OK);
    }

    @RequestMapping(value="/workouts", method= RequestMethod.POST)
    public ResponseEntity<?> createWorkout(@Valid @RequestBody Workout workout) {
        final HttpHeaders responseHeaders = new HttpHeaders();
        try {
            // save the new workout...
            workout = workoutRepository.save(workout);

            // Set the location header for the newly created resource
            final URI newWorkoutUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(workout.getId()).toUri();
            responseHeaders.setLocation(newWorkoutUri);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Workout could not be saved in the database: " + e.getMessage());
        }

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(value="/workouts/{workoutId}", method= RequestMethod.PUT)
    public ResponseEntity<?> updateWorkout(@Valid @RequestBody Workout workout, @PathVariable Long workoutId) {
        this.verifyWorkout(workoutId);
        workout = workoutRepository.save(workout);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value="/workouts/{workoutId}", method= RequestMethod.DELETE)
    public ResponseEntity<?> deleteWorkout(@PathVariable Long workoutId) {
        this.verifyWorkout(workoutId);
        workoutRepository.delete(workoutId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    protected void verifyWorkout(Long workoutId) throws ResourceNotFoundException {
        Workout workout = workoutRepository.findOne(workoutId);
        if (workout == null) {
            throw new ResourceNotFoundException("Workout with id " + workoutId + " not found");
        }
    }


}
