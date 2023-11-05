package com.game;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.LinkedList;

public class Door extends Item{
    Box nextRoom;
    public Door(String name, Box nextRoom) {
        super(name);
        this.nextRoom = nextRoom;
    }

    public Box getNextRoom(){
        return nextRoom;
    }

    @Override
    public SendMessage.SendMessageBuilder actions(){
        var kb = new LinkedList<InlineKeyboardButton>();
        kb.add(InlineKeyboardButton.builder().text("Открыть").callbackData("open").build());
        return SendMessage.builder().text(getName())
                .replyMarkup(
                        InlineKeyboardMarkup.builder().keyboardRow(kb).build()
                );
    }
}
