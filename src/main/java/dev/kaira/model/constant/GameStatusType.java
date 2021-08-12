package dev.kaira.model.constant;

public enum GameStatusType {
    WAITING_START("waiting start"),  PLAYING("playing"), OVER("game over");

    private String alias;

    GameStatusType(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }
}
