package io.github.cristian_eds.libraryapi.controller.dto;

public record ClientDTO(
        String clientId,
        String clientSecret,
        String redirectURI,
        String scope
) {
}
