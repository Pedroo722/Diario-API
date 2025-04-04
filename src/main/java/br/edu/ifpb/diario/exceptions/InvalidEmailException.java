package br.edu.ifpb.diario.exceptions;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException() {
        super("Formato de email inválidas!");
    }
}