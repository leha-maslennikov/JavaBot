package com.gameprocessor.user;

import com.gameprocessor.resourcemanager.Resource;
import com.gameprocessor.resourcemanager.ResourceManager;
import com.gameprocessor.entities.creatures.Creature;
import com.gameprocessor.entities.Room;

public class UserData {
    public Resource player;
    public Resource room;
    public boolean combatFlag;

    public UserData(){}

    public UserData(String userId, Creature player, Room room){
        this.player = ResourceManager.createResource(userId, player);
        this.room = ResourceManager.createResource(userId, room);
        this.combatFlag = false;
    }

    public Resource getPlayer() {
        return player;
    }

    public Resource getRoom() {
        return room;
    }

    public boolean getCombatFlag() {
        return combatFlag;
    }
}
