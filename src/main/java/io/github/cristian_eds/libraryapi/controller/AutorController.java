package io.github.cristian_eds.libraryapi.controller;

import io.github.cristian_eds.libraryapi.controller.dto.AutorDTO;
import io.github.cristian_eds.libraryapi.controller.dto.AutorResponseDTO;
import io.github.cristian_eds.libraryapi.model.Autor;
import io.github.cristian_eds.libraryapi.service.AutorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/autores")
public class AutorController {

    private final AutorService autorService;

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody AutorDTO autor){
        Autor autorEntidade = autor.mapearParaAutor();
        Autor autorSalvo = autorService.salvar(autorEntidade);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(autorSalvo.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorResponseDTO> buscar(@PathVariable("id") String id) {
        UUID idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.buscar(idAutor);
        if (autorOptional.isPresent()) {
            AutorResponseDTO autor = AutorResponseDTO.mapearAutorParaAutorResponseDto(autorOptional.get());
            return ResponseEntity.ok(autor);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        UUID idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.buscar(idAutor);
        if(autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        autorService.deletar(idAutor);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<AutorResponseDTO>> pesquisar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {

        List<Autor> resultado = autorService.pesquisar(nome, nacionalidade);
        List<AutorResponseDTO> lista = resultado.stream().map(AutorResponseDTO::mapearAutorParaAutorResponseDto).toList();

        return ResponseEntity.ok(lista);

    }
}
