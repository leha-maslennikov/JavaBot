package com.gameprocessor;

public class SkillManager implements Sendable {


    @Override
    public String getShortText() {
        return null;
    }

    @Override
    public String getLongText() {
        return "Выберите умение: ";
    }
    @Override
    public Actions getActions() {
        return Actions.builder()
                .addAction("areaAttack")
                .addAction("forceAttack")
                .build();
    }
}
