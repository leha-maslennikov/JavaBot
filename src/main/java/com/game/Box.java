package com.game;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.LinkedList;
import java.util.List;

public class Box extends Item{
    private List<Item> items = new LinkedList<>();
    public Box(String name){
        super(name);
    }

    public Box put(Item item){
        items.add(item);
        return this;
    }

    public boolean contains(Item item){
        return items.contains(item);
    }

    public Item get(int n){
        return items.remove(n);
    }
    @Override
    public boolean isInspectable(){
        return true;
    }
    @Override
    public SendMessage.SendMessageBuilder inspect() {
        var builder = InlineKeyboardMarkup.builder();
        int n = 1;
        for(Item item: items){
            var kb = new LinkedList<InlineKeyboardButton>();
            kb.add(InlineKeyboardButton.builder().text(n+")"+item.getName()).callbackData("t_"+n).build());
            builder.keyboardRow(kb);
            n++;
        }
        return SendMessage.builder().text(getName()).replyMarkup(builder.build());
    }
}