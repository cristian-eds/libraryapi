package io.github.cristian_eds.libraryapi.repository.specs;

import io.github.cristian_eds.libraryapi.model.GeneroLivro;
import io.github.cristian_eds.libraryapi.model.Livro;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;


public class LivroSpecs {

    public static Specification<Livro> isbnEqual(String isbn) {
        return (root, query, cb) -> cb.equal(root.get("isbn"), isbn);
    }

    public static Specification<Livro> tituloLike(String titulo) {
        return
                (root, query, cb) ->
                        cb.like(cb.upper(root.get("titulo")), "%" + titulo.toUpperCase() + "%");
    }

    public static Specification<Livro> generoEqual(GeneroLivro genero) {
        return
                (root, query, cb) ->
                        cb.equal(root.get("genero"), genero);
    }

    public static Specification<Livro> anoPublicacaoEqual(Integer anoPublicacoa) {
        return
                (root, query, cb) ->
                        cb.equal(
                                cb.function("to_char", String.class, root.get("dataPublicacao"),
                                        cb.literal("YYYY")),
                                anoPublicacoa.toString());
    }

    public static Specification<Livro> nomeAutorLike(String nomeAutor) {
        return
                (root, query, cb) ->
                {
                    Join<Object, Object> joinAutor = root.join("autor", JoinType.LEFT);
                    return cb.like(cb.upper(joinAutor.get("nome")),"%"+nomeAutor.toUpperCase()+"%");

                    //return cb.like(cb.upper(root.get("autor").get("nome")), "%" + nomeAutor.toUpperCase() + "%");
                };
    }

}
