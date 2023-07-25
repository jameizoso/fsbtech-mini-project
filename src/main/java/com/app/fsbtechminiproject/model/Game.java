package com.app.fsbtechminiproject.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "The game name can't be empty.")
    @Column(unique = true)
    private String name;

    private Date creationDate;

    private boolean active;

    @Version
    private int version;
}
