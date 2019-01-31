package ch.egli.training.util;

import ch.egli.training.model.Status;
import ch.egli.training.model.Workout;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;

import static org.springframework.util.StringUtils.hasLength;

/**
 * Class providing methods to export workout data to an Excel file.
 *
 * @author Christian Egli
 * @since 1/31/16.
 */
@Component
public class ExcelExporter {

    private static final Logger LOGGER = LogManager.getLogger(ExcelExporter.class.getName());

    private static final String JOGGING_STRING = "Jogging";

    /* Insertion mode if cell already contains a value */
    private enum Mode { ADD, REPLACE, HIGHER }

    /**
     * Export an Excel workbook as an output stream.
     *
     * @param workouts list of workouts for a given person
     * @return output stream of Excel workbook containing workout entries
     */
    public OutputStream exportAllWorkouts(List<Workout> workouts, List<Status> states) {

        OutputStream outputStream = null;
        Resource resource = new ClassPathResource("Vorlage2019.xlsx");
        try (InputStream inputStream = resource.getInputStream()) {
            outputStream = exportToExcel(inputStream, workouts, states);
        } catch (Exception ex) {
            LOGGER.error("Error during Excel export: ", ex);
        }

        return outputStream;
    }


    /**
     * Export given workouts to Excel workbook.
     *
     * @param is input stream containing workbook
     * @param workouts list of workouts
     * @param states list of statuses
     * @return output stream of workbook containing workout entries
     * @throws IOException
     * @throws InvalidFormatException
     */
    private OutputStream exportToExcel(@NotNull final InputStream is, List<Workout> workouts, List<Status> states) throws IOException, InvalidFormatException {

        final Workbook workbook = WorkbookFactory.create(is);
        is.close();

/*
        // if a special style is required...
        final CellStyle workoutCellStyle = workbook.createCellStyle();
        workoutCellStyle.setLocked(false);
        workoutCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        workoutCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
        workoutCellStyle.setBorderTop(CellStyle.BORDER_THIN);
        workoutCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
        workoutCellStyle.setBorderRight(CellStyle.BORDER_THIN);
*/

        String currentSheetName = "";
        for (Workout workout : workouts) {
            LocalDate date = workout.getDatum().toLocalDate();
            String sheetName = getSheetNameForDate(date);
            DayOfWeek dayOfWeek = date.getDayOfWeek();

            Sheet sheet = workbook.getSheet(sheetName);
            if (!currentSheetName.equals(sheetName)) {
                // we cannot unprotect a sheet more than once!
                try {
                    sheet.protectSheet(null);
                } catch (Exception ex) {
                    LOGGER.debug("unprotecting sheet " + sheet.getSheetName() + " has failed.");
                }
                currentSheetName = sheetName;
            }
            Integer column = dayOfWeek.ordinal() + 1;

            addTextToCellInRowAtPosition(sheet.getRow(1), column, null, workout.getOrt());
            addTextToCellInRowAtPosition(sheet.getRow(2), column, null, workout.getWettkampf());
            addIntegerToCellInRowAtPosition(sheet.getRow(5), column, null, workout.getLead(), Mode.REPLACE);
            addIntegerToCellInRowAtPosition(sheet.getRow(6), column, null, workout.getBouldern(), Mode.REPLACE);
            addIntegerToCellInRowAtPosition(sheet.getRow(7), column, null, workout.getCampus(), Mode.REPLACE);
            addIntegerToCellInRowAtPosition(sheet.getRow(8), column, null, workout.getKraftraum(), Mode.REPLACE);
            addIntegerToCellInRowAtPosition(sheet.getRow(9), column, null, workout.getDehnen(), Mode.REPLACE);
            addIntegerToCellInRowAtPosition(sheet.getRow(10), column, null, workout.getMentaltraining(), Mode.REPLACE);
            addTextToCellInRowAtPosition(sheet.getRow(11), column, null, workout.getGeraete());
            addIntegerToCellInRowAtPosition(sheet.getRow(18), column, null, workout.getBelastung(), Mode.HIGHER);
            addIntegerToCellInRowAtPosition(sheet.getRow(19), column, null, workout.getZuege12(), Mode.ADD);
            addIntegerToCellInRowAtPosition(sheet.getRow(20), column, null, workout.getZuege23(), Mode.ADD);
            addIntegerToCellInRowAtPosition(sheet.getRow(21), column, null, workout.getZuege34(), Mode.ADD);
            addIntegerToCellInRowAtPosition(sheet.getRow(23), column, null, workout.getTrainingszeit(), Mode.ADD);
            addTextToCellInRowAtPosition(sheet.getRow(24), column, null, workout.getSonstiges());

            if (workout.getJogging() != null && workout.getJogging() > 0) {
                addTextToCellInRowAtPosition(sheet.getRow(24), column, null, JOGGING_STRING);
            }
        }

        currentSheetName = "";
        for (Status state : states) {
            LocalDate date = state.getDatum().toLocalDate();
            String sheetName = getSheetNameForDate(date);
            DayOfWeek dayOfWeek = date.getDayOfWeek();

            Sheet sheet = workbook.getSheet(sheetName);
            if (!currentSheetName.equals(sheetName)) {
                // we cannot unprotect a sheet more than once!
                try {
                    sheet.protectSheet(null);
                } catch (Exception ex) {
                    LOGGER.debug("unprotecting sheet " + sheet.getSheetName() + " has failed.");
                }
                currentSheetName = sheetName;
            }
            Integer column = dayOfWeek.ordinal() + 1;

            addNumberToCellInRowAtPosition(sheet.getRow(3), column, null, state.getSchlaf(), Mode.REPLACE);
            addNumberToCellInRowAtPosition(sheet.getRow(4), column, null, state.getGefuehl(), Mode.HIGHER);
            addTextToCellInRowAtPosition(sheet.getRow(24), column, null, state.getBemerkung());
        }

        // Re-evaluate all formulas of the workbook
        FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
        evaluator.evaluateAll();

        // return workbook as an output stream
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        workbook.write(os);
        os.flush();
        os.close();

        return os;
    }

    /**
     * Get the name of the sheet depending on the workout date.
     *
     * @param date workout-date
     * @return name of sheet
     */
    private static String getSheetNameForDate(LocalDate date) {
        Locale locale = new Locale("de", "CH");
        Integer weekOfYear = date.get(WeekFields.of(locale).weekOfWeekBasedYear());
        return weekOfYear.toString();
    }

    /**
     * Create a text cell in the row at the given position.
     *
     * @param row row where cell is inserted
     * @param pos position (starting at 0)
     * @param cellStyle style to apply (ignored if null)
     * @param text text to insert
     */
    private static void addTextToCellInRowAtPosition(final Row row, final int pos, final CellStyle cellStyle, final String text) {
        if (!StringUtils.hasLength(text)) {
            return;
        }

        final Cell cell = row.getCell(pos);
        final String existingText = cell.getStringCellValue();
        if (hasLength(existingText)) {
            cell.setCellValue(existingText + "; " + text);
        } else {
            cell.setCellValue(text);
        }
        if (cellStyle != null) {
            cell.setCellStyle(cellStyle);
        }
    }

    /**
     * Create a numeric cell in the row at the given position.
     *
     * @param row row where cell is inserted
     * @param pos position (starting at 0)
     * @param cellStyle style to apply (ignored if null)
     * @param val numeric value to insert
     * @param mode insertion mode if cell already contains a value
     */
    private static void addNumberToCellInRowAtPosition(final Row row, final int pos, final CellStyle cellStyle, final Float val, final Mode mode) {
        if (val != null) {
            final Cell cell = row.getCell(pos);
            final double existingVal = cell.getNumericCellValue();
            double newVal = 0;
            switch (mode) {
                case ADD:
                    newVal = existingVal + val;
                    break;
                case HIGHER:
                    newVal = existingVal > val ? existingVal : val;
                    break;
                case REPLACE:
                    newVal = val;
                    break;
                default:
                    LOGGER.error("Error during Excel export: Invalid mode " + mode + "; val: " + val);
            }
            cell.setCellValue(newVal);
            if (cellStyle != null) {
                cell.setCellStyle(cellStyle);
            }
        }
    }

    /**
     * Create a numeric cell in the row at the given position.
     *
     * @param row row where cell is inserted
     * @param pos position (starting at 0)
     * @param cellStyle style to apply (ignored if null)
     * @param val numeric value to insert
     * @param mode insertion mode if cell already contains a value
     */
    private static void addIntegerToCellInRowAtPosition(final Row row, final int pos, final CellStyle cellStyle, final Integer val, final Mode mode) {
        if (val != null) {
            final Cell cell = row.getCell(pos);
            final int existingVal = (int) cell.getNumericCellValue();
            int newVal = 0;
            switch (mode) {
                case ADD:
                    newVal = existingVal + val;
                    break;
                case HIGHER:
                    newVal = existingVal > val ? existingVal : val;
                    break;
                case REPLACE:
                    newVal = val;
                    break;
                default:
                    LOGGER.error("Error during Excel export: Invalid mode " + mode + "; val: " + val);
            }
            cell.setCellValue(newVal);
            if (cellStyle != null) {
                cell.setCellStyle(cellStyle);
            }
        }
    }

}
