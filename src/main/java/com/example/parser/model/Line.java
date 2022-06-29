package com.example.parser.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.validators.MustMatchRegexExpression;
import com.opencsv.bean.validators.PreAssignmentValidator;
import lombok.Data;

import java.util.ArrayList;
import java.util.StringJoiner;

@Data
public class Line {

    @CsvBindByName
    private long id;

    //@PreAssignmentValidator(validator = MustMatchRegexExpression.class, paramString = "[A-Z,a-z]")
    @CsvBindByName
    private String name;

    @CsvBindByName
    private String email;

    @CsvBindByName
    private String country;

    @CsvBindByName
    private int age;

    private String note;
}