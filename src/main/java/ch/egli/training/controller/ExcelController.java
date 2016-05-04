package ch.egli.training.controller;

import ch.egli.training.LogbookApplication;
import ch.egli.training.util.ExcelImporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO: Describe
 *
 * @author Christian Egli
 * @since 5/2/16.
 */
@Controller
public class ExcelController {

    @Autowired
    private ExcelImporter excelImporter;

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String provideUploadInfo(Model model) {
        File rootFolder = new File(LogbookApplication.ROOT);
        List<String> fileNames = Arrays.stream(rootFolder.listFiles())
                .map(f -> f.getName())
                .collect(Collectors.toList());

        model.addAttribute("files",
                Arrays.stream(rootFolder.listFiles())
                        .sorted(Comparator.comparingLong(f -> -1 * f.lastModified()))
                        .map(f -> f.getName())
                        .collect(Collectors.toList())
        );

        return "uploadForm";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/")
    public String handleFileUpload(@RequestParam("name") String name,
                                   @RequestParam("year") Integer year,
                                   @RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        if (name.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Field 'name' must contain a value!");
            return "redirect:/";
        }
        if ((year < 2014) || (year > 2050)) {
            redirectAttributes.addFlashAttribute("message", "Invalid value for 'year' (4-digit value is required!)");
            return "redirect:/";
        }

        InputStream inputStream = null;
        if (!file.isEmpty()) {
            try {
                inputStream = file.getInputStream();
                excelImporter.importWorkoutData(inputStream, year, name);
            }
            catch (Exception e) {
                redirectAttributes.addFlashAttribute("message", "Upload failed: " + e.getMessage());
            }
        }
        else {
            redirectAttributes.addFlashAttribute("message", "Cannot upload empty file");
        }

        return "redirect:/";
    }

}
