package br.edu.ifpb.diario.exceptions;

public class UserAlreadyRegisteredException extends RuntimeException {
    public UserAlreadyRegisteredException() {
        super("Usuário já registrado!");
    }
}