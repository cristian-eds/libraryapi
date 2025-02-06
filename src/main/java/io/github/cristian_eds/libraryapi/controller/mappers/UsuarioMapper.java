package io.github.cristian_eds.libraryapi.controller.mappers;

import io.github.cristian_eds.libraryapi.controller.dto.UsuarioDTO;
import io.github.cristian_eds.libraryapi.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioDTO usuarioDTO);

    UsuarioDTO toDTO(Usuario usuario);
}
