package dev.kaira.service;

import dev.kaira.model.Game;
import dev.kaira.model.Player;
import dev.kaira.model.constant.GameStatusType;
import dev.kaira.service.exception.GameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {

    private GameService service;

    @BeforeEach
    void init() throws IOException {
        service = new GameService();
    }

    @Test
    void doStart() throws GameException {
        service.doStart();
        Map result =  service.getInfo();
        assertEquals(GameStatusType.PLAYING, ((List)result.get("status")).get(0));
        assertNull(result.get("winner"));
        assertNotNull(result.get("turn"));
    }

    @Test
    void play() throws GameException {
        service.doStart();
        Game game = GameService.getGame();
        Map.Entry<String, Player> player = game.getPlayers().firstEntry();
        service.play(player.getKey(), "rock");
        Map result =  service.getInfo();
        assertEquals(GameStatusType.PLAYING, ((List)result.get("status")).get(0));
        assertNull(result.get("winner"));
        assertEquals(game.getPlayers().lastEntry().getKey(),result.get("turn"));
    }

    @Test
    void reset() {
        service.reset();
        Map result =  service.getInfo();
        assertEquals(GameStatusType.WAITING_START,((List)result.get("status")).get(0));
        assertNull(result.get("winner"));

    }

    @Test
    void getInfo() {
        Map result = service.getInfo();
        assertEquals(0, result.size());
    }
}