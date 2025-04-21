package br.edu.ifpb.diario.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.edu.ifpb.diario.exceptions.InvalidCredentialsException;
import br.edu.ifpb.diario.exceptions.InvalidEmailException;
import br.edu.ifpb.diario.exceptions.PostNotFoundException;
import br.edu.ifpb.diario.exceptions.UnauthorizedAccessException;
import br.edu.ifpb.diario.exceptions.UserAlreadyRegisteredException;
import br.edu.ifpb.diario.exceptions.UserNotFoundException;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class ExceptionsHandler {

    // Conflito
    @ExceptionHandler(UserAlreadyRegisteredException.class)
    public ResponseEntity<String> handlePatientAlreadyRegistered(UserAlreadyRegisteredException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    // Não Encontrado
    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<String> handlePatientNotFound(PostNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleMedicNotFound(UserNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    // Falta de autorização
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<String> handleInvalidCredentials(InvalidCredentialsException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({
            UnauthorizedAccessException.class,
            HttpClientErrorException.Forbidden.class
    })
    public ResponseEntity<String> handleUnauthorizedAccess(UnauthorizedAccessException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    // Formato de dado inválido
    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<String> handleInvalidEmail(InvalidEmailException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }
}