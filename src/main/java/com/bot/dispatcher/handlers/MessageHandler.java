package com.bot.dispatcher.handlers;

import com.bot.dispatcher.Callback;
import com.bot.dispatcher.Event;
import com.bot.dispatcher.filters.Filter;
import org.telegram.telegrambots.meta.api.methods.GetMe;
import org.telegram.telegrambots.meta.api.objects.Message;

public class MessageHandler implements BaseHandler{
    private final Filter<Event<Message>> filter;
    private final Callback<Event<Message>> callback;
    public MessageHandler(Filter<Event<Message>> filter, Callback<Event<Message>> callback){
        this.filter = filter;
        this.callback = callback;
    }
    @Override
    public boolean call(Event<?> event){
        if(event.event instanceof Message message){
            var e = new Event<>(event.bot, message, message.getFrom().getId());
            if(filter.call(e)){
                callback.call(e);
                return true;
            }
        }
        return false;
    }
}
