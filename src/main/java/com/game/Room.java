package com.game;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.LinkedList;
import java.util.List;

public class Room extends Item{
    private List<Item> items = new LinkedList<>();
    public Room(String name){
        super(name);
    }

    public Room put(Item item){
        items.add(item);
        return this;
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
