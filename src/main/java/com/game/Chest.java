package com.game;

public class Chest {

    /**
     * Хранимый пердмет
     */
    public Item item = new Item();
    /**
     * Пустой ли сундук
     */
    public boolean isEmpty=false;


    /**
     * @return - Показывает хранимый предмет, если его нет выводит пусто
     */
    public String ShowItem()
    {
        if(!isEmpty)
        {
            return item.name;
        }
        return "Пусто";
    }

    /**
     * Взять пердмет из сундука
     */
    public void TakeItem()
    {
        isEmpty=true;
    }




}
