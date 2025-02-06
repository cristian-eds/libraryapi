package io.github.cristian_eds.libraryapi.controller;

import io.github.cristian_eds.libraryapi.controller.dto.UsuarioDTO;
import io.github.cristian_eds.libraryapi.controller.mappers.UsuarioMapper;
import io.github.cristian_eds.libraryapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@RequestBody UsuarioDTO usuarioDTO) {
        var usuario = usuarioMapper.toEntity(usuarioDTO);
        System.out.println("Controller: "+usuario);
        usuarioService.salvar(usuario);
    }

}
