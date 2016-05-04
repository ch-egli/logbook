package ch.egli.training.util;

import ch.egli.training.model.Workout;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

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

/**
 * Class providing methods to export workout date to an Excel file.
 *
 * @author Christian Egli
 * @since 1/31/16.
 */
@Component
public class ExcelExporter {

    private static final Logger LOGGER = LogManager.getLogger(ExcelExporter.class.getName());

    /**
     * Export an Excel workbook as an output stream.
     *
     * @param workouts list of workouts for a given person
     * @return output stream of Excel workbook containing workout entries
     */
    public OutputStream exportAllWorkouts(List<Workout> workouts) {

        OutputStream outputStream = null;
        Resource resource = new ClassPathResource("workouts.xlsx");
        try (InputStream inputStream = resource.getInputStream()) {
            outputStream = exportToExcel(inputStream, workouts);
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
     * @return output stream of workbook containing workout entries
     * @throws IOException
     * @throws InvalidFormatException
     */
    private OutputStream exportToExcel(@NotNull final InputStream is, List<Workout> workouts) throws IOException, InvalidFormatException {

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
                sheet.protectSheet(null);
                currentSheetName = sheetName;
            }
            Integer column = dayOfWeek.ordinal() + 1;

            addTextToCellInRowAtPosition(sheet.getRow(1), column, null, workout.getOrt());
            addTextToCellInRowAtPosition(sheet.getRow(2), column, null, workout.getWettkampf());
            addNumberToCellInRowAtPosition(sheet.getRow(3), column, null, workout.getSchlaf());
            addNumberToCellInRowAtPosition(sheet.getRow(4), column, null, workout.getGefuehl());
            addNumberToCellInRowAtPosition(sheet.getRow(5), column, null, workout.getLead());
            addNumberToCellInRowAtPosition(sheet.getRow(6), column, null, workout.getBouldern());
            addNumberToCellInRowAtPosition(sheet.getRow(7), column, null, workout.getCampus());
            addNumberToCellInRowAtPosition(sheet.getRow(8), column, null, workout.getKraftraum());
            addNumberToCellInRowAtPosition(sheet.getRow(9), column, null, workout.getDehnen());
            addNumberToCellInRowAtPosition(sheet.getRow(10), column, null, workout.getMentaltraining());
            addTextToCellInRowAtPosition(sheet.getRow(11), column, null, workout.getGeraete());
            addNumberToCellInRowAtPosition(sheet.getRow(18), column, null, workout.getBelastung());
            addNumberToCellInRowAtPosition(sheet.getRow(19), column, null, workout.getZuege12());
            addNumberToCellInRowAtPosition(sheet.getRow(20), column, null, workout.getZuege23());
            addNumberToCellInRowAtPosition(sheet.getRow(21), column, null, workout.getZuege34());
            addNumberToCellInRowAtPosition(sheet.getRow(23), column, null, workout.getTrainingszeit());
            addTextToCellInRowAtPosition(sheet.getRow(24), column, null, workout.getSonstiges());
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
        final Cell cell = row.getCell(pos);
        cell.setCellValue(text);
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
     */
    private static void addNumberToCellInRowAtPosition(final Row row, final int pos, final CellStyle cellStyle, final Integer val) {
        if (val != null) {
            final Cell cell = row.getCell(pos);
            cell.setCellValue(val);
            if (cellStyle != null) {
                cell.setCellStyle(cellStyle);
            }
        }
    }

}
