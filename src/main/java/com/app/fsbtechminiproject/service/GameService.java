package com.app.fsbtechminiproject.service;

import com.app.fsbtechminiproject.model.Game;

import java.util.List;

public interface GameService {
    Game createGame(Game game);
    Game getGame(String name);
    List<Game> getAllGames();
    Game updateGame(Game game);
    void deleteGame(String name);
}
