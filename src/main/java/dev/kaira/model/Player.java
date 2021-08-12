package dev.kaira.model;

public class Player implements IGameWinner {
    private String name;
    private String token;

    public Player(String name, String token) {
        this.name = name;
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public String getToken() {
        return token;
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof  Player)) {
            return false;
        }

        Player playerForCompare = (Player) obj;
        if (name.equals(playerForCompare.getName()) && name.equals(playerForCompare.getToken())) {
            return  true;
        }
        return false;
    }
}
