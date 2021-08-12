package dev.kaira.model;

import dev.kaira.model.constant.GameStatusType;
import dev.kaira.model.constant.ItemType;
import dev.kaira.service.exception.GameException;

import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class Game {
    private String id;
    private GameStatusType status = GameStatusType.WAITING_START;
    private NavigableMap<String, Player> players = new TreeMap();
    private Map<String, ItemType> hits = new HashMap<>();
    private String turn;
    private IGameWinner winner;

    public Game(String id) {
        this.id = id;
    }

    public void addPlayer(Player player) throws GameException {
        String token = player.getToken();
        if (!players.containsKey(token)){
                players.put(token, player);
        } else {
            throw new GameException("the user " + player.getName() + " exist");
        }
    }

    public void addHit(String token, ItemType item) {
        hits.put(token, item);
    }

    public String getId() {
        return id;
    }

    public GameStatusType getStatus() {
        return status;
    }

    public NavigableMap<String, Player> getPlayers() {
        return players;
    }

    public Map<String, ItemType> getHits() {
        return hits;
    }

    public String getTurn() {
        return turn;
    }

    public void setTurn(String turn) {
        this.turn = turn;
    }

    public void setWinner(IGameWinner winner) {
        this.winner = winner;
    }

    public IGameWinner getWinner() {
        return winner;
    }

    public void setStatus(GameStatusType gameStatusType) {
        status = gameStatusType;
    }

    public String toString() {
        return status.name();
    }

    public void reset() {
        setStatus(GameStatusType.WAITING_START);
        players.clear();
        hits.clear();
    }

}
