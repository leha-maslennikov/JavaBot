package com.gameprocessor;

import java.util.LinkedList;
import java.util.List;

public class Room {

    private final String name;
    private final List<Item> items;

    public Room(String name){
        this.name = name;
        this.items = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    public List<Item> getItems() {
        return items;
    }

    public static RoomBuilder builder(String name){
        return new RoomBuilder(name);
    }

    public static class RoomBuilder{
        private final Room room;

        public RoomBuilder(String name){
            this.room = new Room(name);
        }

        public RoomBuilder addItem(Item item){
            this.room.getItems().add(item);
            return this;
        }

        public Room build(){
            return this.room;
        }
    }
}
