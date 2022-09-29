package com.example.parser.validator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NameValidator implements IValidator<String> {

    @Value("${validator.regexp}")
    private String REGEXP;

    @Override
    public Boolean isValid(String obj) {
        return obj.matches(REGEXP);
    }

    @Override
    public void validate(String obj) throws Exception {
        if (!isValid(obj))
        {
            throw new Exception("Nome non rispetta la regex: ^[A-Z,a-z,\\s]+$ .");
        }
    }
}
