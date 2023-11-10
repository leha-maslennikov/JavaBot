package com.gameprocessor;

import java.util.LinkedList;

public class Creature {
    private String name;
    private Integer hp;
    private Integer ap;
    private final LinkedList<Item> inventory;
    private final LinkedList<Item> equipment;

    public Creature(String name, int hp, int ap){
        this.name = name;
        this.hp = hp;
        this.ap = ap;
        this.inventory = new LinkedList<>();
        this.equipment = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    public Integer getAp() {
        return ap;
    }

    public Integer getHp() {
        return hp;
    }

    public LinkedList<Item> getInventory() {
        return inventory;
    }

    public LinkedList<Item> getEquipment() {
        return equipment;
    }

    public void setHp(Integer hp) {
        this.hp = hp;
    }

    public void setAp(Integer ap) {
        this.ap = ap;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void attack(Creature creature)
    {
        creature.setHp(creature.getHp()-this.getAp());
        if(creature.hp<0)
        {
            creature.setHp(0);
        }
    }
}
