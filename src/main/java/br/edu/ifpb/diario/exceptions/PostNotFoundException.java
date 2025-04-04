package br.edu.ifpb.diario.exceptions;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException() {
        super("Postagem ou história não encontrado!");
    }
}