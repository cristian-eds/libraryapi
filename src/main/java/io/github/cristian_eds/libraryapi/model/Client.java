package io.github.cristian_eds.libraryapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String clientId;

    private String clientSecret;

    @Column(name = "redirect_uri")
    private String redirectURI;

    private String scope;

}
