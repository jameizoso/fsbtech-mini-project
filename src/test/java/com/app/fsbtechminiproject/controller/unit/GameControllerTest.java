package com.app.fsbtechminiproject.controller.unit;

import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.app.fsbtechminiproject.controller.GameController;
import com.app.fsbtechminiproject.exception.ExceptionHandlerController;
import com.app.fsbtechminiproject.exception.NotFoundException;
import com.app.fsbtechminiproject.model.Game;
import com.app.fsbtechminiproject.service.GameService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ExtendWith(MockitoExtension.class)
public class GameControllerTest {

    @Mock
    private GameService gameService;

    @InjectMocks
    private GameController gameController;

    private MockMvc mockMvc;

    private static final Logger logger = LoggerFactory.getLogger(GameControllerTest.class);

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(gameController)
                .setControllerAdvice(new ExceptionHandlerController())
                .build();
    }

    @Test
    public void testAddGame() throws Exception {

        Game game = new Game(1l,"game1", new Date(),true, 0 );

        // Mock the behavior of the GameService
        when(gameService.createGame(any(Game.class))).thenReturn(game);

        mockMvc.perform(post("/api/v1/game/addGame")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(game)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("game1"))
                .andExpect(jsonPath("$.active").value(true));

        // Verify method has been called
        verify(gameService, times(1)).createGame(any(Game.class));
    }

    @Test
    public void testAddGame_DataIntegrityViolationException() throws Exception {

        Game game = new Game(1l,"game1", new Date(),true, 0 );

        // Mock the behavior of the GameService to throw DataIntegrityViolationException
        doThrow(DataIntegrityViolationException.class).when(gameService).createGame(any(Game.class));

        try {
            mockMvc.perform(post("/api/v1/game/addGame")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(game)))
                    .andExpect(status().isInternalServerError());
        } catch (NestedServletException e) {
            // Verify that the exception was logged
            logger.error("DataIntegrityViolationException was thrown: {}", e.getMessage());
        }
    }

    @Test
    public void testGetGame_Success() throws Exception {
        String gameName = "TestGame";
        Game game = new Game(1l,gameName, new Date(), true, 0);

        // Mock the behavior of the GameService
        when(gameService.getGame(eq(gameName))).thenReturn(game);

        mockMvc.perform(get("/api/v1/game/{name}", gameName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(gameName))
                .andExpect(jsonPath("$.active").value(true));

        // Verify method has been called
        verify(gameService).getGame(eq(gameName));
    }

    @Test
    public void testGetGame_NotFound() throws Exception {
        String gameName = "NonExistentGame";
        String errorMessage = "Game with name " + gameName + " not found.";

        // Mock the behavior of the GameService to throw the exception
        doThrow(new NotFoundException(errorMessage)).when(gameService).getGame(eq(gameName));

        try {
            mockMvc.perform(get("/api/v1/game/{name}", gameName)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isInternalServerError())
                    .andExpect(content().string(errorMessage));
        } catch (NestedServletException e) {
            logger.error("NotFoundException was thrown: {}", e.getMessage());
        }

        // Verify method has been called
        verify(gameService).getGame(eq(gameName));
    }

    @Test
    public void testUpdateGame_Success() throws Exception {

        Game game = new Game(1l,"game1", new Date(), true, 0);

        // Performing the request
        mockMvc.perform(put("/api/v1/game/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(game)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Game updated successfully."));

        // Verify method has been called
        verify(gameService).updateGame(any(Game.class));
    }

    @Test
    public void testUpdateGame_NotFound() throws Exception {

        Game game = new Game(1l,"game1", new Date(),true, 0 );

        String errorMessage = "Game with name " + game.getName() + " doesn't exist on the data base.";

        // Mock the behavior of the GameService to throw the exception
        doThrow(new NotFoundException(errorMessage)).when(gameService).updateGame(any(Game.class));

        // Performing the request
        mockMvc.perform(put("/api/v1/game/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(game)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(errorMessage));

        // Verify method has been called
        verify(gameService).updateGame(any(Game.class));
    }

    @Test
    public void testDeleteGame_Success() throws Exception {
        String gameName = "TestGame";

        doNothing().when(gameService).deleteGame(gameName);

        // Performing the request
        mockMvc.perform(delete("/api/v1/game/delete/{name}", gameName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Game deleted successfully."));

        // Verify method has been called
        verify(gameService).deleteGame(gameName);
    }

    @Test
    public void testDeleteGame_NotFound() throws Exception {
        String gameName = "NonExistentGame";

        String errorMessage = "Game with name game1 doesn't exist on the data base.";

        // Mock the behavior of the GameService to throw the exception
        doThrow(new NotFoundException(errorMessage)).when(gameService).deleteGame(gameName);

        // Performing the request
        mockMvc.perform(delete("/api/v1/game/delete/{name}", gameName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(errorMessage));

        // Verify method has been called
        verify(gameService).deleteGame(gameName);
    }

    @Test
    public void testGetAll_Success() throws Exception {
        // Create a list of games
        List<Game> games = new ArrayList<>();
        games.add(new Game(1l,"game1", new Date(),true, 0 ));
        games.add(new Game(2l,"game2", new Date(),false, 1 ));
        games.add(new Game(3l,"game3", new Date(),true, 0 ));

        // Mock the behavior of the GameService
        when(gameService.getAllGames()).thenReturn(games);

        // Performing the request
        mockMvc.perform(get("/api/v1/game/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].name").value("game1"))
                .andExpect(jsonPath("$[1].name").value("game2"));

        // Verify method has been called
        verify(gameService).getAllGames();
    }

    @Test
    void testGetAll_WhenNotFoundException() throws Exception {
        // Mocking the service method to throw NotFoundException
        when(gameService.getAllGames()).thenThrow(new NotFoundException("Games not found"));

        // Performing the request
        mockMvc.perform(get("/api/v1/game/all"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$").isString())
                .andExpect(jsonPath("$").value("Games not found"));

    }

    private static String asJsonString(final Object obj) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}