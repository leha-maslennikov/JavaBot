package com.gameprocessor;

public class Door implements Sendable{
    private final String name;
    private final String description;
    private final Room room;

    public Door(String name, String description, Room room){
        this.name = name;
        this.description = description;
        this.room = room;
    }

    public Room getRoom() {
        return room;
    }

    /**
     * @return текст, который будет отправлен пользователю вместе с объектом
     */
    @Override
    public String getShortText() {
        return this.name;
    }

    /**
     * @return текст, который будет отправлен пользователю, после того, как он выберет данный объект
     */
    @Override
    public String getLongText() {
        return description;
    }

    /**
     * @return действия, которые можно проделать с объектом, после его выбора, должны быть реализованы в GameProcessor
     */
    @Override
    public Actions getActions() {
        return Actions.builder()
                .addAction("open")
                .build();
    }
}
