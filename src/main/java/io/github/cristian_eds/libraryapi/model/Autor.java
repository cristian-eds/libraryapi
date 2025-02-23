package io.github.cristian_eds.libraryapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "autor")
@Getter
@Setter
@ToString(exclude = "livros")
@EntityListeners(AuditingEntityListener.class)
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 100, nullable = false)
    private String nome;

    @Column(nullable = false)
    private LocalDate dataNascimento;

    @Column(nullable = false, length = 50)
    private String nacionalidade;

    @OneToMany(mappedBy = "autor"
            //,cascade = CascadeType.ALL
    )
    private List<Livro> livros;

    @CreatedDate
    private LocalDateTime dataCadastro;

    @LastModifiedDate
    private LocalDateTime dataAtualizacao;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

}
