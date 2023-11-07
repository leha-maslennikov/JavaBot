package com.gameprocessor;

/**
 * Игровой предмет
 */
public class Item implements Sendable{
    private final String name;
    private final String description;

    public Item(String name, String description){
        this.name = name;
        this.description = description;
    }
    @Override
    public String getShortText() {
        return name;
    }

    @Override
    public String getLongText() {
        return name+"\n"+description;
    }

    @Override
    public Actions getActions() {
        return Actions.builder()
                .addAction("take")
                .addAction("throw")
                .build();
    }
}
