package com.bot.dispatcher.handlers;

import com.bot.dispatcher.Callback;
import com.bot.dispatcher.Event;
import com.bot.dispatcher.filters.Filter;
import org.telegram.telegrambots.meta.api.objects.Update;

public class EventHandler extends BaseHandler<Update> {
    public EventHandler(Filter<Update> filter, Callback<Update> callback){
        super(filter, callback);
    }
    @Override
    public boolean call(Event event) {
        if(event.event instanceof Update update && filter.call(event)){
            callback.call(event);
            return true;
        }
        return false;
    }
}
