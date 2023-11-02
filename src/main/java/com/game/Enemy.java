package com.game;

public class Enemy extends BaseRace {
    /**
     * Имя врага
     */
    public String name;

    /**
     * @param health - Здоровье
     * @param damage - урон
     * @param name - имя
     */
    public Enemy (int health,int damage,String name)
    {
        super(health,damage);
        this.name=name;
    }

}
