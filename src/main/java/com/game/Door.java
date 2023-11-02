package com.game;

public class Door {
    /**
     * Комната за дверью
     */
    public int nextRoom;
    /**
     * Была ли открыта дверь
     */
    public boolean isOpen=false;
    /**
     * Номер в массиве, указывающий на ту же дверь в другой комнате
     */
    public int linkedDoor=0;

}
