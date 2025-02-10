package io.github.cristian_eds.libraryapi.controller.mappers;

import io.github.cristian_eds.libraryapi.controller.dto.ClientDTO;
import io.github.cristian_eds.libraryapi.model.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    Client toEntity(ClientDTO clientDTO);
}
