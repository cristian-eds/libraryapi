package io.github.cristian_eds.libraryapi.controller;

import io.github.cristian_eds.libraryapi.controller.dto.CadastroLivroDTO;
import io.github.cristian_eds.libraryapi.controller.dto.ErroResposta;
import io.github.cristian_eds.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import io.github.cristian_eds.libraryapi.controller.mappers.LivroMapper;
import io.github.cristian_eds.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.cristian_eds.libraryapi.model.Livro;
import io.github.cristian_eds.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
public class LivroController implements GenericController {

    private final LivroService livroService;
    private final LivroMapper livroMapper;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid CadastroLivroDTO livroDto) {
        Livro livro = livroMapper.toEntity(livroDto);

        livroService.salvar(livro);
        URI location = gerarHeaderLocation(livro.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<ResultadoPesquisaLivroDTO> buscarDetalhes(@PathVariable("id") String id) {
        return livroService.buscar(UUID.fromString(id))
                .map(livro ->
                        {
                            var dto = livroMapper.toDTO(livro);
                            return ResponseEntity.ok(dto);
                        }
                ).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletar(@PathVariable("id") String id) {
        return livroService.buscar(UUID.fromString(id))
                .map(livro -> {
                    livroService.deletar(livro);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build()
                );
    }
}
