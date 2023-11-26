package com.gameprocessor;

public class Spell implements Sendable{
    public int ap;

    public Spell(int ap) {
        this.ap = ap;
    }

    /**
     * @return текст, который будет отправлен пользователю вместе с объектом
     */
    @Override
    public String getShortText() {
        return null;
    }

    /**
     * @return текст, который будет отправлен пользователю, после того, как он выберет данный объект
     */
    @Override
    public String getLongText() {
        return null;
    }

    /**
     * @return действия, которые можно проделать с объектом, после его выбора, должны быть реализованы в GameProcessor
     */
    @Override
    public Actions getActions() {
        return null;
    }
}
