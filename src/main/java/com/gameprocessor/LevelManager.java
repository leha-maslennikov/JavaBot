package com.gameprocessor;

public class LevelManager implements Sendable{

    public Integer exp;
    public Integer level;
    public Integer nextLevel;




    public LevelManager() {
        this.exp = 0;
        this.level = 1;
        this.nextLevel = 10;
    }

    public Integer getExp() {
        return exp;
    }

    public Integer getLevel() {
        return level;
    }

    public Integer getNextLevel() {
        return nextLevel;
    }

    public void setExp(Integer exp) {
        this.exp = exp;

    }

    public void setNextLevel(Integer nextLevel) {
        this.nextLevel = nextLevel;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public String getShortText() {
        return "Далее";
    }

    @Override
    public String getLongText() {
        return "Выберите улучшение:";
    }


    @Override
    public Actions getActions() {
        return Actions.builder()
                .addAction("raiseHp")
                .addAction("raiseAp")
                .build();
    }
}
