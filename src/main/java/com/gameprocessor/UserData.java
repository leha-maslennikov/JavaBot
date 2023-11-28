package com.gameprocessor;

import java.util.LinkedList;
import java.util.List;

public class UserData {
    public Resource player;
    public List<Resource> rooms;
    public Resource combatFlag;
    public Resource roomId;

    public UserData(String userId, Creature player, int roomId, List<Room>rooms){
        this.player = ResourceManager.createResource(userId, player);
        this.roomId=ResourceManager.createResource(userId, roomId);
        this.combatFlag = ResourceManager.createResource(userId, false);
        this.rooms=new LinkedList<>();
        for (Room room : rooms) {
            this.rooms.add(ResourceManager.createResource(userId, room));
        }
    }

    public Resource getPlayer() {
        return player;
    }

    public Resource getRoom() {
        return this.rooms.get((int)roomId.get());
    }

    public Resource getCombatFlag() {
        return combatFlag;
    }
}
