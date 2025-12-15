package com.auroralibrary.library.validation;

import org.springframework.stereotype.Component;
import com.auroralibrary.library.exception.ValidationException;

@Component
public class StringFormatter {

  public static String removeSpecialCharacters(String input) {
        if (input == null || input.isEmpty()) {
            throw new ValidationException("Dados inválidos, Nulos ou Vazios");
        }
        return input.replaceAll("[^0-9]", "");
    }
}
