package com.gameprocessor;

public class UserData {
    public Resource player;
    public Resource room;
    public Resource combatFlag;

    public UserData(String userId, Creature player, Room room){
        this.player = ResourceManager.createResource(userId, player);
        this.room = ResourceManager.createResource(userId, room);
        this.combatFlag = ResourceManager.createResource(userId, false);
    }

    public Resource getPlayer() {
        return player;
    }

    public Resource getRoom() {
        return room;
    }

    public Resource getCombatFlag() {
        return combatFlag;
    }
}
