package dev.kaira.service;

import dev.kaira.model.DrawAnnouncement;
import dev.kaira.model.Game;
import dev.kaira.model.Player;
import dev.kaira.model.constant.GameStatusType;
import dev.kaira.model.constant.ItemType;
import dev.kaira.service.exception.GameException;
import dev.kaira.util.NameGenerator;
import dev.kaira.util.StringUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

public class GameService {
    private static final String FIRST_NAME_SOURCE = "Roman.txt";
    private static Game game;
    Logger logger = Logger.getLogger(GameService.class.getName());
    NameGenerator nameGeneratorFirstName;

    public GameService() throws IOException {
        nameGeneratorFirstName = new NameGenerator(FIRST_NAME_SOURCE);
    }

    public static Game getGame() {
        return game;
    }

    public Map<String, Object> doStart() throws  GameException {
        Map<String, Object> answer = new HashMap<>();
        initGame(false);
        if (doIntroduction()) {
            //since the key is random generated sequence the turn is going to be random as well
            game.setTurn(game.getPlayers().firstEntry().getKey());
            game.setStatus(GameStatusType.PLAYING);
            answer.put("turn", game.getTurn());
        }
        logger.info(game.getId() + " has been started");
        return answer;
    }

    public Map<String, Object> play(String token, String item) throws GameException {
        logger.info("\"" + token + "\" tossed");
        Map<String, Object> answer = new HashMap<>();
        if (game != null && game.getStatus() == GameStatusType.PLAYING) {
            if (game.getPlayers().containsKey(token)) {
                game.addHit(token, ItemType.getType(item));
                analyze();
                if (game.getStatus() == GameStatusType.PLAYING) {
                    game.setTurn(game.getPlayers().lastKey());
                }
            } else {
                throw new GameException("the token is incorrect");
            }
        } else {
            throw new GameException("the game didn't started");
        }
        answer.put("status", game.getStatus());
        return answer;
    }

    public Map<String, Object> reset() {
        initGame(true);
        Map<String, Object> answer = new HashMap<>();
        logger.info("game was stopped");
        answer.put("status", game.getStatus());
        return answer;
    }

    public Map<String, Object> getInfo() {
        Map<String, Object> answer = new HashMap<>();
        if (game != null) {
            answer.put("status", Arrays.asList(game.getStatus(), game.getStatus().getAlias()));
            answer.put("players", game.getPlayers().values());
            answer.put("hits", game.getHits().values());
            answer.put("winner", game.getWinner());
            answer.put("turn", game.getTurn());
        }
        return answer;
    }

    private boolean doIntroduction() throws GameException {
        logger.info("introducing players");

        if (game.getStatus() == GameStatusType.OVER || game.getStatus() == GameStatusType.WAITING_START) {
            String firstPlayerName = nameGeneratorFirstName.compose(3);
            game.addPlayer(new Player(firstPlayerName, game.getId() + firstPlayerName.hashCode()));
            String secondPlayerName = nameGeneratorFirstName.compose(3);
            game.addPlayer(new Player(secondPlayerName, game.getId() + secondPlayerName.hashCode()));
            return true;
        }
        return false;
    }

    private void initGame(boolean force) {
        if (force || game == null) {
            game = new Game(StringUtil.getRndText(10));
        } else {
            game.setStatus(GameStatusType.WAITING_START);
            game.setTurn(null);
            game.getPlayers().clear();
            game.getHits().clear();
            game.setWinner(null);
        }
    }


    private void analyze() {
        Map<String, ItemType> hits = game.getHits();
        Map<String, Player> players = game.getPlayers();
        if (hits.keySet().size() >= 2) {
            Iterator<String> it = hits.keySet().iterator();
            String key1 = it.next();
            String key2 = it.next();
            if (hits.get(key1) == hits.get(key2)) {
                game.setWinner(new DrawAnnouncement());
            } else {
                if (hits.get(key1) == ItemType.PAPER) {
                    if (hits.get(key2) == ItemType.SCISSORS) {
                        game.setWinner(players.get(key2));
                    } else if (hits.get(key2) == ItemType.ROCK) {
                        game.setWinner(players.get(key1));
                    }
                } else if (hits.get(key1) == ItemType.SCISSORS) {
                    if (hits.get(key2) == ItemType.ROCK) {
                        game.setWinner(players.get(key2));
                    } else if (hits.get(key2) == ItemType.PAPER) {
                        game.setWinner(players.get(key1));
                    }
                } else if (hits.get(key1) == ItemType.ROCK) {
                    if (hits.get(key2) == ItemType.PAPER) {
                        game.setWinner(players.get(key2));
                    } else if (hits.get(key2) == ItemType.SCISSORS) {
                        game.setWinner(players.get(key1));
                    }
                }
            }
            game.setTurn(null);
            game.setStatus(GameStatusType.OVER);
        }
    }

}
