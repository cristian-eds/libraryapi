package io.github.cristian_eds.libraryapi.controller;

import io.github.cristian_eds.libraryapi.controller.dto.AutorDTO;
import io.github.cristian_eds.libraryapi.controller.dto.AutorResponseDTO;
import io.github.cristian_eds.libraryapi.controller.dto.ErroResposta;
import io.github.cristian_eds.libraryapi.controller.mappers.AutorMapper;
import io.github.cristian_eds.libraryapi.exceptions.OperacaoNaoPermitida;
import io.github.cristian_eds.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.cristian_eds.libraryapi.model.Autor;
import io.github.cristian_eds.libraryapi.service.AutorService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/autores")
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class AutorController implements GenericController {

    private final AutorService autorService;
    private final AutorMapper autorMapper;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid AutorDTO autorDTO) {
        Autor autor = autorMapper.toEntity(autorDTO);
        autorService.salvar(autor);

        URI location = gerarHeaderLocation(autor.getId());

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorResponseDTO> buscar(@PathVariable("id") String id) {
        UUID idAutor = UUID.fromString(id);

        return autorService.buscar(idAutor)
                .map(autor -> {
                    AutorResponseDTO dto = autorMapper.toDTO(autor);
                    return ResponseEntity.ok(dto);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletar(@PathVariable String id) {
        UUID idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.buscar(idAutor);

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        autorService.deletar(idAutor);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<AutorResponseDTO>> pesquisar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {

        List<Autor> resultado = autorService.pesquisaByExample(nome, nacionalidade);
        List<AutorResponseDTO> lista = resultado.stream().map(autorMapper::toDTO).toList();

        return ResponseEntity.ok(lista);

    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(@PathVariable String id, @RequestBody @Valid AutorDTO autorDto) {
        UUID idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.buscar(idAutor);

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var autor = autorOptional.get();

        autor.setNome(autorDto.nome());
        autor.setDataNascimento(autorDto.dataNascimento());
        autor.setNacionalidade(autorDto.nacionalidade());

        autorService.atualizar(autor);

        return ResponseEntity.noContent().build();
    }
}
