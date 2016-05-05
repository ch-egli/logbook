package ch.egli.training.util;

/**
 * Class providing methods to import workout data from an Excel file.
 *
 * @author Christian Egli
 * @since 5/2/16.
 */

import ch.egli.training.model.Workout;
import ch.egli.training.repository.WorkoutRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class ExcelImporter {

    private static final Logger LOGGER = LogManager.getLogger(ExcelImporter.class.getName());

    @Autowired
    private WorkoutRepository workoutRepository;

    /**
     * Import the workouts for a given year and user.
     * Note: A valid workout must either have a Trainingszeit or a valid Schlaf-entry!
     *
     * Attention: Any already existing workouts for this year and user will deleted!
     */
    public void importWorkoutData(final InputStream is, final int year, final String username) throws IOException, InvalidFormatException {
        final List<Workout> workouts = getWorkoutsFromExcel(is, year, username);

        // delete existing workouts for current person and year...
        workoutRepository.deleteByYearAndBenutzer(year, username);

        // insert new workouts for current person and year...
        for (Workout w : workouts) {
            workoutRepository.save(w);
        }
        LOGGER.debug("import successfully completed!");
    }

    public List<Workout> getWorkoutsFromExcel(final InputStream is, final int year, final String username) throws IOException, InvalidFormatException {
        final List<Workout> workouts = new ArrayList<>();

        final Workbook workbook;
        try {
            workbook = WorkbookFactory.create(is);
        } finally {
            try {
                is.close();
            } catch (IOException ioEx) {
                LOGGER.warn("Cannot close input stream", ioEx);
            }
        }

        for (int sheetId = 5; sheetId <= 56; sheetId++) { // offset: the first 5 sheets contain Uebersicht, Jahresplanung, Daten, Wochenblatt, W
            final Sheet sheet = workbook.getSheetAt(sheetId);
            for (int col = 1; col <= 7; col++) { // monday = 1, tuesday = 2, ...
                final Workout w = createWorkout(sheet, col, year, username);
                if ((w.getTrainingszeit() != null) || (w.getSchlaf() != null)) {
                    // only add workouts that contain either a Trainingszeit or Schlaf value
                    workouts.add(w);
                }
            }
        }

        return workouts;
    }

    private Workout createWorkout(Sheet sheet, int col, int year, String username) {
        final Workout workout = new Workout();
        for (int row = 1; row <= 24; row++) {
            // set workout username and date
            workout.setBenutzername(username);
            final int weekNo = Integer.valueOf(sheet.getSheetName());
            final LocalDate date = getDateFromYearWeekAndWeekday(year, weekNo, col);
            workout.setDatum(java.sql.Date.valueOf(date));

            final Cell cell = sheet.getRow(row).getCell(col);
            switch (row) {
                case 1:
                    if (StringUtils.hasLength(cell.getStringCellValue())) {
                        workout.setOrt(cell.getStringCellValue());
                    }
                    break;
                case 2:
                    if (StringUtils.hasLength(cell.getStringCellValue())) {
                        workout.setWettkampf(cell.getStringCellValue());
                    }
                    break;
                case 3:
                    final int schlaf = (int) cell.getNumericCellValue();
                    if (schlaf > 0) {
                        workout.setSchlaf(schlaf);
                    }
                    break;
                case 4:
                    final int gefuehl = (int) cell.getNumericCellValue();
                    if (gefuehl > 0) {
                        workout.setGefuehl(gefuehl);
                    }
                    break;
                case 5:
                    final int lead = (int) cell.getNumericCellValue();
                    if (lead > 0) {
                        workout.setLead(lead);
                    }
                    break;
                case 6:
                    final int bouldern = (int) cell.getNumericCellValue();
                    if (bouldern > 0) {
                        workout.setBouldern(bouldern);
                    }
                    break;
                case 7:
                    final int campus = (int) cell.getNumericCellValue();
                    if (campus > 0) {
                        workout.setCampus(campus);
                    }
                    break;
                case 8:
                    final int kraftraum = (int) cell.getNumericCellValue();
                    if (kraftraum > 0) {
                        workout.setKraftraum(kraftraum);
                    }
                    break;
                case 9:
                    final int dehnen = (int) cell.getNumericCellValue();
                    if (dehnen > 0) {
                        workout.setDehnen(dehnen);
                    }
                    break;
                case 10:
                    final int mentaltraining = (int) cell.getNumericCellValue();
                    if (mentaltraining > 0) {
                        workout.setMentaltraining(mentaltraining);
                    }
                    break;
                case 11:
                    if (StringUtils.hasLength(cell.getStringCellValue())) {
                        workout.setGeraete(cell.getStringCellValue());
                    }
                    break;
                case 18:
                    final int belastung = (int) cell.getNumericCellValue();
                    if (belastung > 0) {
                        workout.setBelastung(belastung);
                    }
                    break;
                case 19:
                    final int zuege12 = (int) cell.getNumericCellValue();
                    if (zuege12 > 0) {
                        workout.setZuege12(zuege12);
                    }
                    break;
                case 20:
                    final int zuege23 = (int) cell.getNumericCellValue();
                    if (zuege23 > 0) {
                        workout.setZuege23(zuege23);
                    }
                    break;
                case 21:
                    final int zuege34 = (int) cell.getNumericCellValue();
                    if (zuege34 > 0) {
                        workout.setZuege34(zuege34);
                    }
                    break;
                case 23:
                    final int trZeit = (int) cell.getNumericCellValue();
                    if (trZeit > 0) {
                        workout.setTrainingszeit(trZeit);
                    }
                    break;
                case 24:
                    if (StringUtils.hasLength(cell.getStringCellValue())) {
                        workout.setSonstiges(cell.getStringCellValue());
                    }
                    break;
                default:
                    break;
            }
        }
        return workout;
    }

    private LocalDate getDateFromYearWeekAndWeekday(int year, int weekOfYear, int weekDay) {
        org.joda.time.DateTimeZone zone = org.joda.time.DateTimeZone.forID("Europe/Zurich");
        org.joda.time.LocalDate ld = new org.joda.time.LocalDate(zone)
                .withYear(year)
                .withWeekOfWeekyear(weekOfYear)
                .withDayOfWeek(weekDay);
        return LocalDate.of(ld.getYear(), ld.getMonthOfYear(), ld.getDayOfMonth());
    }

}
