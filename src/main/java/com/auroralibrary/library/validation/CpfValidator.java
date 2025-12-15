package com.auroralibrary.library.validation;

import com.auroralibrary.library.dto.request.ClientCreateRequest;
import com.auroralibrary.library.model.Client;
import com.auroralibrary.library.exception.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class CpfValidator implements InterfaceValidationClient {

  @Override
  public void validateCreate(ClientCreateRequest client) {
      final String cpfLimpo = client.cpf().replaceAll("[^0-9]", "");
      validarTamanho(cpfLimpo);
      validarNumerosRepetidos(cpfLimpo);
      if (!validarDigitosVerificadores(cpfLimpo)) {
        throw new ValidationException("CPF inválido");
      }
  }
  
  private static void validarTamanho(String cpf) {
    if (cpf.length() != 11) {
      throw new ValidationException("CPF inválido: deve conter 11 dígitos");
    }
  }

  private static void validarNumerosRepetidos(String cpf) {
    if (cpf.matches("(\\d)\\1{10}")) {
      throw new ValidationException("CPF inválido: não pode conter todos números iguais");
    }
  }

  private static boolean validarDigitosVerificadores(String cpf) {
    // Calcula primeiro dígito verificador
    int soma = 0;
    for (int i = 0; i < 9; i++) {
      soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
    }
    int primeiroDigito = 11 - (soma % 11);
    if (primeiroDigito > 9) {
      primeiroDigito = 0;
    }

    // Calcula segundo dígito verificador
    soma = 0;
    for (int i = 0; i < 10; i++) {
      soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
    }
    int segundoDigito = 11 - (soma % 11);
    if (segundoDigito > 9) {
      segundoDigito = 0;
    }

    // Verifica dígitos
    if (Character.getNumericValue(cpf.charAt(9)) != primeiroDigito ||
        Character.getNumericValue(cpf.charAt(10)) != segundoDigito) {
      throw new ValidationException("CPF inválido: dígitos verificadores incorretos");
    }

    return true;
  }


  @Override
  public void validateUpdate(Client client, Long id) {

  }
}
