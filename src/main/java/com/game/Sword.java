package com.game;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class Sword extends Item{
    public final int ap;
    public Sword(String name, int ap) {
        super(name);
        this.ap = ap;
    }

    @Override
    public boolean isEquipable(){
        return true;
    }

    @Override
    public boolean isStoreable(){
        return false;
    }

    @Override
    public SendMessage.SendMessageBuilder equip(Creature c){
        var builder = SendMessage.builder();
        if(c.getEquipment().contains(this)){
            return builder.text("Уже надето");
        }
        c.ap += ap;
        c.getBag().put(this);
        return builder.text(getName()+" надето");
    }
}
