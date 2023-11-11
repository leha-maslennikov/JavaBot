package com.gameprocessor;

public class Equipment extends Item{
    private final Integer hp;
    private final Integer ap;
    public Equipment(String name, String description, int hp, int ap) {
        super(name, description);
        this.hp = hp;
        this.ap = ap;
    }

    public void equip(Creature creature){
        if(creature.getInventory().contains(this)){
            creature.getInventory().remove(this);
            creature.getEquipment().add(this);
            creature.setHp(creature.getHp() + hp);
            creature.setAp(creature.getAp() + ap);
        }
    }

    public void unequip(Creature creature){
        if(creature.getEquipment().contains(this)) {
            creature.getEquipment().remove(this);
            creature.getInventory().add(this);
            creature.setHp(creature.getHp() - hp);
            creature.setAp(creature.getAp() - ap);
        }
    }

    @Override
    public Actions getActions() {
        return Actions.builder()
                .addAction("equip")
                .addAction("unequip")
                .build();
    }
}
