package com.game;

public class BaseRace {
    /**
     * здоровье персонажа
     */
    int health;
    /**
     * Наносимый урон
     */
    int damage;

    /**
     * Конструктор персонажа
     * @param health - здоровье персонажа
     * @param damage - наносимый урон персонажа
     */
    public BaseRace(int health,int damage)
    {
        this.damage=damage;
        this.health=health;
    }

    /**
     * @return - выводит наносимый урон персонажа
     */
    public int getDamage() {
        return damage;
    }

    /**
     * @return - Выводит здоровье персонажа
     */
    public int getHealth() {
        return health;
    }

    /**
     * Персонаж получает урон
     * @param damage - получаемый урон
     * @return - выводит оставшееся здоровье
     */
    public int getHit(int damage)
    {
        this.health-=damage;
        if (this.health<0)
        {
            this.health=0;
        }
        return health;
    }
}
