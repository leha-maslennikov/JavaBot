package com.game;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class Creature {
    protected String name;
    protected int hp;
    protected int ap;
    protected Item[] bag;
    protected Item[] equipment;

    public Creature(String name){
        this.name = name;
        hp = 10;
        ap = 1;
        bag = new Item[8];
        equipment = new Item[5];
    }

    public String getName(){
        return name;
    }
    public int getAp() {
        return ap;
    }

    public int getHp() {
        return hp;
    }

    public SendMessage.SendMessageBuilder Attack(Creature enemy){
        int damage = getAp();
        return SendMessage.builder().text(getName()+": "+enemy.getName()+" -"+damage);
    }

    public SendMessage.SendMessageBuilder equip(Item item){
        return item.equip(this);
    }


}
