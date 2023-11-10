package com.gameprocessor;

import java.util.LinkedList;
import java.util.List;

public class Room {

    private final String name;
    private final List<Item> items;
    private final List<Creature> enemies;

    public Room(String name){
        this.name = name;
        this.items = new LinkedList<>();
        this.enemies=new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    public List<Item> getItems() {
        return items;
    }
    public List<Creature> getEnemies(){return enemies;}
    public void deleteEnemy()
    {
        for(int i=0;i<enemies.size();i++)
        {
            if(enemies.get(i).getHp()==0)
            {
                enemies.remove(i);
                i--;
            }
        }
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

        public RoomBuilder addEnemy(Creature enemy)
        {
            this.room.getEnemies().add(enemy);
            return this;
        }

        public Room build(){
            return this.room;
        }
    }
}
