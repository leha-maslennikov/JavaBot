package com.gameprocessor.entities.creatures;

public class Dwarf extends Creature {
    public static final String info = "Dwarf info";

    @Override
    public String getInfo() {
        return info;
    }

    @Override
    public int getHp() {
        return (int) Math.round(super.getHp()*1.05);
    }
}