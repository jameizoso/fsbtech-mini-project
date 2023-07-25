package com.app.fsbtechminiproject.controller;

import com.app.fsbtechminiproject.exception.NotFoundException;
import com.app.fsbtechminiproject.model.Game;
import com.app.fsbtechminiproject.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/v1/game")
public class GameController {
    private static final Logger logger = LoggerFactory.getLogger(GameController.class);

    private final GameService service;

    @Autowired
    public GameController(GameService service) {
        this.service = service;
    }


    @PostMapping("/addGame")
    public ResponseEntity<?> addGame(@Valid @RequestBody Game game) {
        try {
            logger.info("Executing addGame Method.");

            return new ResponseEntity<>(service.createGame(game), HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            logger.error("Throw DataIntegrityViolationException.");

            String errorMsg = e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMsg);
        }
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getGame(@PathVariable String name) {
        try {
            logger.info("Executing getGame Method.");

            return new ResponseEntity<>(service.getGame(name), HttpStatus.OK);
        } catch (NotFoundException e) {
            logger.error("NotFoundException.");

            String errorMsg = e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMsg);
        }

    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {

        logger.info("Executing getAll Method.");
        try {
            return new ResponseEntity<>(service.getAllGames(), HttpStatus.OK);
        } catch (NotFoundException ex) {
            logger.error("NotFoundException.");

            String errorMsg = ex.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMsg);
        }

    }


    @PutMapping("/update")
    public ResponseEntity<?> updateGame(@Valid @RequestBody Game game) {
        try {
            logger.info("Executing updateGame Method.");

            service.updateGame(game);
            return ResponseEntity.ok("Game updated successfully.");
        } catch (NotFoundException e) {
            logger.error("NotFoundException.");

            String errorMsg = e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMsg);
        }
    }

    @DeleteMapping("/delete/{name}")
    public ResponseEntity<?> deleteGame(@PathVariable String name) {
        try {
            logger.info("Executing deleteGame Method.");

            service.deleteGame(name);
            return ResponseEntity.ok("Game deleted successfully.");
        } catch (NotFoundException e) {
            logger.error("NotFoundException.");

            String errorMsg = e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMsg);
        }
    }

}
