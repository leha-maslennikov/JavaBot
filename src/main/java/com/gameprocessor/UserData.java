package com.gameprocessor;

public class UserData {
    public Resource player;
    public Resource room;
    public boolean combatFlag;
    public Resource levelManager;

    public UserData(){}

    public UserData(String userId, Creature player, Room room, LevelManager levelManager){
        this.player = ResourceManager.createResource(userId, player);
        this.room = ResourceManager.createResource(userId, room);
        this.levelManager =  ResourceManager.createResource(userId, levelManager);
        this.combatFlag = false;
    }

    public Resource getPlayer() {
        return player;
    }

    public Resource getRoom() {
        return room;
    }

    public Resource getLevelManager() {
        return levelManager;
    }

    public boolean getCombatFlag() {
        return combatFlag;
    }
}
