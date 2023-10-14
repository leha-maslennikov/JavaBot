package com.bot.dispatcher.filters;

import com.bot.dispatcher.Event;
import org.telegram.telegrambots.meta.api.objects.Message;

public class Command implements Filter<Event<Message>> {
    private final String command;
    
    public Command(String command){
        this("/", command);
    }

    public Command(String prefix, String text){
        this.command = prefix+text;
    }

    @Override
    public boolean call(Event<Message> event){
        if (!event.event.hasText()) return false;
        return event.event.getText().equals(command);
    }
}
