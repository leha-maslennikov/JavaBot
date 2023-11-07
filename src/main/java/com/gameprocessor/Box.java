package com.gameprocessor;

import java.util.LinkedList;
import java.util.List;

public class Box extends Item{
    private final List<Item> items;
    public Box(String name, String description) {
        super(name, description);
        this.items = new LinkedList<>();
    }

    public List<Item> getItems() {
        return items;
    }

    @Override
    public Actions getActions() {
        return Actions.builder()
                .addAction("loot")
                .build();
    }

    public static BoxBuilder builder(String name, String description){
        return new BoxBuilder(name, description);
    }

    public static class BoxBuilder{
        private final Box box;

        public BoxBuilder(String name, String description){
            this.box = new Box(name,description);
        }

        public BoxBuilder addItem(Item item){
            this.box.getItems().add(item);
            return this;
        }

        public Box build(){
            return this.box;
        }
    }
}
