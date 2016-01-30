package ch.egli.training.controller;

import ch.egli.training.model.Workout;
import ch.egli.training.repository.WorkoutRepository;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by christian on 1/30/16.
 */
@CrossOrigin // allow cross-origin requests for angular frontends...
@RestController
public class ComputeResultController {

    @Autowired
    private WorkoutRepository workoutRepository;

    @RequestMapping(value="/workouts/computeresults", method= RequestMethod.GET)
    public ResponseEntity<?> computeResult() {
        Iterable<Workout> allWorkouts = workoutRepository.findAll();

        boolean result = false;
        final Resource resource = new ClassPathResource("workouts.xlsx");
        try (InputStream inputStream = resource.getInputStream()) {
            result = readFromExcel(inputStream);
        } catch (Exception ex) {
            System.out.println("Excel Exception " + ex.getMessage());
        }

        return new ResponseEntity(HttpStatus.MULTI_STATUS.OK);
    }

    private boolean readFromExcel(@NotNull final InputStream is) {

        final Workbook workbook;
        try {
            workbook = WorkbookFactory.create(is);
        } catch (IOException | InvalidFormatException ex) {
            throw new RuntimeException("read exception", ex);
        } finally {
            try {
                is.close();
            } catch (IOException ioEx) {
                System.out.println("Cannot close input stream");
            }
        }

        // iterate sheets
        for (int sheetId = 0; sheetId < workbook.getNumberOfSheets(); sheetId++) {
            final Sheet sheet = workbook.getSheetAt(sheetId);
            String sheetName = sheet.getSheetName();
            String lastPrefix = ""; // allows abbreviated key names per sheet

            // read column labels from first row
            Map<Integer, String> columns = this.readFirstRow(sheet);

            // iterate rows, omit the first one containing the header
            for (int r = 1; r <= sheet.getLastRowNum(); r++) {
                // row numbers in an Excel sheet are 1 based
                int rowNumber = r + 1;

                // only read rows containing data (at least a key)
                final Row row = sheet.getRow(r);
                if (row == null) {
                    continue;
                }
                final Cell cell = row.getCell(0); // key
                if (cell == null) {
                    continue;
                }

                // read the data of an entire row
                lastPrefix = this.handleRow(sheetName, row, rowNumber, columns);
            }
        }
        return true;
    }

    private String handleRow(final String sheetName, final Row row, final int rowNumber, final Map<Integer, String> columns) {
        int columnToRead = 0; // used for error messages
        try {
            Cell cell = row.getCell(columnToRead); // key

            columnToRead = 1;
            cell = row.getCell(columnToRead); // scope
            String scope = getStringCellValue(cell);

            return "";
        } catch (RuntimeException ex) {
            throw new RuntimeException("handleRow", ex);
        }
    }

    private Map<Integer, String> readFirstRow(Sheet sheet) {
        final Map<Integer, String> result = new HashMap<Integer, String>();

        final Row row = sheet.getRow(0);
        for (Integer i = 1; i < row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i);
            if (cell == null) {
                break;
            }

            // get label in header
            String label = cell.getStringCellValue();
            result.put(i, label);
        }
        return result;
    }

    /**
     * Get the value of a cell as String value.
     */
    private String getStringCellValue(final Cell cell) {
        return cell != null ? cell.getStringCellValue() : "";
    }

}
