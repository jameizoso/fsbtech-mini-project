package com.app.fsbtechminiproject.controller.integration;

import com.app.fsbtechminiproject.model.Game;
import com.app.fsbtechminiproject.repository.GameRepository;
import com.app.fsbtechminiproject.service.GameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class GameServiceIntegrationTest {
    @Autowired
    private GameService gameService;

    @Autowired
    private GameRepository repository;

    @Test
    public void testDeleteGameWithOptimisticLocking() throws InterruptedException {
        // Create a game
        Game game = new Game(1l, "game1", new Date(), true, 0);
        repository.save(game);

        // Start two threads that will try to delete the game at the same time
        Thread thread1 = new Thread(() -> {
            try {
                // This will throw the ObjectOptimisticLockingFailureException
                gameService.deleteGame(game.getName());
            } catch (ObjectOptimisticLockingFailureException e) {
                // Do something with the exception
                assertEquals(e.getMessage(), "Object optimistic locking failure");
            }

        });
        Thread thread2 = new Thread(() -> {
            try {
                // This will throw the ObjectOptimisticLockingFailureException
                gameService.deleteGame(game.getName());
            } catch (ObjectOptimisticLockingFailureException e) {
                // Do something with the exception
                assertEquals(e.getMessage(), "Object optimistic locking failure");
            }

        });

        thread1.start();
        thread2.start();

        // Wait for the threads to finish
        thread1.join();
        thread2.join();
}
}