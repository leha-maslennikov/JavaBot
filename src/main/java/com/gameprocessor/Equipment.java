package com.gameprocessor;

public class Equipment extends Item{
    public Integer hp;
    public Integer ap;
    public Equipment(String name, String description, int hp, int ap) {
        super(name, description);
        this.hp = hp;
        this.ap = ap;
    }

//    public void equip(Resource resource) {
//        if(resource.get() instanceof Creature creature) {
//            creature.getEquipment().add();
//            creature.setHp(creature.getHp() + hp);
//            creature.setAp(creature.getAp() + ap);
//        }
//    }
//
//    public void unequip(Creature creature) {
//        if(creature.getEquipment().contains(this)) {
//            creature.getEquipment().remove(this);
//            creature.getInventory().add(this);
//            creature.setHp(creature.getHp() - hp);
//            creature.setAp(creature.getAp() - ap);
//        }
//    }

    @Override
    public Actions getActions() {
        return Actions.builder()
                .addAction("equip")
                .addAction("unequip")
                .build();
    }
}
