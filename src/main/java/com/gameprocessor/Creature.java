package com.gameprocessor;

import java.util.LinkedList;

public class Creature {
    public String name;
    public Integer hp;
    public Integer maxHp;
    public Integer ap;
    public Integer exp;
    public LinkedList<Resource> inventory;
    public LinkedList<Resource> equipment;

    public Creature() {

    }

    public Creature(String name, int hp, int ap,int exp) {
        this.name = name;
        this.maxHp = hp;
        this.hp = hp;
        this.ap = ap;
        this.exp = exp;
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
    public Integer getMaxHp(){return maxHp;}

    public LinkedList<Resource> getInventory() {
        return inventory;
    }

    public LinkedList<Resource> getEquipment() {
        return equipment;
    }

    public Integer getExp() {
        return this.exp;
    }

    public void setHp(Integer hp)
    {
        this.hp = hp;
        if (this.hp > this.maxHp)
        {
            this.hp = this.maxHp;
        }
    }
    public void setMaxHp(Integer maxHp){this.maxHp = maxHp;}

    public void setAp(Integer ap) {
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
