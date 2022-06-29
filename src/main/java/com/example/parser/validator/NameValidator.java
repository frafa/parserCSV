package com.example.parser.validator;

public class NameValidator implements IValidator<String> {

    private final static String REGEXP ="^[A-Za-z\\s]+$";

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
