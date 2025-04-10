package br.edu.ifpb.diario.dto;

import br.edu.ifpb.diario.model.Role;

public record RegisterUserRequestDTO(String name, String email, String password, Role role) {
}
