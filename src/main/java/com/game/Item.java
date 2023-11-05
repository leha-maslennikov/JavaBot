package com.game;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.LinkedList;

public abstract class Item {
    /**
     * Имя предмета
     */
    private final String name;

    public Item(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isEquipable(){
        return false;
    }

    public SendMessage.SendMessageBuilder equip(Creature c){
        return null;
    }

    public boolean isStoreable(){
        return false;
    }

    public SendMessage.SendMessageBuilder store(Creature c){
        return null;
    }

    public boolean isInspectable(){
        return false;
    }

    public SendMessage.SendMessageBuilder inspect(){
        return null;
    }

    public SendMessage.SendMessageBuilder actions(){
        var builder = InlineKeyboardMarkup.builder();
        if(isEquipable()){
            var kb = new LinkedList<InlineKeyboardButton>();
            kb.add(InlineKeyboardButton.builder().text("Надеть").callbackData("equip").build());
            builder.keyboardRow(kb);
        }
        if(isStoreable()){
            var kb = new LinkedList<InlineKeyboardButton>();
            kb.add(InlineKeyboardButton.builder().text("Положить в сумку").callbackData("store").build());
            builder.keyboardRow(kb);
        }
        if(isInspectable()){
            var kb = new LinkedList<InlineKeyboardButton>();
            kb.add(InlineKeyboardButton.builder().text("Анализ").callbackData("inspect").build());
            builder.keyboardRow(kb);
        }
        var kb = new LinkedList<InlineKeyboardButton>();
        kb.add(InlineKeyboardButton.builder().text("Отмена").callbackData("quit").build());
        builder.keyboardRow(kb);
        return SendMessage.builder().text(getName()).replyMarkup(builder.build());
    }
}

