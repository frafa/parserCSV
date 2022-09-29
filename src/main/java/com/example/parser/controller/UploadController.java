package com.example.parser.controller;

import com.example.parser.model.Line;
import com.example.parser.validator.NameValidator;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
public class UploadController {

    @Autowired
    private NameValidator nameValidator;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/upload-csv-file")
    public String uploadCSVFile(@RequestParam("file") MultipartFile file, Model model) {

        // validate file
        if (file.isEmpty()) {
            model.addAttribute("message", "Please select a CSV file to upload.");
            model.addAttribute("status", false);
        } else {

            // parse CSV file to create a list of `User` objects
            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

                // create csv bean reader
                CsvToBean<Line> csvToBean = new CsvToBeanBuilder<Line>(reader)
                        .withType(Line.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();

                // convert `CsvToBean` object to list of lines
                List<Line> lines = csvToBean.parse().parallelStream().map(function).collect(Collectors.toList());

                // save lines list on model
                model.addAttribute("lines", lines);
                model.addAttribute("status", true);

            } catch (Exception ex) {
                model.addAttribute("message", "An error occurred while processing the CSV file: " + ex.getCause().getMessage());
                model.addAttribute("status", false);
            }
        }

        return "file-upload-status";
    }

    Function<Line, Line> function = l -> {
        StringJoiner sj = new StringJoiner("\n");
        try {
            nameValidator.validate(l.getName());
        } catch (Exception e) {
            sj.add(e.getMessage());
        }

        l.setNote(sj.toString());
        return l;
    };

}