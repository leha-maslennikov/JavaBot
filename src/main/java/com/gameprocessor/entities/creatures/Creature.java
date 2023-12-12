package com.gameprocessor.entities.creatures;

import com.gameprocessor.resourcemanager.Resource;

import java.util.LinkedList;

public class Creature {
    public static final String info = "info";
    public String name;
    public int hp;
    public int ap;
    public LinkedList<Resource> inventory;
    public LinkedList<Resource> equipment;

    public Creature() {
        this("no name", 0, 0);
    }

    public Creature(String name, int hp, int ap) {
        this.name = name;
        this.hp = hp;
        this.ap = ap;
        this.inventory = new LinkedList<>();
        this.equipment = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }

    public int getAp() {
        return ap;
    }

    public int getHp() {
        return hp;
    }

    public LinkedList<Resource> getInventory() {
        return inventory;
    }

    public LinkedList<Resource> getEquipment() {
        return equipment;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setAp(int ap) {
        this.ap = ap;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int attack(Creature creature) {
        return creature.reciveDamage(ap);
    }

    public int reciveDamage(int ap) {
        hp -= ap;
        if(hp < 0) hp = 0;
        return ap;
    }
}
