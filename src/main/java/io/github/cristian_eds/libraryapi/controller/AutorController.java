package io.github.cristian_eds.libraryapi.controller;

import io.github.cristian_eds.libraryapi.controller.dto.AutorDTO;
import io.github.cristian_eds.libraryapi.controller.dto.AutorResponseDTO;
import io.github.cristian_eds.libraryapi.controller.mappers.AutorMapper;
import io.github.cristian_eds.libraryapi.model.Autor;
import io.github.cristian_eds.libraryapi.service.AutorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/autores")
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Tag(name = "Autores")
@Slf4j
public class AutorController implements GenericController {

    private final AutorService autorService;
    private final AutorMapper autorMapper;

    @PostMapping
    @PreAuthorize("hasRole('GERENTE')")
    @Operation(summary = "Salvar")
    @ApiResponses({
            @ApiResponse(responseCode = "201",description = "Cadastrado com sucesso"),
            @ApiResponse(responseCode = "422",description = "Erro de validação"),
            @ApiResponse(responseCode = "409",description = "Autor já cadastrado")
    })
    public ResponseEntity<Object> salvar(@RequestBody @Valid AutorDTO autorDTO) {
        log.info("Cadastrando novo autor: {}", autorDTO.nome());
        Autor autor = autorMapper.toEntity(autorDTO);
        autorService.salvar(autor);

        URI location = gerarHeaderLocation(autor.getId());

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('OPERADOR','GERENTE')")
    @Operation(summary = "Buscar")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "Autor encontrado"),
            @ApiResponse(responseCode = "404",description = "Autor não encontrado")
    })
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
    @PreAuthorize("hasRole('GERENTE')")
    @Operation(summary = "Deletar")
    @ApiResponses({
            @ApiResponse(responseCode = "204",description = "Deletado com sucesso"),
            @ApiResponse(responseCode = "404",description = "Autor não encontrado"),
            @ApiResponse(responseCode = "400",description = "Autor possui livros cadastrados")

    })
    public ResponseEntity<Object> deletar(@PathVariable String id) {
        log.info("Deletando autor com id: {}", id);

        UUID idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.buscar(idAutor);

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        autorService.deletar(idAutor);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('OPERADOR','GERENTE')")
    @Operation(summary = "Pesquisar")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "Sucesso"),
    })
    public ResponseEntity<List<AutorResponseDTO>> pesquisar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {

        List<Autor> resultado = autorService.pesquisaByExample(nome, nacionalidade);
        List<AutorResponseDTO> lista = resultado.stream().map(autorMapper::toDTO).toList();

        return ResponseEntity.ok(lista);

    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('GERENTE')")
    @Operation(summary = "Atualizar")
    @ApiResponses({
            @ApiResponse(responseCode = "204",description = "Atualizado com sucesso"),
            @ApiResponse(responseCode = "404",description = "Autor não encontrado"),
            @ApiResponse(responseCode = "409",description = "Autor já cadastrado")
    })
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
