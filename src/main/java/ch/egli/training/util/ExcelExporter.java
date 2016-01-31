package ch.egli.training.util;

import ch.egli.training.model.Workout;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

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
 * Created by christian on 1/31/16.
 */
public final class ExcelExporter {

    public static OutputStream exportAllWorkouts(List<Workout> workouts) {

        OutputStream outputStream = null;
        Resource resource = new ClassPathResource("workouts.xlsx");
        try (InputStream inputStream = resource.getInputStream()) {
            outputStream = exportToExcel(inputStream, workouts);


        } catch (Exception ex) {
            System.out.println("Excel Exception " + ex.getMessage());
        }

        return outputStream;
    }


    private static OutputStream exportToExcel(@NotNull final InputStream is, List<Workout> workouts) throws IOException, InvalidFormatException {

        final Workbook workbook = WorkbookFactory.create(is);
        is.close();

        for (Workout workout : workouts) {
            LocalDate date = workout.getDatum().toLocalDate();
            String sheetName = getSheetNameForDate(date);
            DayOfWeek dayOfWeek = date.getDayOfWeek();

            Sheet sheet = workbook.getSheet(sheetName);
            Integer column = dayOfWeek.ordinal() + 1;

            createTextCellInRowAtPosition(sheet.getRow(1), column, null, workout.getOrt());
            createNumericCellInRowAtPosition(sheet.getRow(3), column, null, workout.getSchlaf());
            createNumericCellInRowAtPosition(sheet.getRow(4), column, null, workout.getGefuehl());
            createNumericCellInRowAtPosition(sheet.getRow(5), column, null, workout.getLead());
            createNumericCellInRowAtPosition(sheet.getRow(6), column, null, workout.getBouldern());
            createNumericCellInRowAtPosition(sheet.getRow(18), column, null, workout.getBelastung());
            createNumericCellInRowAtPosition(sheet.getRow(19), column, null, workout.getZuege12());
            createNumericCellInRowAtPosition(sheet.getRow(20), column, null, workout.getZuege23());
            createNumericCellInRowAtPosition(sheet.getRow(21), column, null, workout.getZuege34());
            createNumericCellInRowAtPosition(sheet.getRow(23), column, null, workout.getTrainingszeit());
        }

        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        workbook.write(os);
        os.flush();
        os.close();

        return os;
    }

    private static String getSheetNameForDate(LocalDate date) {
/*
        TemporalField weekOfYear = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear();
        Integer weekNumber = date.get(weekOfYear);
        Integer sheetNumber = weekNumber;
*/

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
    private static void createTextCellInRowAtPosition(final Row row, final int pos, final CellStyle cellStyle, final String text) {
        final Cell cell = row.createCell(pos);
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
     * @param val numeric value to insert
     */
    private static void createNumericCellInRowAtPosition(final Row row, final int pos, final CellStyle cellStyle, final double val) {
        final Cell cell = row.createCell(pos);
        cell.setCellValue(val);
        if (cellStyle != null) {
            cell.setCellStyle(cellStyle);
        }
    }


}
