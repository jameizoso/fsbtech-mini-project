package com.app.fsbtechminiproject.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Game {
    @Id
    @Column(unique = true)
    private String name;

    private Date creationDate;

    private boolean active;

    @Version
    private int version;
}
