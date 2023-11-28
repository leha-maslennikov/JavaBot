package com.gameprocessor;

public class Door extends Item {
    public Resource room;
    public Resource roomId;

    public Door(String name, String description, Room room, String userId){
        super(name, description);
        this.room = ResourceManager.createResource(userId, room);
    }

    public Door(String name, String description, int roomId,String userId){
        super(name, description);
        this.roomId=ResourceManager.createResource(userId,roomId);
    }

    public Resource getRoomId() {
        return roomId;
    }

    public Resource getRoom() {
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
