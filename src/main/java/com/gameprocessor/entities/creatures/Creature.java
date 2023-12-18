package com.gameprocessor.entities.creatures;

import com.gameprocessor.entities.spells.Attack;
import com.gameprocessor.entities.spells.Spell;
import com.gameprocessor.resourcemanager.Resource;

import java.util.LinkedList;

public class Creature {
    public static final String info = "info";
    public String name;
    public int maxHp;
    public int hp;
    public int ap;
    public int exp;
    public Integer mp;
    public Integer bonusAp;
    public LinkedList<Resource> inventory;
    public LinkedList<Resource> equipment;
    public LinkedList<Spell> spells;

    public Creature() {
        this("player", 10, 1, 0,20);
    }

    public Creature(String name, int hp, int ap,int exp,int mp) {
        this.name = name;
        this.maxHp = hp;
        this.hp = hp;
        this.ap = ap;
        this.exp = exp;
        this.mp = mp;
        bonusAp = 0;
        this.inventory = new LinkedList<>();
        this.equipment = new LinkedList<>();
        this.spells = new LinkedList<>();
        spells.add(new Attack());
    }

    public Creature init() {
        return new Creature("player", 10, 1, 0,20);
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
    public Integer getMp(){return mp;}

    public Integer getBonusAp() {
        return bonusAp;
    }
    public int getMaxHp(){return maxHp;}

    public LinkedList<Resource> getInventory() {
        return inventory;
    }

    public LinkedList<Resource> getEquipment() {
        return equipment;
    }

    public int getExp() {
        return this.exp;
    }
    public void setMp(Integer mp) {
        this.mp = mp;
    }

    public void setHp(int hp)
    {
        this.hp = hp;
        if (this.hp > this.maxHp)
        {
            this.hp = this.maxHp;
        }
    }
    public void setBonusAp(Integer bonusAp) {
        this.bonusAp = bonusAp;
    }

    public void setMaxHp(int maxHp){this.maxHp = maxHp;}

    public void setAp(int ap) {
        this.ap = ap;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Spell attack(Creature creature) {
        return creature.reciveDamage(new Spell(getAp() + getBonusAp()));
    }

    public Spell reciveDamage(Spell spell) {
        hp -= spell.ap;
        if(hp < 0) hp = 0;
        return spell;
    }
}
