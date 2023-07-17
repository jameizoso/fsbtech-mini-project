package com.app.fsbtechminiproject.repository;

import com.app.fsbtechminiproject.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, String> {
    Game findByName(String name);
    void deleteByName(String name);
}
