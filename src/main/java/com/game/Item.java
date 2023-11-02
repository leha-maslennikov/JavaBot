package com.game;

public class Item {
    /**
     * Имя предмета
     */
    public String name;
    /**
     * Добавляемое здоровье
     */
    public int health;
    /**
     * Добавляемый урон
     */
    public int damage;
    /**
     * Был ли использован
     */
    public boolean isUsed = true;


    /**
     * @return - Можно ли надеть
     */
    public boolean wear(){
        return false;
    }
}

