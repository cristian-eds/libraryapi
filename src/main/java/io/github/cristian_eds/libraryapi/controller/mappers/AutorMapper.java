package io.github.cristian_eds.libraryapi.controller.mappers;

import io.github.cristian_eds.libraryapi.controller.dto.AutorDTO;
import io.github.cristian_eds.libraryapi.model.Autor;
import io.github.cristian_eds.libraryapi.controller.dto.AutorResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    Autor toEntity(AutorDTO autorDTO);

    AutorResponseDTO toDTO(Autor autor);
}
