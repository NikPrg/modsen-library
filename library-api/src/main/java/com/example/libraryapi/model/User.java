package com.example.libraryapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Table(name = "users")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class User {

    @Id
    @SequenceGenerator(name = "user_generator", allocationSize = 5)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
    private Long id;
    private UUID externalId;
    private String name;
    private String password;
    private String email;
    //todo: change to enum
    private String roles;
    //createdAt

}