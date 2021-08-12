package dev.kaira.model.constant;

public enum ItemType {
    ROCK("rock"), PAPER("paper"), SCISSORS("scissors");

    private String name;

    ItemType(String name) {
        this.name = name;
    }

    public static ItemType getType(String name) {
        for (ItemType type : values()) {
            if (type.name.equals(name)) {
                return type;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }


}
