package io.github.cristian_eds.libraryapi.controller;

import io.github.cristian_eds.libraryapi.controller.dto.CadastroLivroDTO;
import io.github.cristian_eds.libraryapi.controller.dto.ErroResposta;
import io.github.cristian_eds.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import io.github.cristian_eds.libraryapi.controller.mappers.LivroMapper;
import io.github.cristian_eds.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.cristian_eds.libraryapi.model.GeneroLivro;
import io.github.cristian_eds.libraryapi.model.Livro;
import io.github.cristian_eds.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
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

    @GetMapping
    public ResponseEntity<Page<ResultadoPesquisaLivroDTO>> pesquisar(
            @RequestParam(value = "isbn", required = false) String isbn,
            @RequestParam(value = "titulo", required = false) String titulo,
            @RequestParam(value = "genero", required = false) GeneroLivro genero,
            @RequestParam(value = "nome-autor", required = false) String nomeAutor,
            @RequestParam(value = "ano-publicacao", required = false) Integer anoPublicacao,
            @RequestParam(value = "pagina", defaultValue = "0", required = false) Integer pagina,
            @RequestParam(value = "tamanho-pagina", defaultValue = "10", required = false) Integer tamanhoPagina
    ) {
        var resultado = livroService.pesquisar(isbn, titulo, nomeAutor, genero, anoPublicacao, pagina, tamanhoPagina);
        var lista = resultado.map(livroMapper::toDTO);

        return ResponseEntity.ok(lista);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(@PathVariable("id") String id,
                                            @RequestBody @Valid CadastroLivroDTO dto) {
        return livroService.buscar(UUID.fromString(id))
                .map(livro ->
                        {
                            Livro entidadeAux = livroMapper.toEntity(dto);
                            livro.setAutor(entidadeAux.getAutor());
                            livro.setGenero(entidadeAux.getGenero());
                            livro.setIsbn(entidadeAux.getIsbn());
                            livro.setPreco(entidadeAux.getPreco());
                            livro.setTitulo(entidadeAux.getTitulo());
                            livro.setDataPublicacao(entidadeAux.getDataPublicacao());

                            livroService.atualizar(livro);

                            return ResponseEntity.noContent().build();
                        }
                ).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
