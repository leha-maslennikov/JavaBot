package com.gameprocessor;

public class UserData {
    private Resource player;
    private Resource room;
    private Resource combatFlag;

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
