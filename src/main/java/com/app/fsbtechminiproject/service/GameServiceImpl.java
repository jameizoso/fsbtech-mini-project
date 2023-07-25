package com.app.fsbtechminiproject.service;

import com.app.fsbtechminiproject.exception.NotFoundException;
import com.app.fsbtechminiproject.model.Game;
import com.app.fsbtechminiproject.repository.GameRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository repository;

    public GameServiceImpl(GameRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = {"gameCache", "games"}, allEntries = true)
    public Game createGame(Game game) {
        Optional<Game> gameExists = Optional.ofNullable(repository.findByName(game.getName()));
        if (gameExists.isPresent()) {
            throw new DataIntegrityViolationException("This game already exists");
        }

        try {
            return repository.save(game);
        } catch (ObjectOptimisticLockingFailureException ex) {
            throw new DataIntegrityViolationException("The update operation could not be completed due to a concurrency conflict");
        }

    }

    @Override
    @Cacheable(cacheNames = {"gameCache"}, key = "#name")
    public Game getGame(String name) {
        Optional<Game> game = Optional.ofNullable(repository.findByName(name));
        return game.orElseThrow(() -> new NotFoundException("Game with name " + name + " not found."));
    }

    @Override
    @Cacheable(cacheNames = {"gameCache", "games"})
    public List<Game> getAllGames() {
        if (repository.findAll().isEmpty()) {
            throw new NotFoundException("No results.");
        } else {
            return repository.findAll();
        }
    }

    @Override
    @CacheEvict(cacheNames = {"gameCache", "games"}, allEntries = true)
    @Transactional
    public Game updateGame(Game game) {
        if (!repository.existsByName(game.getName())) {
            throw new NotFoundException("Game with name " + game.getName() + " doesn't exist on the data base.");
        }

        Game gameUpdate = repository.findByName(game.getName());
        gameUpdate.setName(game.getName());
        gameUpdate.setCreationDate(game.getCreationDate());
        gameUpdate.setActive(game.isActive());

        try{
            return repository.save(gameUpdate);
        }catch (ObjectOptimisticLockingFailureException ex) {
            throw new DataIntegrityViolationException("The update operation could not be completed due to a concurrency conflict");
        }

    }

    @Override
    @CacheEvict(cacheNames = {"gameCache", "games"}, allEntries = true)
    @Transactional
    public void deleteGame(String name) {
        if (!repository.existsByName(name)) {
            throw new NotFoundException("Game with name " + name + " doesn't exist on the data base.");
        }
        try {
            repository.deleteByName(name);
        } catch (ObjectOptimisticLockingFailureException ex) {
            throw new DataIntegrityViolationException("The update operation could not be completed due to a concurrency conflict");
        }
    }


    public static void waitInsecons(int seconds) { //for testing proposes
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
