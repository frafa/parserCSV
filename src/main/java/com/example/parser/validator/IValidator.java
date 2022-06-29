package com.example.parser.validator;

public interface IValidator<T> {

    Boolean isValid(T obj);

    default void validate(T obj) throws Exception {
        if (!isValid(obj)){
            throw new Exception("Field not valid");
        }
    }
}
