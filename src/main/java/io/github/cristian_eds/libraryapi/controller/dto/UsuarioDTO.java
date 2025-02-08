package io.github.cristian_eds.libraryapi.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record UsuarioDTO(
        @NotBlank(message = "obrigatorio")
        String login,
        @NotBlank(message = "obrigatoria")
        String senha,
        List<String> roles,
        @Email(message = "invalido")
        String email) {
}
