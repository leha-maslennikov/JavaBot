package com.bot.dispatcher.handlers;

import com.bot.dispatcher.Callback;
import com.bot.dispatcher.Event;
import com.bot.dispatcher.filters.Filter;
import org.telegram.telegrambots.meta.api.objects.Message;

public class EventHandler implements BaseHandler{
    private final Filter<Event<?>> filter;
    private final Callback<Event<?>> callback;
    public EventHandler(Filter<Event<?>> filter, Callback<Event<?>> callback){
        this.filter = filter;
        this.callback = callback;
    }
    @Override
    public boolean call(Event<?> event){
        if(filter.call(event)){
            callback.call(event);
            return true;
        }
        return false;
    }
}
