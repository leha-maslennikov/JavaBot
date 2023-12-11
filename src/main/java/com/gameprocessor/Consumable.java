package com.gameprocessor;

public class Consumable extends Item {
    public Integer hp;

    public Consumable(String name, String description, int hp) {
        super(name, description);
        this.hp = hp;
    }
    public Integer getHp(){return hp;}
    public void useConsumable(Creature creature)
    {
        creature.setHp(this.getHp() + creature.getHp());
    }

    @Override
    public Actions getActions() {
        return Actions.builder()
                .addAction("use")
                .build();
    }
}
